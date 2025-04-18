package pers.catigeart.notice.service;

import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserOrgRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface UserOrgRoleService extends IService<UserOrgRole> {
    Boolean insert(UserOrgRole userOrgRole);

    List<UserOrgRole> findByUserAndOrg(User user, Org org);

    List<UserOrgRole> findByOrg(Org org);

    List<UserOrgRole> findByOrgRole(OrgRole orgRole);
}
