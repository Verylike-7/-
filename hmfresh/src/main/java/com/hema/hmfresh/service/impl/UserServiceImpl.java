package com.hema.hmfresh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hema.hmfresh.mapper.UserMapper;
import com.hema.hmfresh.pojo.User;
import com.hema.hmfresh.pojo.UserRole;
import com.hema.hmfresh.service.RoleMenuService;
import com.hema.hmfresh.service.UserRoleService;
import com.hema.hmfresh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    protected RoleMenuService roleMenuService;
    @Autowired
    protected UserRoleService userRoleService;

    @Override
    @Transactional
    public void updateUserRole(User user) {
        Long userId = user.getId();
        if (userId == null) {
            return;
        }
        // 删除旧的
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        // 添加新的
        List<UserRole> list = user.getRoleIds().stream().map(roleId -> new UserRole(roleId, userId)).collect(Collectors.toList());
        userRoleService.saveBatch(list);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        // 删除用户表
        removeById(id);
        // 删除角色中间表
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
    }
}
