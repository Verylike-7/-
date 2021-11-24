package com.hema.hmfresh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hema.hmfresh.pojo.User;

public interface UserService extends IService<User> {
    void updateUserRole(User user);

    void deleteUserById(Long id);
}
