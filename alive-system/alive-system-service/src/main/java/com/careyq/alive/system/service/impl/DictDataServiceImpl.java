package com.careyq.alive.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.careyq.alive.core.enums.CommonStatusEnum;
import com.careyq.alive.core.exception.CustomException;
import com.careyq.alive.system.dto.DictDataPageDTO;
import com.careyq.alive.system.entity.DictData;
import com.careyq.alive.system.entity.DictType;
import com.careyq.alive.system.mapper.DictDataMapper;
import com.careyq.alive.system.mapper.DictTypeMapper;
import com.careyq.alive.system.service.DictDataService;
import com.careyq.alive.system.vo.DictDataVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.careyq.alive.system.constants.SystemResultCode.*;

/**
 * 字典数据服务实现
 *
 * @author CareyQ
 */
@Service
@AllArgsConstructor
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements DictDataService {

    private final DictTypeMapper dictTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveDictData(DictDataVO req) {
        if (req.getId() != null) {
            this.checkDataExists(req.getId());
        }
        boolean existsType = dictTypeMapper.exists(new LambdaQueryWrapper<DictType>()
                .eq(DictType::getType, req.getDictType()));
        if (!existsType) {
            throw new CustomException(DICT_TYPE_NOT_EXISTS);
        }
        boolean exists = this.lambdaQuery()
                .ne(req.getId() != null, DictData::getId, req.getId())
                .eq(DictData::getLabel, req.getLabel())
                .exists();
        if (exists) {
            throw new CustomException(DICT_DATA_LABEL_DUPLICATE);
        }
        DictData dictData = BeanUtil.copyProperties(req, DictData.class);
        this.saveOrUpdate(dictData);
        return dictData.getId();
    }

    @Override
    public IPage<DictDataVO> getDictDataPage(DictDataPageDTO dto) {
        IPage<DictData> page = this.lambdaQuery()
                .like(StrUtil.isNotBlank(dto.getLabel()), DictData::getLabel, dto.getLabel())
                .eq(StrUtil.isNotEmpty(dto.getDictType()), DictData::getDictType, dto.getDictType())
                .eq(dto.getStatus() != null, DictData::getStatus, dto.getStatus())
                .orderByDesc(DictData::getId)
                .page(new Page<>(dto.getCurrent(), dto.getSize()));
        if (page.getRecords().isEmpty()) {
            return new Page<>();
        }
        return page.convert(e -> DictDataVO.of(e.getId(), e.getLabel(), e.getValue(), e.getDictType(), e.getColorType(), e.getRemark(), e.getStatus()));
    }

    @Override
    public DictDataVO getDictDataDetail(Long id) {
        DictData dictData = this.checkDataExists(id);
        return BeanUtil.copyProperties(dictData, DictDataVO.class);
    }

    @Override
    public void delDictData(Long id) {
        this.checkDataExists(id);
        this.removeById(id);
    }

    /**
     * 校验字典数据是否存在
     *
     * @param id 字典数据 ID
     * @return 字典数据
     */
    private DictData checkDataExists(Long id) {
        if (id == null) {
            return null;
        }
        DictData data = this.getById(id);
        if (data == null) {
            throw new CustomException(DICT_DATA_NOT_EXISTS);
        }
        return data;
    }

    @Override
    public Map<String, List<DictDataVO>> getDictDataMap() {
        return this.lambdaQuery()
                .eq(DictData::getStatus, CommonStatusEnum.ENABLE.getStatus())
                .list().stream()
                .map(e -> DictDataVO.of(e.getId(), e.getLabel(), e.getValue(), e.getDictType(), e.getColorType(), e.getRemark(), e.getStatus()))
                .collect(Collectors.groupingBy(DictDataVO::getDictType));
    }
}
