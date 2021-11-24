package com.hema.hmfresh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hema.hmfresh.pojo.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    void saveMenu(Menu menu);

    void deleteMenu(Long id);

    List<Menu> queryMenuByRoleIds(List<Long> roleIds);
}
