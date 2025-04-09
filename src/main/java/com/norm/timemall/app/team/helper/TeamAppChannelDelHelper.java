package com.norm.timemall.app.team.helper;


import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.team.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamAppChannelDelHelper {

    @Autowired
    private TeamOasisHtmlAppService teamOasisHtmlAppService;

    @Autowired
    private TeamAppCardService teamAppCardService;
    @Autowired
    private TeamAppLinkShoppingService teamAppLinkShoppingService;

    @Autowired
    private TeamAppFbService teamAppFbService;

    @Autowired
    private TeamAppGroupChatService teamAppGroupChatService;

    public void doRemoveOasisChannelData(OasisChannel oasisChannel){
        switch (oasisChannel.getAppId()){
            case "1":
                teamOasisHtmlAppService.removeChannelData(oasisChannel.getId());
                break;

            case "2":
                teamAppFbService.removeChannelData(oasisChannel.getId());
                break;

            case "3":
                teamAppCardService.removeChannelData(oasisChannel.getId());
                break;

            case "4":
                teamAppLinkShoppingService.removeChannelData(oasisChannel.getId());
                break;
            case "5":
                teamAppGroupChatService.removeChannelData(oasisChannel.getId());
                break;
            default:
                break;
        }
    }


}
