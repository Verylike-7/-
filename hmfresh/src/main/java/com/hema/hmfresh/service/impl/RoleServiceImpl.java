package com.hema.hmfresh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hema.hmfresh.mapper.RoleMapper;
import com.hema.hmfresh.pojo.Role;
import com.hema.hmfresh.pojo.RoleMenu;
import com.hema.hmfresh.service.RoleMenuService;
import com.hema.hmfresh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    protected RoleMenuService roleMenuService;

    @Override
    @Transactional
    public void deleteRole(Long id) {
        // 删除角色
        removeById(id);
        // 删除角色对应菜单权限
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", id));
    }

    @Override
    @Transactional
    public void updateRoleMenu(Role role) {
        // 获取角色id
        Long roleId = role.getId();
        if (roleId == null) {
            return;
        }
        // 删除旧的
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        // 添加新的
        List<RoleMenu> list = role.getMenuIds().stream().map(menuId -> new RoleMenu(roleId, menuId)).collect(Collectors.toList());
        roleMenuService.saveBatch(list);
    }
}
