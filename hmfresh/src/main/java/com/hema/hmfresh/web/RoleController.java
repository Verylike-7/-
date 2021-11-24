package com.hema.hmfresh.web;

import com.hema.hmfresh.pojo.Role;
import com.hema.hmfresh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    protected RoleService roleService;

    @GetMapping("/all")
    public List<Role> queryAllRole() {
        return roleService.list();
    }

    @PostMapping
    public void saveRole(@RequestBody Role Role){
        roleService.save(Role);
    }

    @PutMapping
    public void updateRole(@RequestBody Role Role){
        roleService.updateById(Role);
    }

    @PutMapping("menu")
    public void updateRoleMenu(@RequestBody Role Role){
        roleService.updateRoleMenu(Role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
    }
}
