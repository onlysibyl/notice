package pers.catigeart.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.mapper.OrgRoleMapper;
import pers.catigeart.notice.model.AllGroupModel;
import pers.catigeart.notice.model.AllRoleModel;

import java.util.List;


public interface OrgRoleService extends IService<OrgRole> {
    List<OrgRole> findByOrg(Org org);

    List<OrgRole> findByUser(User user);

    OrgRole findByUsernameAndOrgRoleName(String username, String orgRoleName);

    Boolean setUserRoleToBasic(User user);

    Boolean addUserWithBasicRole(User user);

    Boolean deleteByUser(User user);

    Boolean insert(OrgRole orgRole);

    AllRoleModel genAllRoleModel(OrgRole orgRole);

    List<AllRoleModel> genAllRoleModel(List<OrgRole> orgRoleList);

    OrgRole findMemberRole(Integer orgId);

    OrgRole findSuperRoleByOrg(Org org);

    List<OrgRole> findByUserAndOrg(User user, Org org);

    // 设置角色权限
    Boolean setRolePermissions(Integer roleId, List<String> permissionCodes);

    // 检查角色是否有某个权限
    Boolean hasPermission(Integer roleId, String permissionCode);
}
