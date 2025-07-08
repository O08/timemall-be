package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.ProposalProjectStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Proposal;
import com.norm.timemall.app.studio.domain.dto.StudioChangeProposalDTO;
import com.norm.timemall.app.studio.domain.dto.StudioChangeProposalStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCreateNewProposalDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchProposalPageDTO;
import com.norm.timemall.app.base.pojo.ProposalServiceItem;
import com.norm.timemall.app.studio.domain.ro.StudioFetchOneProposalRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchProposalPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchOneProposalVO;
import com.norm.timemall.app.studio.mapper.StudioProposalMapper;
import com.norm.timemall.app.studio.service.StudioProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Service
public class StudioProposalServiceImpl implements StudioProposalService {
    @Autowired
    private StudioProposalMapper studioProposalMapper;

    @Override
    public void newOneProposal(StudioCreateNewProposalDTO dto) {
        // validate tags to json  arr
        try {
            new JSONArray(dto.getServices());
        } catch (JSONException ne) {
            throw new QuickMessageException("services 字段传参不合法");
        }
        Gson gson = new Gson();
        ProposalServiceItem[] services = gson.fromJson(dto.getServices(), ProposalServiceItem[].class);
        if(services.length==0){
            throw new QuickMessageException("未找到服务与费用信息");
        }
        boolean existsBlankServiceName = Arrays.stream(services).anyMatch(a -> CharSequenceUtil.isBlank(a.getServiceName()));
        if(existsBlankServiceName){
            throw new QuickMessageException("服务与费用信息存在服务项目名称未填写");
        }
        long haveZeroOrNegativeFeeServiceItem = Arrays.stream(services).filter(a -> BigDecimal.ZERO.compareTo(a.getQuantity().multiply(a.getPrice())) >= 0).count();

        if(haveZeroOrNegativeFeeServiceItem>0){
            throw new QuickMessageException("服务费用必须大于0");
        }
        BigDecimal total = Arrays.stream(services).map(a -> a.getPrice().multiply(a.getQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);

        String projectNo= getProjectNo();
        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Proposal proposal = new Proposal();
        proposal.setId(IdUtil.simpleUUID())
                .setServices(dto.getServices())
                .setSellerBrandId(sellerBrandId)
                .setProjectName(dto.getProjectName())
                .setProjectStarts(dto.getStarts())
                .setProjectEnds(dto.getEnds())
                .setProjectNo(projectNo)
                .setExtraContent(dto.getExtraContent())
                .setTotal(total)
                .setProjectStatus(ProposalProjectStatusEnum.DRAFT.ordinal()+"")
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioProposalMapper.insert(proposal);
    }
    private String getProjectNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
        String dateStr = sdf.format(new Date());
        String userStr = SecurityUserHelper.getCurrentPrincipal().getBrandId().substring(0,4).toUpperCase();
        String randomStr = RandomUtil.randomNumbers(4);
        return userStr + dateStr + randomStr;
    }

