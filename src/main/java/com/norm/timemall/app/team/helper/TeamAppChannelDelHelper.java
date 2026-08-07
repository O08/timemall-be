package com.norm.timemall.app.team.helper;


import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.mo.MiniAppLibrary;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.team.mapper.TeamMiniAppLibraryMapper;
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
    private TeamAppDeskService teamAppDeskService;

    @Autowired
    private TeamAppGroupChatService teamAppGroupChatService;

    @Autowired
    private TeamMiniAppLibraryMapper teamMiniAppLibraryMapper;

    @Autowired
    private TeamAppRedeemService teamAppRedeemService;

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
            case "6":
                teamAppDeskService.removeChannelData(oasisChannel.getId());
                break;
            default:
                break;
        }
    }


    public void validateCanRemoveChannel(String oasisChannelId, String appId) {
        MiniAppLibrary miniAppLibrary = teamMiniAppLibraryMapper.selectById(appId);
        if(!SwitchCheckEnum.ENABLE.getMark().equals(miniAppLibrary.getEnableValidateBeforeRemoveChannel())){
            return;
        }
        switch (appId){
            case "8":
                teamAppRedeemService.doValidateChannelBeforeRemove(oasisChannelId);
                break;
            default:
                break;
        }

    }
}
