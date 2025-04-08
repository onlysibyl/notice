package pers.catigeart.notice.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.OrgRole;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.entity.UserOrgRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  Mapper 接口
 */
@Repository
public interface UserOrgRoleMapper extends BaseMapper<UserOrgRole> {

    List<UserOrgRole> findByOrgRole(List<OrgRole> orgRoleList);

    List<UserOrgRole> findByUsernameAndOrgId(@Param("username") String username,
                                             @Param("orgId") Integer orgId);

    List<UserOrgRole> findByOrg(Org org);
}
