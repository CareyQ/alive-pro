package com.careyq.alive.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.careyq.alive.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 mapper
 *
 * @author CareyQ
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