    @Override
    public void modifyProposal(StudioChangeProposalDTO dto) {
        // validate tags to json  arr
        try {
            new JSONArray(dto.getServices());
        } catch (JSONException ne) {
            throw new QuickMessageException("services 字段传参不合法");
        }
        Gson gson = new Gson();
        ProposalServiceItem[] services = gson.fromJson(dto.getServices(), ProposalServiceItem[].class);
        if(services.length==0){
            throw new QuickMessageException("未找到服务与费用信息");
        }

        boolean existsBlankServiceName = Arrays.stream(services).anyMatch(a -> CharSequenceUtil.isBlank(a.getServiceName()));
        if(existsBlankServiceName){
            throw new QuickMessageException("服务与费用信息存在服务项目名称未填写");
        }

        long haveZeroOrNegativeFeeServiceItem = Arrays.stream(services).filter(a -> BigDecimal.ZERO.compareTo(a.getQuantity().multiply(a.getPrice())) >= 0).count();

        if(haveZeroOrNegativeFeeServiceItem>0){
            throw new QuickMessageException("服务费用必须大于0");
        }
        BigDecimal total = Arrays.stream(services).map(a -> a.getPrice().multiply(a.getQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);


        // validate role
        Proposal proposal = studioProposalMapper.selectById(dto.getId());
        if(proposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(!currentBrandId.equals(proposal.getSellerBrandId())){
            throw new QuickMessageException("授权失败");
        }
        if(!(ProposalProjectStatusEnum.DRAFT.ordinal()+"").equals(proposal.getProjectStatus())){
            throw new QuickMessageException("提案状态验证失败");
        }
        // update
        proposal.setModifiedAt(new Date())
                .setServices(dto.getServices())
                .setExtraContent(dto.getExtraContent())
                .setProjectStarts(dto.getStarts())
                .setProjectEnds(dto.getEnds())
                .setTotal(total)
                .setProjectName(dto.getProjectName());

        studioProposalMapper.updateById(proposal);

    }

    @Override
    public IPage<StudioFetchProposalPageRO> findProposals(StudioFetchProposalPageDTO dto) {

        String sellerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Page<StudioFetchProposalPageRO>  page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioProposalMapper.selectPageByQ(page,dto,sellerBrandId);

    }

    @Override
    public void duplicateOneProposal(String id) {
        // validate role
        Proposal targetProposal = studioProposalMapper.selectById(id);
        if(targetProposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(!currentBrandId.equals(targetProposal.getSellerBrandId())){
            throw new QuickMessageException("未拥有提案副本权限，创建副本失败");
        }
        String projectNo= getProjectNo();
        Proposal dupProposal=new Proposal();
        dupProposal.setId(IdUtil.simpleUUID())
                .setServices(targetProposal.getServices())
                .setSellerBrandId(currentBrandId)
                .setProjectName(targetProposal.getProjectName())
                .setProjectStarts(targetProposal.getProjectStarts())
                .setProjectEnds(targetProposal.getProjectEnds())
                .setProjectNo(projectNo)
                .setExtraContent(targetProposal.getExtraContent())
                .setTotal(targetProposal.getTotal())
                .setProjectStatus(ProposalProjectStatusEnum.DRAFT.ordinal()+"")
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioProposalMapper.insert(dupProposal);


    }

    @Override
    public void modifyProjectStatus(StudioChangeProposalStatusDTO dto) {
        // validate status
        boolean checked = (ProposalProjectStatusEnum.SUSPENDED.ordinal()+"").equals(dto.getProjectStatus())
                || (ProposalProjectStatusEnum.COMPLETED.ordinal()+"").equals(dto.getProjectStatus())
                || (ProposalProjectStatusEnum.DELIVERING.ordinal()+"").equals(dto.getProjectStatus());
        if(!checked){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // validate role
        Proposal targetProposal = studioProposalMapper.selectById(dto.getId());
        if(targetProposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(!currentBrandId.equals(targetProposal.getSellerBrandId())){
            throw new QuickMessageException("未拥有修订权限");
        }
        if((ProposalProjectStatusEnum.DRAFT.ordinal()+"").equals(targetProposal.getProjectStatus())){
            throw new QuickMessageException("提案状态验证失败");
        }
        targetProposal.setProjectStatus(dto.getProjectStatus())
                .setModifiedAt(new Date());

        studioProposalMapper.updateById(targetProposal);

    }

    @Override
    public void delOneProposal(String id) {
        // validate role
        Proposal targetProposal = studioProposalMapper.selectById(id);
        if(targetProposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(!currentBrandId.equals(targetProposal.getSellerBrandId())){
            throw new QuickMessageException("未拥有修订权限");
        }
        if(!(ProposalProjectStatusEnum.DRAFT.ordinal()+"").equals(targetProposal.getProjectStatus())){
            throw new QuickMessageException("提案状态验证失败");
        }

        studioProposalMapper.deleteById(id);

    }

    @Override
    public StudioFetchOneProposalVO findOneProposal(String no) {

        StudioFetchOneProposalRO ro = studioProposalMapper.selectProposalByNo(no);
        StudioFetchOneProposalVO vo = new StudioFetchOneProposalVO();
        vo.setProposal(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public Proposal findOneProposalUsingId(String proposalId) {
        return studioProposalMapper.selectById(proposalId);
    }
}
