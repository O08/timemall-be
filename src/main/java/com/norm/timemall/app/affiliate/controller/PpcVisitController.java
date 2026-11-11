package com.norm.timemall.app.affiliate.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.PpcNewVisitDTO;
import com.norm.timemall.app.affiliate.domain.dto.PpcVisitPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcVisitPageRO;
import com.norm.timemall.app.affiliate.domain.vo.PpcVisitPageVO;
import com.norm.timemall.app.affiliate.service.AffiliatePpcVisitService;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.util.IpLocationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class PpcVisitController {
    @Autowired
    private AffiliatePpcVisitService affiliatePpcVisitService;

    @PostMapping("/api/v1/web_mall/ppc/visit")
    @ResponseBody
    public SuccessVO newUserVisitLog(@Validated @RequestBody PpcNewVisitDTO dto,
                                     HttpServletRequest request){

        String ipAddress = IpLocationUtil.getIpAddress(request);
        dto.setIp(ipAddress);
        affiliatePpcVisitService.newVisitLog(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @GetMapping("/api/v1/web/affiliate/ppc/visit")
    @ResponseBody
    public PpcVisitPageVO fetchPpcVisitPage(@Validated PpcVisitPageDTO dto){

        IPage<PpcVisitPageRO> visit=affiliatePpcVisitService.findVisitPage(dto);
        PpcVisitPageVO vo =new PpcVisitPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setVisit(visit);
        return vo;

    }

    @GetMapping("/api/v1/web/affiliate/ppc/visit/download")
    public ResponseEntity<Resource> getFile( PpcVisitPageDTO dto) throws UnsupportedEncodingException {

        String filename =  "PPC访问明细-ppc-visit-record.csv";
        InputStreamResource file = new InputStreamResource(affiliatePpcVisitService.loadPpcVisitRecord(dto));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(filename,"UTF-8"))
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);

    }



}
