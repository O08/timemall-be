package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.StudioMpsChainPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsChainDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsChainDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchAChain;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsChainRO;
import com.norm.timemall.app.studio.domain.vo.StudioFectchMpsChainPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchAChainVO;
import com.norm.timemall.app.studio.domain.vo.StudioNewMpsChainVO;
import com.norm.timemall.app.studio.service.StudioMpsChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioMpsChainController {
    @Autowired
    private StudioMpsChainService studioMpsChainService;



    @GetMapping(value = "/api/v1/web_estudio/brand/mps_chain")
    public StudioFectchMpsChainPageVO fetchMpsChainPage(StudioMpsChainPageDTO dto){

        IPage<StudioFetchMpsChainRO> chain= studioMpsChainService.fetchMpsChainPage(dto);
        StudioFectchMpsChainPageVO vo = new StudioFectchMpsChainPageVO();
        vo.setChain(chain);
        vo.setResponseCode(CodeEnum.SUCCESS);

        return vo;
    }
    @GetMapping("/api/v1/web_estudio/mps/chain/{chain_id}")
    public StudioFetchAChainVO fetchAchainInfo(@PathVariable("chain_id") String chainId){

        StudioFetchAChain chain = studioMpsChainService.findChainInfo(chainId);
        StudioFetchAChainVO vo = new StudioFetchAChainVO();
        vo.setChain(chain);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/web_estudio/brand/mps_chain/new")
    public StudioNewMpsChainVO newMpsChain(@RequestBody @Validated StudioNewMpsChainDTO dto){

        String chaindId=studioMpsChainService.newMpsChain(dto);
        StudioNewMpsChainVO vo=new StudioNewMpsChainVO();
        vo.setChainId(chaindId);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PutMapping("/api/v1/web_estudio/brand/mps_chain/chain")
    public SuccessVO putMpsChain(@RequestBody @Validated StudioPutMpsChainDTO dto){

        studioMpsChainService.modifyMpsChain(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
