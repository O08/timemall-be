package com.norm.timemall.app.team.domain.pojo;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.AppViberPostInteractEventEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppViberPostInteract;
import com.norm.timemall.app.team.domain.dto.TeamAppViberPostInteractDTO;
import lombok.Data;

import java.util.Date;

@Data
public class TeamAppViberPostInteractionHelper {
    private final int likeDelta;
    private final int shareDelta;

    public TeamAppViberPostInteractionHelper(int likeDelta, int shareDelta) {
        this.likeDelta = likeDelta;
        this.shareDelta = shareDelta;
    }

    public static boolean isRepeatOrNotAllowAction(AppViberPostInteract existingInteract, TeamAppViberPostInteractDTO dto){
        if(existingInteract==null && dto.getEvent().equals(AppViberPostInteractEventEnum.CANCEL_LIKE.getMark())){
            return  true;
        }
        if(existingInteract==null){
            return false;
        }
        boolean isSameCancelLikeAction = dto.getEvent().equals(AppViberPostInteractEventEnum.CANCEL_LIKE.getMark()) &&
                existingInteract.getHasLike() != null && existingInteract.getHasLike() == 0;

        boolean isSameLikeAction = dto.getEvent().equals(AppViberPostInteractEventEnum.LIKE.getMark()) &&
                existingInteract.getHasLike() != null && existingInteract.getHasLike() == 1;

        boolean isSameShareAction = dto.getEvent().equals(AppViberPostInteractEventEnum.SHARE.getMark()) &&
                existingInteract.getHasShare() != null && existingInteract.getHasShare() == 1;

        return isSameCancelLikeAction || isSameLikeAction || isSameShareAction ;

    }
    public static AppViberPostInteract createInteract(TeamAppViberPostInteractDTO dto){

        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        AppViberPostInteract interact = new AppViberPostInteract();
        interact.setId(IdUtil.simpleUUID())
                .setPostId(dto.getPostId())
                .setReaderBrandId(currentUserBrandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        if(dto.getEvent().equals(AppViberPostInteractEventEnum.LIKE.getMark())){
            interact.setHasLike(1);
        }
        if(dto.getEvent().equals(AppViberPostInteractEventEnum.CANCEL_LIKE.getMark())){
            interact.setHasLike(0);
        }
        if(dto.getEvent().equals(AppViberPostInteractEventEnum.SHARE.getMark())){
            interact.setHasShare(1);
        }

        return interact;
    }

    /**
     * Calculates the change (delta) in like and share counts based on a new interaction event.
     *
     * @param existingInteract The current interaction object (or null if none exists).
     * @param dto The DTO containing the new event (LIKE, CANCEL_LIKE, or SHARE).
     * @return A PostInteractionDelta object containing the calculated likeDelta and shareDelta.
     */
    public static TeamAppViberPostInteractionHelper calculateDeltas(AppViberPostInteract existingInteract, TeamAppViberPostInteractDTO dto) {
        int likeDelta = 0;
        int shareDelta = 0;

        if(existingInteract==null){
            likeDelta = AppViberPostInteractEventEnum.LIKE.getMark().equals(dto.getEvent()) ? 1 : 0;
            shareDelta = AppViberPostInteractEventEnum.SHARE.getMark().equals(dto.getEvent()) ? 1 : 0;
        }

        if(existingInteract != null && AppViberPostInteractEventEnum.LIKE.getMark().equals(dto.getEvent())){
            likeDelta=1;
        }
        if(existingInteract != null && AppViberPostInteractEventEnum.CANCEL_LIKE.getMark().equals(dto.getEvent())){
            likeDelta=-1;
        }
        if(existingInteract != null && AppViberPostInteractEventEnum.SHARE.getMark().equals(dto.getEvent())){
            shareDelta=1;
        }

        return new TeamAppViberPostInteractionHelper(likeDelta, shareDelta);
    }
}