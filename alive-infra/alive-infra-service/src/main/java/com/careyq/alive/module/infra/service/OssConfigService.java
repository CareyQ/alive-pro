package com.careyq.alive.module.infra.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careyq.alive.mybatis.core.service.IServiceX;
import com.careyq.alive.module.infra.entity.OssConfig;
import com.careyq.alive.module.infra.dto.*;
import com.careyq.alive.module.infra.vo.*;

/**
 * 对象存储配置 服务
 *
 * @author CareyQ
 */
public interface OssConfigService extends IServiceX<OssConfig> {

    /**
     * 保存对象存储配置
     *
     * @param dto 对象存储配置
     * @return 编号
     */
    Long saveOssConfig(OssConfigDTO dto);

    /**
     * 获取对象存储配置分页
     *
     * @param dto 分页筛选项
     * @return 分页
     */
    IPage<OssConfigPageVO> getOssConfigPage(OssConfigPageDTO dto);

    /**
     * 获取对象存储配置详情
     *
     * @param id 编号
     * @return 对象存储配置
     */
    OssConfigVO getOssConfigDetail(Long id);

    /**
     * 删除对象存储配置
     *
     * @param id 编号
     */
    void delOssConfig(Long id);
}
