package com.norm.timemall.app.team.domain.pojo;

import com.norm.timemall.app.base.enums.AppViberCommentInteractEventEnum;
import com.norm.timemall.app.base.mo.AppViberCommentInteract;
import com.norm.timemall.app.team.domain.dto.TeamAppViberCommentInteractDTO;
import lombok.Data;

@Data
public class TeamAppViberCommentInteractionHelper {
    private final int likeDelta;
    private final int dissDelta;

    public TeamAppViberCommentInteractionHelper(int likeDelta, int dissDelta) {
        this.likeDelta = likeDelta;
        this.dissDelta = dissDelta;
    }

    /**
     * Calculates the change (delta) in like and dislike counts based on a new interaction event.
     *
     * @param existingInteract The current interaction object (or null if none exists).
     * @param dto The DTO containing the new event (LIKE or DISLIKE mark).
     * @return An InteractionDelta object containing the calculated likeDelta and dissDelta.
     */
    public static TeamAppViberCommentInteractionHelper calculateDeltas(AppViberCommentInteract existingInteract, TeamAppViberCommentInteractDTO dto ) {
        int likeDelta = 0;
        int dissDelta = 0;

        if(existingInteract==null){
            likeDelta = AppViberCommentInteractEventEnum.LIKE.getMark().equals(dto.getEvent()) ? 1 : 0;
            dissDelta = AppViberCommentInteractEventEnum.DISLIKE.getMark().equals(dto.getEvent()) ? 1 : 0;
        }

        if(existingInteract != null && AppViberCommentInteractEventEnum.LIKE.getMark().equals(dto.getEvent())){
            likeDelta = 1;
            dissDelta = -1;
        }
        if(existingInteract != null && AppViberCommentInteractEventEnum.DISLIKE.getMark().equals(dto.getEvent())){
            likeDelta = -1;
            dissDelta = 1;
        }

        return new TeamAppViberCommentInteractionHelper(likeDelta, dissDelta);
    }


}