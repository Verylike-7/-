package com.hema.hmfresh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hema.hmfresh.pojo.Role;

public interface RoleService extends IService<Role> {
    void deleteRole(Long id);

    void updateRoleMenu(Role role);
}
