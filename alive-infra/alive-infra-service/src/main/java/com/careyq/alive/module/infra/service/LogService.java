package com.careyq.alive.module.infra.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.careyq.alive.module.infra.dto.*;
import com.careyq.alive.module.infra.vo.LoginLogPageVO;
import com.careyq.alive.module.infra.vo.OperateLogPageVO;

/**
 * 日志服务
 *
 * @author CareyQ
 */
public interface LogService {

    /**
     * 保存登录日志
     *
     * @param dto 登录日志
     */
    void createLoginLog(LoginLogDTO dto);

    /**
     * 查询登录日志分页
     *
     * @param dto 分页请求筛选项
     * @return 日志分页
     */
    IPage<LoginLogPageVO> getLoginLogPage(LoginLogPageDTO dto);

    /**
     * 创建操作日志
     *
     * @param dto 操作日志
     */
    void createOperateLog(OperateLogDTO dto);

    /**
     * 创建错误日志
     *
     * @param dto 错误日志
     */
    void createErrorLog(ErrorLogDTO dto);

    /**
     * 查询操作日志分页
     *
     * @param dto 分页请求筛选项
     * @return 日志分页
     */
    IPage<OperateLogPageVO> getOperateLogPage(OperateLogPageDTO dto);
}
