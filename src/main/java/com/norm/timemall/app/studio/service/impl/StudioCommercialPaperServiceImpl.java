package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CommercialPaperTagEnum;
import com.norm.timemall.app.base.enums.ElectricityBusinessTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommercialPaper;
import com.norm.timemall.app.base.mo.MpsTemplate;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.BaseElectricityService;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaper;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDetail;
import com.norm.timemall.app.studio.domain.ro.StudioDiscoverMpsPaperPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperListRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperRO;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperMapper;
import com.norm.timemall.app.studio.mapper.StudioMpsMapper;
import com.norm.timemall.app.studio.mapper.StudioMpsTemplateMapper;
import com.norm.timemall.app.studio.service.StudioCommercialPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class StudioCommercialPaperServiceImpl implements StudioCommercialPaperService {
    @Autowired
    private StudioCommercialPaperMapper studioCommercialPaperMapper;
    @Autowired
    private StudioMpsTemplateMapper studioMpsTemplateMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private StudioMpsMapper studioMpsMapper;

    @Autowired
    private BaseElectricityService baseElectricityService;

    @Override
    public void generateMpsPaper(String chainId,String brandId,String mpsId) {
        List<CommercialPaper> paperList = getPaperList(chainId, brandId,mpsId);
        studioCommercialPaperMapper.insertBatchSomeColumn(paperList);
    }

    @Override
    public IPage<StudioFetchMpsPaperRO> findPaperPageForBrand(StudioFetchMpsPaperPageDTO dto) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        dto.setBrandId(brandId);

        IPage<StudioFetchMpsPaperRO> page=new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        IPage<StudioFetchMpsPaperRO> paper = studioCommercialPaperMapper.selectPaperPageForBrand(page,dto);

        return paper;
    }

    @Override
    public IPage<StudioDiscoverMpsPaperPageRO> discoverMpsPaper(StudioDiscoverMpsPaperPageDTO dto) {
        IPage<StudioDiscoverMpsPaperPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        IPage<StudioDiscoverMpsPaperPageRO> paper=studioCommercialPaperMapper.selectPaperPageForPublic(page,dto.getQ());
        return paper;
    }

    @Override
    public StudioFetchMpsPaperDetail findMpsPaperDetail(String id) {
        StudioFetchMpsPaperDetail detail = studioCommercialPaperMapper.selectPaperDetailById(id);
        return detail;
    }

    @Override
    public StudioFetchMpsPaper findPaperList(StudioFetchmpsPaperListDTO dto) {
        ArrayList<StudioFetchMpsPaperListRO> records = studioCommercialPaperMapper.selectPaperListByMpsId(dto.getMpsId());
        StudioFetchMpsPaper paper = new StudioFetchMpsPaper();
        paper.setRecords(records);
        return paper;
    }

    @Override
    public void modifyPaperTag(StudioPutMpsPaperTagDTO dto) {
        studioCommercialPaperMapper.updateTagById(dto);
    }

    @Override
    public void modifyPaper(StudioPutMpsPaperDTO dto) {

        studioCommercialPaperMapper.updateSowAndBonusById(dto);
    }

    @Override
    public void mpsPaperOrderReceiving(StudioMpsOrderReceivingDTO dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // check paper is validate
        CommercialPaper commercialPaper = studioCommercialPaperMapper.selectById(dto.getPaperId());
        if(commercialPaper==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Date deadLineDate=DateUtil.offsetDay(commercialPaper.getModifiedAt(),commercialPaper.getContractValidityPeriod());
        boolean paperAlreadyInvalid = DateUtil.compare(new Date(),deadLineDate)>1;
        if(paperAlreadyInvalid || !CommercialPaperTagEnum.PUBLISH.getMark().equals(commercialPaper.getTag())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // can't receive self paper, if current brand is purchaser, block
        if(commercialPaper.getPurchaser().equals(brandId)){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }
        // deduct electricity
        baseElectricityService.deduct(brandId,commercialPaper.getBidElectricity(),"接取商单扣除电力", ElectricityBusinessTypeEnum.DEDUCT.getMark(),  commercialPaper.getId(), "目标商单："+commercialPaper.getId());

        studioCommercialPaperMapper.updateTagAndSupplierById(dto.getPaperId(),CommercialPaperTagEnum.DELIVERING.getMark(),brandId);
    }

    @Override
    public void modifyPapersTag(String mpsId, String mark) {
        studioCommercialPaperMapper.updatePapersTagById(mpsId,mark);
    }

    @Override
    public void modifyPaperTagForCurrentUser(String paperId, String mark) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        studioCommercialPaperMapper.updateTagByPurchaserAndId(paperId,mark,brandId);

    }

    @Override
    public void generateFastMpsPaper(StudioNewFastMpsDTO dto, String brandId, String mpsId) {

        // validate json arr
        try {
            new JSONArray(dto.getSkills());
        } catch (JSONException ne) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String[] skills = new Gson().fromJson(dto.getSkills(), String[].class);
        if(skills.length>5){
            throw new QuickMessageException("技能项目最大可配置5项，配额超限请调整");
        }
        boolean existsBlankSkill = Arrays.stream(skills).anyMatch(CharSequenceUtil::isBlank);
        if(existsBlankSkill){
            throw new QuickMessageException("技能项目存在空值，校验不通过");
        }
        CommercialPaper paper = new CommercialPaper();
        paper.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setSow(dto.getSow())
                .setPiece(dto.getPiece())
                .setBonus(dto.getBonus())
                .setPurchaser(brandId)
                .setMpsId(mpsId)
                .setSupplier(dto.getFirstSupplier())
                .setDuration(dto.getDuration())
                .setDeliveryCycle(dto.getDeliveryCycle())
                .setContractValidityPeriod(dto.getContractValidityPeriod())
                .setSkills(dto.getSkills())
                .setDifficulty(dto.getDifficulty())
                .setExperience(dto.getExperience())
                .setLocation(dto.getLocation())
                .setBidElectricity(dto.getBidElectricity())
                .setTag(CommercialPaperTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioCommercialPaperMapper.insert(paper);

    }

    @Override
    public void emptySupplier(String id) {
        studioCommercialPaperMapper.updateSupplierAndTagAndBidAtById(id,CommercialPaperTagEnum.PUBLISH.getMark());
    }

    private List<CommercialPaper> getPaperList(String chainId,String brandId,String mpsId){

        LambdaQueryWrapper<MpsTemplate> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MpsTemplate::getChainId,chainId);
        List<MpsTemplate> mpsTemplates = studioMpsTemplateMapper.selectList(wrapper);
        if(mpsTemplates.size()==0){
            studioMpsMapper.deleteById(mpsId); // delete invalid mps
            throw new ErrorCodeException(CodeEnum.INVALID_MPS_CHAIN);
        }

        List<CommercialPaper> paperList =new ArrayList<>();
        mpsTemplates.stream().forEach(e->{
            CommercialPaper paper = new CommercialPaper();
            paper.setId(IdUtil.simpleUUID())
                    .setTitle(e.getTitle())
                    .setSow(e.getSow())
                    .setPiece(e.getPiece())
                    .setBonus(e.getBonus())
                    .setPurchaser(brandId)
                    .setMpsId(mpsId)
                    .setSupplier(e.getFirstSupplier())
                    .setDuration(e.getDuration())
                    .setDeliveryCycle(e.getDeliveryCycle())
                    .setContractValidityPeriod(e.getContractValidityPeriod())
                    .setTag(CommercialPaperTagEnum.CREATED.getMark())
                    .setTemplateId(e.getId())
                    .setSkills(e.getSkills())
                    .setDifficulty(e.getDifficulty())
                    .setExperience(e.getExperience())
                    .setLocation(e.getLocation())
                    .setBidElectricity(e.getBidElectricity())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            paperList.add(paper);
        });

        return  paperList;
    }
}
