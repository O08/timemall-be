package com.norm.timemall.app.marketing.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseSequenceMapper;
import com.norm.timemall.app.base.mo.MarketPuzzleDream;
import com.norm.timemall.app.base.mo.MarketPuzzleEvent;
import com.norm.timemall.app.base.mo.MarketPuzzleLikes;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.marketing.domain.dto.MktShareStoryDTO;
import com.norm.timemall.app.marketing.domain.pojo.MktFetchDreams;
import com.norm.timemall.app.marketing.domain.pojo.MktFetchPuzzleInfo;
import com.norm.timemall.app.marketing.domain.ro.MktFetchDreamsRO;
import com.norm.timemall.app.marketing.mapper.MktMarketPuzzleDreamMapper;
import com.norm.timemall.app.marketing.mapper.MktMarketPuzzleEventMapper;
import com.norm.timemall.app.marketing.mapper.MktMarketPuzzleLikesMapper;
import com.norm.timemall.app.marketing.service.MktPuzzleService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
@Slf4j
@Service
public class MktPuzzleServiceImpl implements MktPuzzleService {
    @Autowired
    private MktMarketPuzzleEventMapper mktMarketPuzzleEventMapper;
    @Autowired
    private MktMarketPuzzleDreamMapper mktMarketPuzzleDreamMapper;
    @Autowired
    private MktMarketPuzzleLikesMapper mktMarketPuzzleLikesMapper;
    @Autowired
    private BaseSequenceMapper baseSequenceMapper;
    @Autowired
    private DefaultPayService defaultPayService;

    @Override
    public MktFetchPuzzleInfo findPuzzleInfo(String puzzleVersion) {

        MarketPuzzleEvent puzzleEvent = mktMarketPuzzleEventMapper.selectById(puzzleVersion);
        MktFetchPuzzleInfo puzzle=new MktFetchPuzzleInfo();
        String brand= !SecurityUserHelper.alreadyLogin() ? "" : SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if(puzzleEvent!=null){
            puzzle.setOd(puzzleEvent.getOd()==null ? "" : puzzleEvent.getOd()+"");
            puzzle.setTag(puzzleEvent.getTag());
            puzzle.setWinner(puzzleEvent.getWinner());
            puzzle.setBeginAt(DateUtil.format(puzzleEvent.getBeginAt(), "yyyy-MM-dd HH:mm:ss"));
            puzzle.setEndAt(DateUtil.format(puzzleEvent.getEndAt(), "yyyy-MM-dd HH:mm:ss"));
            puzzle.setCurrentBrand(brand);
            puzzle.setPieceWhere("");
        }
        if(puzzleEvent!=null && ObjectUtil.isNotEmpty(puzzleEvent.getWinner())
                && MarketPuzzleEventTagEnum.CREATED.getMark().equals(puzzleEvent.getTag())
                && brand.equals(puzzleEvent.getWinner())){

            puzzle.setPieceWhere(puzzleEvent.getKeyWhere());

        }

        return puzzle;

    }

    @Override
    public MktFetchDreams findDreams(String q, String puzzleVersion) {

        ArrayList<MktFetchDreamsRO> records= mktMarketPuzzleDreamMapper.selectDreamListByQ(q,puzzleVersion);
        MktFetchDreams dream=new MktFetchDreams();
        dream.setRecords(records);
        return  dream;

    }

    @Override
    public void likeDream(String dreamId) {
        // insert like record
        MarketPuzzleLikes like=new MarketPuzzleLikes();
        like.setId(IdUtil.simpleUUID())
                .setDreamId(dreamId)
                .setLikesBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setCreateAt(new Date());
        mktMarketPuzzleLikesMapper.insert(like);
        // update likes cnt
        mktMarketPuzzleDreamMapper.incrementLikes(dreamId);

    }

    @Override
    public void createDreamStory(MktShareStoryDTO dto) {

        Long od = baseSequenceMapper.nextSequence(SequenceKeyEnum.PUZZLE_VERSION_ONE.getMark());

        MarketPuzzleDream dream =new MarketPuzzleDream();
        dream.setId(IdUtil.simpleUUID())
                .setContent(dto.getStory())
                .setEventId(dto.getPuzzleVersion())
                .setOd(od)
                .setLikes(0L)
                .setAuthorBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setCreateAt(new Date());
        mktMarketPuzzleDreamMapper.insert(dream);


    }

    @Override
    public void openTreasureBox(MultipartFile clueOne, MultipartFile clueTwo, String clueThree, String puzzleVersion) {

        MarketPuzzleEvent puzzleEvent = mktMarketPuzzleEventMapper.selectById(puzzleVersion);

        doOpenBox(clueOne,clueTwo,clueThree,puzzleEvent);

        // give bonus
        TransferBO bo = generateTransferBO(puzzleEvent.getBonus(),puzzleEvent.getId(),puzzleEvent.getSponsor());
        String transNo=defaultPayService.transfer(new Gson().toJson(bo));
        // update tag and winner
        puzzleEvent.setTag(MarketPuzzleEventTagEnum.ALREADY_OPEN_BOX.getMark());
        puzzleEvent.setWinner(SecurityUserHelper.getCurrentPrincipal().getBrandId());
        mktMarketPuzzleEventMapper.updateById(puzzleEvent);
        log.info(puzzleVersion+ "活动发放成功！transNo："+transNo);

    }


    private void  doOpenBox(MultipartFile clueOne, MultipartFile clueTwo, String clueThree,MarketPuzzleEvent puzzleEvent){
        if(puzzleEvent==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(MarketPuzzleEventTagEnum.ALREADY_OPEN_BOX.getMark().equals(puzzleEvent.getTag())){
            throw new ErrorCodeException(CodeEnum.MARKETING_OPENED_BOX);
        }
        if(MarketPuzzleEventTagEnum.END.getMark().equals(puzzleEvent.getTag())){
            throw new ErrorCodeException(CodeEnum.MARKETING_END);
        }
        String netOne= checkSum(clueOne);
        String netTwo= checkSum(clueTwo);
        String inputCheckSum=netOne+"|"+netTwo+"|"+clueThree;
        if(!puzzleEvent.getKeyPrint().equals(inputCheckSum)){
          throw new ErrorCodeException(CodeEnum.MARKETING_OPEN_FAIL);
       }

    }

    private TransferBO generateTransferBO(BigDecimal amount, String outNo,String payer){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setPayerAccount(payer)
                .setPayerType(FidTypeEnum.BRAND.getMark())
                .setTransType(TransTypeEnum.TRANSFER.getMark());
        return  bo;
    }
    private String checkSum(MultipartFile file){
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(file.getBytes());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String checksum = new BigInteger(1, hash).toString(16);
        return checksum;
    }
}
