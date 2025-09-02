package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.MpsTemplate;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplate;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplateDetailRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsTemplateRO;
import com.norm.timemall.app.studio.mapper.StudioMpsTemplateMapper;
import com.norm.timemall.app.studio.service.StudioMpsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class StudioMpsTemplateServiceImpl implements StudioMpsTemplateService {
    @Autowired
    private StudioMpsTemplateMapper studioMpsTemplateMapper;
    @Override
    public StudioFetchMpsTemplate findMpsTemplate(String chainId) {

        ArrayList<StudioFetchMpsTemplateRO>  records= studioMpsTemplateMapper.selectMpsTemplateListByChainId(chainId);
        StudioFetchMpsTemplate template = new StudioFetchMpsTemplate();
        template.setRecords(records);

        return template;
    }

    @Override
    public StudioFetchMpsTemplateDetailRO findTemplateDetail(String id) {

        StudioFetchMpsTemplateDetailRO detail = studioMpsTemplateMapper.selectTemplateDetailById(id);

        return detail;
    }

    @Override
    public void newMpsTemplate(StudioNewMpsTemplateDTO dto) {

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
        MpsTemplate template = new MpsTemplate();
        template.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setSow(dto.getSow())
                .setPiece(dto.getPiece())
                .setBonus(dto.getBonus())
                .setFirstSupplier(dto.getFirstSupplier())
                .setDuration(dto.getDuration())
                .setChainId(dto.getChainId())
                .setDeliveryCycle(dto.getDeliveryCycle())
                .setContractValidityPeriod(dto.getContractValidityPeriod())
                .setSkills(dto.getSkills())
                .setDifficulty(dto.getDifficulty())
                .setExperience(dto.getExperience())
                .setLocation(dto.getLocation())
                .setBidElectricity(dto.getBidElectricity())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioMpsTemplateMapper.insert(template);

    }

    @Override
    public void modifyMpsTemplate(StudioPutMpsTemplateDTO dto) {

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
        LambdaUpdateWrapper<MpsTemplate> updateWrapper = Wrappers.lambdaUpdate();
        // fix duration null value
        if(ObjectUtil.isNull(dto.getDuration())){
            updateWrapper.set(MpsTemplate::getDuration,null);
        }

        updateWrapper.eq(MpsTemplate::getId,dto.getId());
        MpsTemplate template = new MpsTemplate();
        template.setId(dto.getId())
                .setTitle(dto.getTitle())
                .setSow(dto.getSow())
                .setPiece(dto.getPiece())
                .setBonus(dto.getBonus())
                .setFirstSupplier(dto.getFirstSupplier())
                .setDuration(dto.getDuration())
                .setChainId(dto.getChainId())
                .setDeliveryCycle(dto.getDeliveryCycle())
                .setContractValidityPeriod(dto.getContractValidityPeriod())
                .setSkills(dto.getSkills())
                .setDifficulty(dto.getDifficulty())
                .setExperience(dto.getExperience())
                .setLocation(dto.getLocation())
                .setBidElectricity(dto.getBidElectricity())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioMpsTemplateMapper.update(template, updateWrapper);
    }

    @Override
    public void delTemplate(String id) {
        studioMpsTemplateMapper.deleteById(id);
    }
}
