package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.ObjectRecordMarkEnum;
import com.norm.timemall.app.base.enums.ObjectRecordTagEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.MarketObject;
import com.norm.timemall.app.base.mo.MarketObjectRecord;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
import com.norm.timemall.app.team.domain.dto.TeamObjPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamOwnedObjPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamSwapCellDTO;
import com.norm.timemall.app.team.domain.dto.TeamTagObjDTO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import com.norm.timemall.app.team.mapper.TeamCellMapper;
import com.norm.timemall.app.team.mapper.TeamMarketObjectMapper;
import com.norm.timemall.app.team.mapper.TeamMarketObjectRecordMapper;
import com.norm.timemall.app.team.service.TeamObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TeamObjServiceImpl implements TeamObjService {
    @Autowired
    private TeamMarketObjectMapper teamMarketObjectMapper;
    @Autowired
    private TeamMarketObjectRecordMapper teamMarketObjectRecordMapper;
    @Autowired
    private TeamCellMapper teamCellMapper;

    @Override
    public IPage<TeamObjRO> findObjs(TeamObjPageDTO dto) {
        IPage<TeamObjRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return  teamMarketObjectMapper.selectPageByQ(page,dto.getQ());

    }

    @Override
    public void swapCell(TeamSwapCellDTO dto) {
        // query cell info
        CellInfoRO sponsorCellRO = teamCellMapper.selectCellByIdAndSbu(dto.getSponsorCellId(), dto.getSponsorCellSbu());
        CellInfoRO partnerCellRO = teamCellMapper.selectCellByIdAndSbu(dto.getPartnerCellId(),dto.getPartnerCellSbu());

        if(sponsorCellRO==null || partnerCellRO ==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        MarketObject sponsorObj = new MarketObject();
        sponsorObj.setId(IdUtil.simpleUUID())
                        .setCellId(sponsorCellRO.getId())
                                .setQuantity(dto.getSponsorCellQuantity())
                                        .setSbu(dto.getSponsorCellSbu())
                                                .setObjectVal(sponsorCellRO.getPrice().multiply(new BigDecimal(dto.getSponsorCellQuantity())));
        MarketObject partnerObj = new MarketObject();
        partnerObj.setId(IdUtil.simpleUUID())
                        .setCellId(dto.getPartnerCellId())
                                .setQuantity(dto.getPartnerrCellQuantity())
                                        .setSbu(dto.getPartnerCellSbu())
                                                .setObjectVal(partnerCellRO.getPrice().multiply(new BigDecimal(dto.getPartnerrCellQuantity())));

        String swapNo = IdUtil.simpleUUID();// generate swap no
        MarketObjectRecord sponsorRecord = new MarketObjectRecord();
        sponsorRecord.setId(IdUtil.simpleUUID())
                .setSwapNo(swapNo)
                .setObjId(sponsorObj.getId())
                .setMark(ObjectRecordMarkEnum.COOPERATION.getMark())
                        .setTag(ObjectRecordTagEnum.CREATED.getMark())
                                .setCreditId(dto.getSponsor())
                                        .setDebitId(dto.getPartner())
                                                .setCreateAt(new Date())
                                                        .setModifiedAt(new Date());
        MarketObjectRecord partnerRecord = new MarketObjectRecord();
        partnerRecord.setId(IdUtil.simpleUUID())
                .setSwapNo(swapNo)
                .setObjId(partnerObj.getId())
                .setMark(ObjectRecordMarkEnum.COOPERATION.getMark())
                .setTag(ObjectRecordTagEnum.CREATED.getMark())
                        .setCreditId(dto.getPartner())
                                .setDebitId(dto.getSponsor())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        // insert sponsor obj
        teamMarketObjectMapper.insert(sponsorObj);
        // insert partner obj
        teamMarketObjectMapper.insert(partnerObj);

        // insert sponsor record
        teamMarketObjectRecordMapper.insert(sponsorRecord);
        // insert partner record
        teamMarketObjectRecordMapper.insert(partnerRecord);

    }

    @Override
    public IPage<TeamObjRO> findOwnedObjs(TeamOwnedObjPageDTO dto) {
        String brandId = dto.getBrandId();
        IPage<TeamObjRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return  teamMarketObjectMapper.selectPagebyTagAndCreditId(page,dto.getMark(),brandId);
    }

    @Override
    public void tagObj(TeamTagObjDTO dto) {
        teamMarketObjectRecordMapper.updateTagByObjId(dto.getObjId(),dto.getTag());
    }
}
