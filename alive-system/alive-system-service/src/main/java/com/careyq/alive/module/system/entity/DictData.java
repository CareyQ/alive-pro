package com.careyq.alive.module.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.careyq.alive.core.domain.BaseEntity;
import lombok.Data;

/**
 * 字典数据
 *
 * @author CareyQ
 */
@Data
@TableName("system_dict_data")
public class DictData extends BaseEntity {

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 颜色类型
     */
    private String colorType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Integer status;

}
