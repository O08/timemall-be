package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.TeamAddBrandAlipayDTO;
import com.norm.timemall.app.team.domain.dto.TeamAddBrandBankDTO;
import com.norm.timemall.app.team.domain.dto.TeamTalentPageDTO;
import com.norm.timemall.app.team.domain.pojo.TeamBrandAlipayAccount;
import com.norm.timemall.app.team.domain.pojo.TeamBrandBank;
import com.norm.timemall.app.team.domain.ro.TeamTalentRO;
import com.norm.timemall.app.team.domain.vo.TeamBrandAlipayAccountVO;
import com.norm.timemall.app.team.domain.vo.TeamBrandBankVO;
import com.norm.timemall.app.team.domain.vo.TeamTalentPageVO;
import com.norm.timemall.app.team.service.TeamBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamBrandController {

    @Autowired
    private TeamBrandService teamBrandService;
    /**
     * 商家黄页
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/talent")
    public TeamTalentPageVO retrieveTalents(@Validated TeamTalentPageDTO dto){
        IPage<TeamTalentRO>  talent = teamBrandService.findTalents(dto);
        TeamTalentPageVO vo = new TeamTalentPageVO();
        vo.setTalent(talent);
        vo.setResponseCode(CodeEnum.SUCCESS);

       return vo;
    }
    /**
     * 查询商家银行卡
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/bank")
    public TeamBrandBankVO retrieveBanks(){
        TeamBrandBank bank = teamBrandService.findBrandBank();
        TeamBrandBankVO vo = new TeamBrandBankVO();
        vo.setBank(bank);
        vo.setResponseCode(CodeEnum.SUCCESS);

        return vo;
    }

    /**
     * 添加商家银行卡
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/team/bank")
    public SuccessVO addBankCard(TeamAddBrandBankDTO dto){
        teamBrandService.addNewBank(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * 查询商家支付宝账号
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/team/alipayAccounts")
    public TeamBrandAlipayAccountVO retrieveAliPayAccounts(){
        TeamBrandAlipayAccount alipayAccount = teamBrandService.findBrandAlipay();
        TeamBrandAlipayAccountVO vo = new TeamBrandAlipayAccountVO();
        vo.setAlipay(alipayAccount);
        vo.setResponseCode(CodeEnum.SUCCESS);

        return vo;
    }

    /**
     * 添加商家支付宝账号
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/team/addAlipayAccount")
    public SuccessVO addAlipayAccount(@Validated  @RequestBody TeamAddBrandAlipayDTO dto){
        teamBrandService.addNewAlipayAccount(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    /**
     * 删除商家支付宝账号
     */
    @ResponseBody
    @DeleteMapping(value = "/api/v1/team/delAlipayAccount")
    public SuccessVO delAlipayAccount(@RequestParam("id") String id){
        teamBrandService.delAlipayAccount(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
