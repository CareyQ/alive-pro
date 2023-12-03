package com.careyq.alive.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.careyq.alive.core.util.CollUtils;
import com.careyq.alive.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息 mapper
 *
 * @author CareyQ
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    default List<Role> getByIds(List<Long> ids) {
        if (CollUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }
        return this.selectList(new LambdaQueryWrapper<Role>().in(Role::getId, ids));
    }
}
