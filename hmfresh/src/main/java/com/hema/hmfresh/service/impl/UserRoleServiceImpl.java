package com.hema.hmfresh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hema.hmfresh.mapper.UserRoleMapper;
import com.hema.hmfresh.pojo.UserRole;
import com.hema.hmfresh.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
