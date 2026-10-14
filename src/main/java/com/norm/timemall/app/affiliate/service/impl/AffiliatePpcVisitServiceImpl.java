package com.norm.timemall.app.affiliate.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.PpcNewVisitDTO;
import com.norm.timemall.app.affiliate.domain.dto.PpcVisitPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcVisitPageRO;
import com.norm.timemall.app.affiliate.mapper.AffiliatePpcVisitMapper;
import com.norm.timemall.app.affiliate.service.AffiliatePpcVisitService;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.PpcVisit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AffiliatePpcVisitServiceImpl implements AffiliatePpcVisitService {
    @Autowired
    private AffiliatePpcVisitMapper affiliatePpcVisitMapper;

    private final String VALID_VISIT="1";
    private final String VISIT_UN_PAY="0";
    private final BigDecimal VISIT_PRICE=BigDecimal.valueOf(0.02);
    @Override
    public void newVisitLog(PpcNewVisitDTO dto) {

        PpcVisit visit=new PpcVisit();
        visit.setId(IdUtil.simpleUUID())
                .setIp(dto.getIp())
                .setTrackCode(dto.getTrackCode())
                .setDeviceInfo(dto.getDeviceInfo())
                .setLinkAddress(dto.getLinkAddress())
                .setSourceAddress(dto.getSourceAddress())
                .setValid(VALID_VISIT)
                .setPay(VISIT_UN_PAY)
                .setPrice(VISIT_PRICE)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        affiliatePpcVisitMapper.insert(visit);

    }

    @Override
    public IPage<PpcVisitPageRO> findVisitPage(PpcVisitPageDTO dto) {

        IPage<PpcVisitPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return affiliatePpcVisitMapper.selectPageByDTO(page, SecurityUserHelper.getCurrentPrincipal().getBrandId(), dto);

    }

    @Override
    public ByteArrayInputStream loadPpcVisitRecord(PpcVisitPageDTO dto) {

        List<PpcVisitPageRO> visits= affiliatePpcVisitMapper.selectPpcVisitRecord(dto,SecurityUserHelper.getCurrentPrincipal().getBrandId());
        ByteArrayInputStream in = visitsToCSV(visits);
        return in;

    }
    private ByteArrayInputStream visitsToCSV(List<PpcVisitPageRO> records) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            // 表头
            List<String> csvHeader = Arrays.asList(
                    "访问时间",
                    "来源",
                    "访问IP",
                    "推广链接",
                    "采购价格",
                    "合法标识",
                    "结算标识",
                    "结算批次"
            );
            csvPrinter.printRecord(csvHeader);

            for (PpcVisitPageRO record : records) {

                List<String> data = Arrays.asList(
                        record.getCreateAt(),
                        record.getSourceAddress(),
                        record.getIp(),
                        record.getLinkAddress(),
                        record.getPrice(),
                        record.getValid(),
                        record.getPay(),
                        record.getBatch()
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
