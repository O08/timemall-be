package com.norm.timemall.app.base.rtm;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseStatus;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.handler.IWsMsgHandler;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RtmWsMsgHandler implements IWsMsgHandler {

    private final RtmJwtUtil rtmJwtUtil;


    //定义一个全局缓存，记录 groupId -> 上次推送时间

    private static final Cache<String, Long> groupLimitMap = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.SECONDS) // 写入 1 秒后自动删除，天然清理
            .maximumSize(10000) // 保护内存，最多存 1 万个
            .build();


    // Constructor injection
    public RtmWsMsgHandler(RtmJwtUtil rtmJwtUtil) {
        this.rtmJwtUtil = rtmJwtUtil;
    }
    private static final Gson gson = new Gson();

    /** Handshake & Auth (Spring Security alternative for WS) */
    @Override
    public HttpResponse handshake(HttpRequest request, HttpResponse response, ChannelContext channelContext) {
        // 1. Get token from query string: ws://ip:port?token=xxxx
        String token = request.getParam("token");

        // 2. Validate Token using your Nimbus-JWT logic
        String userId = rtmJwtUtil.verifyAndGetUserId(token);

        if (userId != null) {
            Tio.bindUser(channelContext, userId);
            log.info("User {} connected successfully via WebSocket", userId);
            return response;
        }

        log.warn("Unauthorized WebSocket connection attempt from IP: {}", request.getRemote().getIp());
        response.setStatus(HttpResponseStatus.C401);
        return response;
    }

    @Override
    public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) {
        if ("ping".equals(text)) {
            return WsResponse.fromText("pong", StandardCharsets.UTF_8.name());
        }
        JsonElement element = JsonParser.parseString(text);
        if (!element.isJsonObject()) return null;
        JsonObject json = element.getAsJsonObject();

        if (json == null || !json.has("event")) return null;

        String action = json.get("event").getAsString();
        String groupId = json.has("groupId") ? json.get("groupId").getAsString() : null;

        boolean groupIdExist= groupId != null && !groupId.isEmpty();
        if (RtmChatEvent.JOIN_GROUP.getValue().equals(action) && groupIdExist) {
            return  helpUserJoinGroup(groupId,channelContext);
        } else if (RtmChatEvent.NOTIFY_PULL.getValue().equals(action) && groupIdExist) {
            return notifyUserPull(groupId,channelContext);
        }
        return null;
    }


    private Object helpUserJoinGroup(String groupId,ChannelContext channelContext){
        // 💡 核心逻辑：退出其他所有群组
        SetWithLock<String> oldGroups = channelContext.getGroups();
        if (oldGroups != null) {
            // 注意：这里建议直接用一行代码批量解绑该连接下的所有群聊
            Tio.unbindGroup(channelContext);
        }

        // 绑定新群
        Tio.bindGroup(channelContext, groupId);

        // 返回响应
        JsonObject res = new JsonObject();
        res.addProperty("status", "success");
        res.addProperty("msg", "已切换至群: " + groupId);
        return WsResponse.fromText(res.toString(), StandardCharsets.UTF_8.name());
    }

    /**
     *
     * @param groupId 群组
     * @param channelContext  用户
     * @return 返回 null： T-io 不会给发信人任何回复，处理结束
     */
    private Object notifyUserPull(String groupId, ChannelContext channelContext){
        if (!Tio.isInGroup(groupId, channelContext)) {
            log.warn("用户{}尝试向未加入的群组{}发送信号", channelContext.userid, groupId);
            return null;
        }
        long now = System.currentTimeMillis();
        // 从连接上下文中获取上次发送时间
        Long lastSendTime = (Long) channelContext.getAttribute("LAST_NOTIFY_TIME");

        // 用户限流
        if (lastSendTime != null && (now - lastSendTime < 1000)) {
            log.warn("用户{}发送频率过快", channelContext.userid);
            return null; // 直接丢弃消息，不予处理
        }

        // 群组限流
        Long lastGroupPush = groupLimitMap.getIfPresent(groupId);
        // 限制整个群每 1000ms 只能广播一次信号
        if (lastGroupPush != null && (now - lastGroupPush < 1000)) {
            log.info("群组{}推送过快，已拦截本次转发", groupId);
            return null;
        }

        // 更新推送时间
        channelContext.setAttribute("LAST_NOTIFY_TIME", now);
        groupLimitMap.put(groupId, now);

        // 推送给该组内的所有成员，不通知发信人
        // 用户信号解析对象
        JsonObject signal = new JsonObject();
        signal.addProperty("event", RtmChatEvent.NEW_MSG_NOTIFY.getValue());
        signal.addProperty("groupId", groupId);
        signal.addProperty("timestamp", now); // 增加时间戳防止前端缓存
        WsResponse response = WsResponse.fromText(signal.toString(), StandardCharsets.UTF_8.name());
        
        Tio.sendToGroup(channelContext.tioConfig, groupId, response, ctx -> channelContext != ctx);

        return null;
    }

    @Override
    public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) {
        Map<String, String> body = new HashMap<>();
        body.put("event", RtmChatEvent.CONNECTED.getValue());
        body.put("userId", channelContext.userid);

        String syncSignal = gson.toJson(body);
        WsResponse response = WsResponse.fromText(syncSignal, StandardCharsets.UTF_8.name());
        Tio.send(channelContext, response);
    }



    @Override public Object onBytes(WsRequest r, byte[] b, ChannelContext c) {
        log.info("RTM 收到二进制消息，长度：{}", b.length);
        return null;
    }
    @Override public Object onClose(WsRequest r, byte[] b, ChannelContext c) {
        log.debug("RTM 连接关闭: {}", c.toString());
        return null;
    }
}