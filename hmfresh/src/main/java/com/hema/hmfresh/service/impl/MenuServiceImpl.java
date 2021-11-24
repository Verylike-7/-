package com.hema.hmfresh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hema.hmfresh.mapper.MenuMapper;
import com.hema.hmfresh.pojo.Menu;
import com.hema.hmfresh.pojo.RoleMenu;
import com.hema.hmfresh.service.MenuService;
import com.hema.hmfresh.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService  {

    @Autowired
    private RoleMenuService roleMenuService;

    @Transactional
    @Override
    public void saveMenu(Menu menu) {
        // 1.新增菜单
        save(menu);
        if(menu.getParentId() == 0){
            // 自己就是一级菜单，结束业务
            return;
        }

        // 2.给超管添加新菜单权限
        RoleMenu roleMenu = new RoleMenu(10001L, menu.getId());
        roleMenuService.save(roleMenu);

        // 3.自己不是一级菜单，可能需要更新父菜单状态
        // 3.1.查询父菜单
        Menu parent = getById(menu.getParentId());
        // 3.2.判断父状态
        if(parent.getHasChildren()){
            // 已经有 hasChildren属性了，结束
            return;
        }
        // 3.3.修改父的 hasChildren状态
        parent.setHasChildren(true);
        updateById(parent);


    }

    @Override
    @Transactional
    public void deleteMenu(Long id) {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        // 1.查询当前菜单
        Menu menu = getById(id);
        // 2.删除当前菜单
        removeById(id);
        // 3.判断是否有子菜单
        if(menu.getHasChildren()){
            // 查询子菜单
            List<Menu> subMenus = query().eq("parent_id", id).list();
            if(!CollectionUtils.isEmpty(subMenus)){
                // 获取子菜单id
                List<Long> subMenuIds = subMenus.stream().map(Menu::getId).collect(Collectors.toList());
                ids.addAll(subMenuIds);
                // 需要删除子菜单
                removeByIds(subMenuIds);
            }
        }
        // 4.判断是否有父菜单
        if (menu.getParentId() == 0) {
            // 4.1.没有父菜单
            return;
        }
        // 4.2.有父菜单，需要判断父菜单是否还有其他子菜单
        Integer count = query().eq("parent_id", menu.getParentId()).count();
        if (count <= 0) {
            // 没有其它子菜单，需要修改父菜单的hasChildren状态
            update().set("has_children", false).eq("id", menu.getParentId()).update();
        }

        // 5.删除菜单关联的中间表
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("menu_id", ids));
    }

    @Override
    public List<Menu> queryMenuByRoleIds(List<Long> roleIds) {
        // 查询中间表
        List<RoleMenu> roleMenus = roleMenuService.query().in("role_id", roleIds).list();
        if (CollectionUtils.isEmpty(roleMenus)) {
            return Collections.emptyList();
        }
        // 获取menuId
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());
        // 查询
        List<Menu> menus = listByIds(menuIds);
        // 判断父菜单
        List<Long> parentIds = new ArrayList<>();
        for (Menu menu : menus) {
            if (menu.getParentId() != 0 && !menuIds.contains(menu.getParentId())) {
                parentIds.add(menu.getParentId());
            }
        }
        // 查询父
        if (!parentIds.isEmpty()) {
            List<Menu> parents = listByIds(parentIds);
            menus.addAll(parents);
        }
        return menus;
    }
}
