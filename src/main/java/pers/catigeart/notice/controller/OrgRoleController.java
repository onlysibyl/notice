package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import pers.catigeart.notice.dto.RoleDTO;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserOrgRole;
import pers.catigeart.notice.service.OrgRoleService;
import pers.catigeart.notice.service.UserOrgRoleService;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *  前端控制器
 */
@RestController
@RequestMapping("/orgRole")
public class OrgRoleController {

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    OrgRoleService orgRoleService;


    @PostMapping(value = "/edit")
    public Result<Boolean> updateOrgRole(@RequestBody RoleDTO role) {
        OrgRole orgRole = orgRoleService.getById(role.getId());
        orgRole.setRoleName(role.getName());
        orgRoleService.updateById(orgRole);
        return Result.success();
    }

    //设置用户角色为basic
    @PostMapping("/setUserRoleToBasic")
    public Result<Boolean> setUserRoleToBasic(@RequestBody User user) {
        boolean result = orgRoleService.setUserRoleToBasic(user);
        return Result.success(result);
    }

    //新增用户并设置为basic角色
    @PostMapping("/addUserWithBasicRole")
    public Result<Boolean> addUserWithBasicRole(@RequestBody User user) {
        boolean result = orgRoleService.addUserWithBasicRole(user);
        return Result.success(result);
    }


    @GetMapping(value = "/add")
    public Result<Boolean> addRole(@RequestParam String addRoleName, @RequestParam String groupId) {
        OrgRole orgRole = new OrgRole();
        orgRole.setRoleName(addRoleName);
        orgRole.setOrgId(Integer.parseInt(groupId));
        orgRoleService.insert(orgRole);
        return Result.success();
    }

    @GetMapping(value = "/delete")
    public Result<Boolean> deleteRole(@RequestParam String id) {
        OrgRole orgRole = new OrgRole();
        orgRole.setId(Integer.parseInt(id));
        List<UserOrgRole> userOrgRoleList = userOrgRoleService.findByOrgRole(orgRole);
        if (userOrgRoleList != null) {
            return Result.success(false);
        }

        orgRoleService.removeById(id);
        return Result.success(true);
    }



}
