package com.hema.hmfresh.web;

import com.hema.hmfresh.pojo.Menu;
import com.hema.hmfresh.pojo.RoleMenu;
import com.hema.hmfresh.service.MenuService;
import com.hema.hmfresh.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    protected MenuService menuService;

    @Autowired
    protected RoleMenuService roleMenuService;

    @GetMapping("/of/parent/{pid}")
    public List<Menu> queryMenuByParentId(@PathVariable("pid") Long pid) {
        return menuService.query().eq("parent_id", pid).list();
    }

    @GetMapping("/of/role/{roleId}")
    public List<Menu> queryMenuByRoleId(@PathVariable("roleId") Long roleId) {
        // 查询menuId
        List<RoleMenu> roleMenus = roleMenuService.query().eq("role_id", roleId).list();
        if (CollectionUtils.isEmpty(roleMenus)) {
            return Collections.emptyList();
        }
        // 查询menuList
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return menuService.listByIds(menuIds);
    }

    @GetMapping("/all")
    public List<Menu> queryAllMenus(){
        return menuService.list();
    }

    @PostMapping
    public void saveMenu(@RequestBody Menu menu){
        menuService.saveMenu(menu);
    }

    @PutMapping
    public void updateMenu(@RequestBody Menu menu){
        menuService.updateById(menu);
    }

    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable("id") Long id) {
        menuService.deleteMenu(id);
    }
}
