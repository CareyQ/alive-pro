package com.careyq.alive.module.system.vo;

import com.careyq.alive.module.system.dto.RoleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 角色 VO
 *
 * @author CareyQ
 */
@Data
@NoArgsConstructor
@Schema(description = "管理后台 - 角色 VO")
public class RoleVO extends RoleDTO {

    @Schema(description = "是否是默认")
    private Boolean isDefault;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    public RoleVO(Long id, String name, String code, String remark, Boolean isDefault, LocalDateTime createTime) {
        super(id, name, code, remark);
        this.isDefault = isDefault;
        this.createTime = createTime;
    }
}
