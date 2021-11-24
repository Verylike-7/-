package com.hema.hmfresh.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hema.hmfresh.pojo.Menu;
import com.hema.hmfresh.pojo.PageResult;
import com.hema.hmfresh.pojo.User;
import com.hema.hmfresh.pojo.UserRole;
import com.hema.hmfresh.service.MenuService;
import com.hema.hmfresh.service.UserRoleService;
import com.hema.hmfresh.service.UserService;
import com.hema.hmfresh.util.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/{id}")
    public User findById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        user.setPassword(null);
        return user;
    }
    @GetMapping("page")
    public PageResult<User> queryUserPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ){
        Page<User> result = userService.page(new Page<>(page, size));
        return new PageResult<>(result.getTotal(), result.getRecords());
    }

    @PutMapping("/state/{userId}/{enable}")
    public void updateState(@PathVariable("enable") Boolean enable, @PathVariable("userId") String userId) {
        userService.update().set("enable", enable).eq("id", userId).update();
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/role")
    public void updateUserRole(@RequestBody User user){
        userService.updateUserRole(user);
    }

    @GetMapping("/role/{userId}")
    public List<Long> queryUserRoles(@PathVariable("userId") Long userId) {
        // 查询中间表
        List<UserRole> userRoles = userRoleService.query().eq("user_id", userId).list();
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }
        // 返回ids
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }

    @GetMapping("/menu/{userId}")
    public List<Menu> queryUserMenus(@PathVariable("userId") Long userId) {
        // 查询中间表
        List<UserRole> userRoles = userRoleService.query().eq("user_id", userId).list();
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }
        // 获取角色ID
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 获取菜单
        return menuService.queryMenuByRoleIds(roleIds);
    }

    @PostMapping("register")
    public void register(@RequestBody User user){
        user.setEnable(false);
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        userService.save(user);
    }

    @PostMapping("login")
    public Long login(@RequestBody User userForm, HttpServletRequest request){
        String username = userForm.getUsername();
        String password = userForm.getPassword();
        if (username == null || password == null) {
            return -1L;
        }
        // 1.根据用户名查询
        User user = userService.query().eq("username", username).one();
        if (user == null) {
            // 用户名错误
            return -2L;
        }
        // 2.判断是否启用
        if(!user.getEnable()){
            return -3L;
        }
        // 3.判断密码
        if(!PasswordEncoder.matches(user.getPassword(), password)){
            return -2L;
        }

        // 4.更新登录时间
        User u = new User();
        u.setId(user.getId());
        u.setLastLoginTime(new Date());
        userService.updateById(u);
        // 5.返回
        return user.getId();
    }

    /*@GetMapping("/me")
    public User me(HttpServletRequest request){
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        }
        User u = (User) user;
        u.setPassword(null);
        return u;
    }*/
}
