package com.careyq.alive.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.careyq.alive.core.domain.EntryVO;
import com.careyq.alive.core.exception.CustomException;
import com.careyq.alive.system.dto.RolePageDTO;
import com.careyq.alive.system.entity.Role;
import com.careyq.alive.system.mapper.RoleMapper;
import com.careyq.alive.system.mapper.RoleMenuMapper;
import com.careyq.alive.system.mapper.RoleUserMapper;
import com.careyq.alive.system.service.RoleService;
import com.careyq.alive.system.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.careyq.alive.system.constants.SystemResultCode.*;

/**
 * 角色服务实现
 *
 * @author CareyQ
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final RoleUserMapper roleUserMapper;
    private final RoleMenuMapper roleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveRole(RoleVO req) {
        // 检查角色名称是否已存在
        boolean exists = this.lambdaQuery()
                .ne(req.getId() != null, Role::getId, req.getId())
                .eq(Role::getName, req.getName())
                .exists();
        if (exists) {
            throw new CustomException(ROLE_NAME_DUPLICATE);
        }
        if (req.getId() != null) {
            this.checkRoleExists(req.getId());
        }
        Role role = BeanUtil.copyProperties(req, Role.class);
        this.saveOrUpdate(role);
        return role.getId();
    }

    @Override
    public IPage<RoleVO> getRolePage(RolePageDTO dto) {
        IPage<Role> page = this.lambdaQuery()
                .like(StrUtil.isNotBlank(dto.getName()), Role::getName, dto.getName())
                .orderByDesc(Role::getId)
                .page(new Page<>(dto.getCurrent(), dto.getSize()));
        return page.convert(e -> RoleVO.of(e.getId(), e.getName(), e.getRemark(), e.getIsDefault()));
    }

    @Override
    public RoleVO getRoleDetail(Long id) {
        Role role = this.checkRoleExists(id);
        return RoleVO.of(role.getId(), role.getName(), role.getRemark(), role.getIsDefault());
    }

    @Override
    public void delRole(Long id) {
        roleUserMapper.delByRole(id);
        roleMenuMapper.delByRole(id);
        this.removeById(id);
    }

    @Override
    public List<EntryVO> getRoleSimpleList() {
        return this.lambdaQuery()
                .list().stream()
                .map(e -> new EntryVO(e.getId(), e.getName()))
                .toList();
    }

    @Override
    public void changeDefault(Long id, Boolean isDefault) {
        this.checkRoleExists(id);
        this.lambdaUpdate().set(Role::getIsDefault, isDefault).eq(Role::getId, id).update();
    }

    /**
     * 校验角色是否存在
     *
     * @param id 角色 ID
     * @return 角色
     */
    private Role checkRoleExists(Long id) {
        if (id == null) {
            return null;
        }
        Role role = this.getById(id);
        if (role == null) {
            throw new CustomException(ROLE_NOT_EXISTS);
        }
        return role;
    }

}
