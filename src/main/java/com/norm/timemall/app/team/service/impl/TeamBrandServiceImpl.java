package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.PayType;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.BrandAlipay;
import com.norm.timemall.app.base.mo.BrandBank;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamAddBrandAlipayDTO;
import com.norm.timemall.app.team.domain.dto.TeamAddBrandBankDTO;
import com.norm.timemall.app.team.domain.dto.TeamTalentPageDTO;
import com.norm.timemall.app.team.domain.pojo.TeamBrandAlipayAccount;
import com.norm.timemall.app.team.domain.pojo.TeamBrandAlipayAccountItem;
import com.norm.timemall.app.team.domain.pojo.TeamBrandBank;
import com.norm.timemall.app.team.domain.pojo.TeamBrandBankItem;
import com.norm.timemall.app.team.domain.ro.TeamTalentRO;
import com.norm.timemall.app.team.mapper.TeamBrandAlipayMapper;
import com.norm.timemall.app.team.mapper.TeamBrandBankMapper;
import com.norm.timemall.app.team.mapper.TeamBrandMapper;
import com.norm.timemall.app.team.service.TeamBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamBrandServiceImpl implements TeamBrandService {
    @Autowired
    private TeamBrandMapper teamBrandMapper;
    @Autowired
    private TeamBrandBankMapper teamBrandBankMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TeamBrandAlipayMapper teamBrandAlipayMapper;
    @Override
    public IPage<TeamTalentRO> findTalents(TeamTalentPageDTO dto) {
        IPage<TeamTalentRO> page  = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());

        return teamBrandMapper.selectPageByQ(page,dto);
    }

    @Override
    public TeamBrandBank findBrandBank() {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        ArrayList<TeamBrandBankItem> banks = teamBrandBankMapper.selectBankByBrandId(brandId);
        TeamBrandBank ro = new TeamBrandBank();
        ro.setRecords(banks);
        return ro;
    }

    @Override
    public void addNewBank(TeamAddBrandBankDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        BrandBank bank = new BrandBank();
        bank.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setCardholder(dto.getCardholder())
                .setCardno(dto.getCardno())
                .setDeposit(dto.getDeposit())
                .setDepositName(dto.getDepositName())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamBrandBankMapper.insert(bank);
    }

    @Override
    public TeamBrandAlipayAccount findBrandAlipay() {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<TeamBrandAlipayAccountItem>  ros= teamBrandAlipayMapper.selectAlipayByBrandId(brandId);
        TeamBrandAlipayAccount records = new TeamBrandAlipayAccount();
        records.setRecords(ros);
        return records;
    }

    @Override
    public void addNewAlipayAccount(TeamAddBrandAlipayDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        BrandAlipay alipayAccount = new BrandAlipay();
        alipayAccount.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setPayType(PayType.ALIPAY.getCode()+"")
                .setPayeeAccount(dto.getPayeeAccount())
                .setPayeeRealName(dto.getPayeeRealName())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamBrandAlipayMapper.insert(alipayAccount);
    }

    @Override
    public void delAlipayAccount(String id) {
        teamBrandAlipayMapper.deleteById(id);
    }

}
