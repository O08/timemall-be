package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.BillCategoiesEnum;
import com.norm.timemall.app.base.enums.BillMarkEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.ProposalProjectStatusEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.mo.Proposal;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.dto.PodGetProposalsPageDTO;
import com.norm.timemall.app.pod.domain.vo.PodGetProposalsPageRO;
import com.norm.timemall.app.pod.mapper.PodBillMapper;
import com.norm.timemall.app.pod.mapper.PodProposalMapper;
import com.norm.timemall.app.pod.service.PodProposalOrderService;
import com.norm.timemall.app.base.pojo.ProposalServiceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

@Service
public class PodProposalOrderServiceImpl implements PodProposalOrderService {

    @Autowired
    private PodProposalMapper podProposalMapper;

    @Autowired
    private PodBillMapper podBillMapper;

    @Override
    public IPage<PodGetProposalsPageRO> findProposal(PodGetProposalsPageDTO dto) {

        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Page<PodGetProposalsPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return podProposalMapper.selectPageByQ(page,dto,buyerBrandId);

    }

    @Override
    public void toSignProposal(String id) {
        Proposal proposal = podProposalMapper.selectById(id);
        if(proposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        if(!(ProposalProjectStatusEnum.DRAFT.ordinal()+"").equals(proposal.getProjectStatus())){
            throw new QuickMessageException("提案状态验证失败");
        }
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentBrandId.equals(proposal.getSellerBrandId());
        if(isSeller){
            throw new QuickMessageException("提案暂不支持自签");
        }

        generateFirstBill(proposal);

        proposal.setBuyerBrandId(currentBrandId);
        proposal.setModifiedAt(new Date());
        proposal.setProjectStatus(ProposalProjectStatusEnum.SIGNED.ordinal()+"");
        podProposalMapper.updateById(proposal);



    }
    private void generateFirstBill(Proposal proposal){
        CustomizeUser payer = SecurityUserHelper.getCurrentPrincipal();
        Gson gson = new Gson();
        ProposalServiceItem[] services = gson.fromJson(proposal.getServices().toString(), ProposalServiceItem[].class);
        ProposalServiceItem service = services[0];
        BigDecimal amount = service.getQuantity().multiply(service.getPrice())
                .setScale(2, RoundingMode.HALF_UP);
        Bill bill =new Bill();
        bill.setId(IdUtil.simpleUUID())
                .setOrderId(proposal.getId()) // proposal id
                .setStage(service.getServiceName())
                .setStageNo("0")
                .setAmount(amount)
                .setPayeeFid(proposal.getSellerBrandId())
                .setPayeeFidType(FidTypeEnum.BRAND.getMark())
                .setPayerFid(payer.getBrandId())
                .setPayerFidType(FidTypeEnum.BRAND.getMark())
                .setRemark(proposal.getProjectName())
                .setCategories(BillCategoiesEnum.PROPOSAL.getMark())
                .setMark(BillMarkEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        podBillMapper.insert(bill);
    }
}
