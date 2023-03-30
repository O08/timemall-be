package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.MarketObject;
import com.norm.timemall.app.base.mo.MarketObjectRecord;
import com.norm.timemall.app.base.mo.Millstone;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.mall.domain.pojo.InsertOrderParameter;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamObj2RO;
import com.norm.timemall.app.team.domain.ro.TeamObj3RO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import com.norm.timemall.app.team.mapper.*;
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

    @Autowired
    private TeamMillstoneMapper teamMillstoneMapper;
    @Autowired
    private TeamOrderDetailsMapper teamOrderDetailsMapper;

    @Autowired
    private AccountService accountService;

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
                                .setQuantity(dto.getPartnerCellQuantity())
                                        .setSbu(dto.getPartnerCellSbu())
                                                .setObjectVal(partnerCellRO.getPrice().multiply(new BigDecimal(dto.getPartnerCellQuantity())));

        String swapNo = IdUtil.simpleUUID();// generate swap no
        MarketObjectRecord sponsorRecord = new MarketObjectRecord();
        sponsorRecord.setId(IdUtil.simpleUUID())
                .setSwapNo(swapNo)
                .setObjId(sponsorObj.getId())
                .setMark(ObjectRecordMarkEnum.COOPERATION.getMark())
                        .setTag(ObjectRecordTagEnum.CREATED.getMark())
                .setOd(ObjectRecordOdEnum.SPONSOR.getMark())
                                .setCreditId(dto.getPartner())
                                        .setDebitId(dto.getSponsor())
                                                .setCreateAt(new Date())
                                                        .setModifiedAt(new Date());
        MarketObjectRecord partnerRecord = new MarketObjectRecord();
        partnerRecord.setId(IdUtil.simpleUUID())
                .setSwapNo(swapNo)
                .setObjId(partnerObj.getId())
                .setMark(ObjectRecordMarkEnum.COOPERATION.getMark())
                .setTag(ObjectRecordTagEnum.CREATED.getMark())
                .setOd(ObjectRecordOdEnum.TARGET.getMark())
                        .setCreditId(dto.getSponsor())
                                .setDebitId(dto.getPartner())
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

    @Override
    public void modifyObjSalePrice(TeamObjPricingDTO dto) {
        teamMarketObjectMapper.updateSalePriceById(dto);
    }

    @Override
    public IPage<TeamObjRO> findtodoObjs(TeamTodoObjPageDTO dto) {
        String brandId = dto.getBrandId();
        IPage<TeamObjRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamMarketObjectMapper.selectPagebyTagAndDebitId(page,ObjectRecordMarkEnum.OWNED.getMark(),brandId);
    }

    @Override
    public void markObj(TeamMarkObjDTO dto) {
        teamMarketObjectRecordMapper.updateMarkBySwapNo(dto);
    }

    @Override
    public TeamObjRO findObj(TeamObjDTO dto) {
        return teamMarketObjectMapper.selectOneObjBySwapNoAndOd(dto);
    }

    @Override
    public void useObj(String objId) {
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        String brandId = accountService.
                findBrandInfoByUserId(user.getUserId())
                .getId();
        TeamObj3RO teamObj3RO = teamMarketObjectMapper.selectObjInfoByObjId(objId);
        // check
        if(teamObj3RO==null || !brandId.equals(teamObj3RO.getCreditId()) ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // 增加新订单
        String orderId = IdUtil.simpleUUID();
        InsertOrderParameter parameter = new InsertOrderParameter()
                .setId(orderId)
                .setUserId(user.getUserId())
                .setUsername(user.getUsername())
                .setCellId(teamObj3RO.getCellId())
                .setQuantity(teamObj3RO.getQuantity())
                .setSbu(teamObj3RO.getSbu())
                .setOrderType(OrderTypeEnum.MALL.getMark());
        teamOrderDetailsMapper.insertNewOrder(parameter);

        // 增加该订单对应的空Workflow
        Millstone millstone = new Millstone();
        millstone.setOrderId(orderId)
                .setMark(WorkflowMarkEnum.IN_QUEUE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamMillstoneMapper.insert(millstone);

        // tag to used
        teamMarketObjectRecordMapper.updateTagByObjId(teamObj3RO.getId(), ObjectRecordTagEnum.IN_USE.getMark());
    }

    @Override
    public TeamObjRO findObjInfo(String objId) {
        return teamMarketObjectMapper.selectOneObjById(objId);
    }
}
