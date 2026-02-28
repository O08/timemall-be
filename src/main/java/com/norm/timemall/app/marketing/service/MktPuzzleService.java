package com.norm.timemall.app.marketing.service;

import com.norm.timemall.app.marketing.domain.dto.MktShareStoryDTO;
import com.norm.timemall.app.marketing.domain.pojo.MktFetchDreams;
import com.norm.timemall.app.marketing.domain.pojo.MktFetchPuzzleInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface MktPuzzleService {
    MktFetchPuzzleInfo findPuzzleInfo(String puzzleVersion);

    MktFetchDreams findDreams(String q, String puzzleVersion);

    void likeDream(String dreamId);

    void createDreamStory(MktShareStoryDTO dto);

    void openTreasureBox(MultipartFile clueOne, MultipartFile clueTwo, String clueThree, String puzzleVersion);

}
