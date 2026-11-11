package com.norm.timemall.app.base.rtm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;

import java.io.IOException;

@Configuration
public class TioServerConfig {

    @Autowired
    private RtmJwtUtil rtmJwtUtil;
    @Bean
    public WsServerStarter wsServerStarter() throws IOException {
        RtmWsMsgHandler handler = new RtmWsMsgHandler(rtmJwtUtil);
        WsServerConfig config = new WsServerConfig(8888); // Port for WebSocket

        WsServerStarter starter = new WsServerStarter(config, handler);

        org.tio.server.TioServerConfig tioServerConfig = starter.getTioServerConfig();
        tioServerConfig.setHeartbeatTimeout(60000);
        tioServerConfig.setReadBufferSize(2048);
        tioServerConfig.setName("Blv-Rtm-Service");

        starter.start();
        return starter;
    }
}
