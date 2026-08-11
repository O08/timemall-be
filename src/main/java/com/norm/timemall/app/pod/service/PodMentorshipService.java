package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.pod.domain.dto.PodFetchMentorPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchMentorPageRO;

public interface PodMentorshipService {

    IPage<PodFetchMentorPageRO> findMentorPage(PodFetchMentorPageDTO dto);
    
    void deleteMentor(String id);
    
    void applyMentor(String mentorBrandId);

}
