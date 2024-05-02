package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.CellMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.pojo.ro.CellIntroRO;
import com.norm.timemall.app.base.pojo.vo.CellIntroVO;
import com.norm.timemall.app.studio.domain.dto.StudioCellIntroContentDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCellOverViewDTO;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
import com.norm.timemall.app.studio.mapper.StudioCellMapper;
import com.norm.timemall.app.studio.service.StudioCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioCellServiceImpl implements StudioCellService {

    @Autowired
    private StudioCellMapper studioCellMapper;
    @Override
    public IPage<CellInfoRO> findCells(String brandId, PageDTO cellPageDTO) {
        IPage<CellInfoRO> page = new Page<>();
        page.setSize(cellPageDTO.getSize());
        page.setCurrent(cellPageDTO.getCurrent());
        IPage<CellInfoRO> ro = studioCellMapper.selectCellPage(page, brandId);
        return ro;
    }

    @Override
    public void modifyTitleAndCanProvideInvoice(String cellId,String userId, StudioCellOverViewDTO dto) {
        // validate tags to json  arr
        try {
            new JSONArray(dto.getOverview().getTags());
        } catch (JSONException ne) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        studioCellMapper.updateTitleInvoiceTagById(cellId,userId,dto.getOverview());
    }

    @Override
    public Cell findSingleCell(String cellId) {
        return studioCellMapper.selectById(cellId);
    }

    @Override
    public void modifyCellCover(String cellId, String uri) {
        studioCellMapper.updateCoverById(cellId,uri);
    }

    @Override
    public void modifyIntroCover(String cellId, String uri) {
        studioCellMapper.updateIntroCoverById(cellId,uri);
    }

    @Override
    public void modifyCellContent(String cellId, StudioCellIntroContentDTO dto) {
        Gson gson = new Gson();
        String content = gson.toJson(dto.getContent());
        studioCellMapper.updateCoverContentById(cellId,content);
    }

    @Override
    public void markCell(String cellId, String code) {
        studioCellMapper.updateMarkById(cellId,code);

    }

    @Override
    public String initCell(String brandId) {
        Cell cell = new Cell();
        cell.setBrandId(brandId)
                .setMark(CellMarkEnum.DRAFT.getMark())
                .setId(IdUtil.simpleUUID())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioCellMapper.insert(cell);
        return cell.getId();
    }

    @Override
    public void trashCell(String cellId) {
        LambdaQueryWrapper<Cell> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Cell::getId,cellId);
        wrapper.eq(Cell::getMark, CellMarkEnum.DRAFT.getMark());
        studioCellMapper.delete(wrapper);
    }

    @Override
    public CellIntroVO findCellProfileInfo(String cellId) {
        CellIntroRO intro = studioCellMapper.selectCellProfileInfo(cellId);
        CellIntroVO result = new CellIntroVO().setResponseCode(CodeEnum.SUCCESS)
                .setProfile(intro);
        return result;
    }

}
