package com.careyq.alive.system.convert;

import com.careyq.alive.system.dto.OperateLogDTO;
import com.careyq.alive.system.entity.LoginLog;
import com.careyq.alive.system.entity.OperateLog;
import com.careyq.alive.system.vo.LoginLogVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 日志相关转换器
 *
 * @author CareyQ
 */
@Mapper
public interface LogConvert {

    LogConvert INSTANCE = Mappers.getMapper(LogConvert.class);

    /**
     * 错误日志转换为 VO
     *
     * @param loginLog LoginLog
     * @return VO
     */
    @Mapping(target = "loginTime", source = "createTime")
    LoginLogVO convert(LoginLog loginLog);

    /**
     * 操作日志转换
     *
     * @param dto DTO
     * @return 实体
     */
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "isDel", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    OperateLog convert(OperateLogDTO dto);
}
