package pers.catigeart.notice.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission>{
    /**
     * 根据角色 ID 删除角色权限
     * @param roleId 角色 ID
     * @return 删除的记录数
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据角色 ID 查询角色权限列表
     * @param roleId 角色 ID
     * @return 角色权限列表
     */
    List<RolePermission> findByRoleId(@Param("roleId") Integer roleId);

    /**
     * 批量插入角色权限
     * @param rolePermissions 角色权限列表
     * @return 插入的记录数
     */
    int batchInsert(@Param("rolePermissions") List<RolePermission> rolePermissions);
}

