package com.norm.timemall.app.team.helper;


import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.team.service.TeamAppCardService;
import com.norm.timemall.app.team.service.TeamAppFbService;
import com.norm.timemall.app.team.service.TeamAppLinkShoppingService;
import com.norm.timemall.app.team.service.TeamOasisHtmlAppService;
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
            default:
                break;
        }
    }


}
