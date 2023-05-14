package com.norm.timemall.app.ms.helper;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedDTO;
import com.norm.timemall.app.ms.domain.dto.MsEventFeedTriggerDTO;
import com.norm.timemall.app.ms.domain.pojo.MsPodMessageNotice;
import com.norm.timemall.app.ms.domain.pojo.MsStudioMessageNotice;
import com.norm.timemall.app.ms.enums.MsEventFeedCodeEnum;
import com.norm.timemall.app.ms.service.MsEventFeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MsEventFeedHelper {
    @Autowired
    private MsEventFeedService msEventFeedService;
    public void eventHandler(MsEventFeedTriggerDTO dto){
        Gson gson = new Gson();
        CustomizeUser customizeUser = SecurityUserHelper.getCurrentPrincipal();
        if(MsEventFeedCodeEnum.UPDATE_EVENT_FEED_MARK.getMark().equals(dto.getEventCode())){
            MsEventFeedDTO msEventFeedDTO = gson.fromJson(dto.getAppendix(), MsEventFeedDTO.class);
            assertMsEventFeedDTO(msEventFeedDTO);
            msEventFeedService.modifyEventFeedMark(msEventFeedDTO);
        }
        if(MsEventFeedCodeEnum.SEND_POD_MESSAGE_NOTICE.getMark().equals(dto.getEventCode())){
            MsPodMessageNotice msPodMessageNotice = gson.fromJson(dto.getAppendix(), MsPodMessageNotice.class);
            assertMsPodMessageNotice(msPodMessageNotice);

            msPodMessageNotice.setEventCode(MsEventFeedCodeEnum.SEND_POD_MESSAGE_NOTICE.getMark());
            msPodMessageNotice.setUpDesc(customizeUser.getUsername());

            msEventFeedService.sendPodMessageNotice(msPodMessageNotice);
        }
        if(MsEventFeedCodeEnum.SEND_STUDIO_MESSAGE_NOTICE.getMark().equals(dto.getEventCode())){
            MsStudioMessageNotice msStudioMessageNotice = gson.fromJson(dto.getAppendix(), MsStudioMessageNotice.class);
            assertMsStudioMessageNotice((msStudioMessageNotice));

            msStudioMessageNotice.setEventCode(MsEventFeedCodeEnum.SEND_STUDIO_MESSAGE_NOTICE.getMark());
            msStudioMessageNotice.setUpDesc(customizeUser.getUsername());

            msEventFeedService.send_studio_message_notice(msStudioMessageNotice);
        }
    }


    private void assertMsEventFeedDTO(MsEventFeedDTO msEventFeedDTO){
        if(StrUtil.isEmpty(msEventFeedDTO.getMark()) || StrUtil.isEmpty(msEventFeedDTO.getScene())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
    }
    private void assertMsPodMessageNotice(MsPodMessageNotice msPodMessageNotice){
       if(StrUtil.isEmpty(msPodMessageNotice.getWorkFlowId())
         || StrUtil.isEmpty(msPodMessageNotice.getDown())){
           throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
       }
    }
    private void assertMsStudioMessageNotice(MsStudioMessageNotice msStudioMessageNotice){
        if(StrUtil.isEmpty(msStudioMessageNotice.getWorkFlowId())
                || StrUtil.isEmpty(msStudioMessageNotice.getDown())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
    }
}
