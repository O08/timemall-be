package com.norm.timemall.app.ms.controller;

import com.norm.timemall.app.base.entity.ResultVO;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.ms.helper.SseHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class SseController {


    /**
     * 用于创建连接
     */
    @CrossOrigin
    @GetMapping("/api/v1/ms/sse/connect")
    public SseEmitter connect() {
        String userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        return SseHelper.connect(userId);
    }
//    @CrossOrigin
//    @GetMapping("/api/v1/ms/sse/connect/{userId}")
//    public SseEmitter connect(@PathVariable String userId) {
//        return SseHelper.connect(userId);
//    }

//    /**
//     * 推送给所有人
//     *
//     * @param message
//     * @return
//     */
//    @GetMapping("/api/v1/ms/sse/push/{message}")
//    public CodeVO push(@PathVariable(name = "message") String message) {
//        //获取连接人数
//        int userCount = SseHelper.getUserCount();
//        //如果无在线人数，返回
//        if(userCount<1){
//            return CodeVO.fail("无人在线！");
//        }
//        SseHelper.batchSendMessage(message);
//        return CodeVO.success("发送成功！");
//    }
//
//    /**
//     * 发送给单个人
//     *
//     * @param message
//     * @param userid
//     * @return
//     */
//    @GetMapping("/api/v1/ms/sse/push_one/{message}/{userid}")
//    public ResultVO pushOne(@PathVariable(name = "message") String message, @PathVariable(name = "userid") String userid) {
//        SseHelper.sendMessage(userid, message);
//        return ResultVO.success("推送消息给" + userid);
//    }

    /**
     * 关闭连接
     */
    @GetMapping("/api/v1/ms/sse/close")
    public ResultVO close() {
        String userId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        SseHelper.removeUser(userId);
        return ResultVO.success("连接关闭");
    }
}
