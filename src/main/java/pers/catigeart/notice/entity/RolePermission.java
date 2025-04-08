package pers.catigeart.notice.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 角色权限关联表
 */
@Data
@TableName("role_permission")
public class RolePermission implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @TableId
    private Integer id;

    /**
     * 角色 ID
     */
    private Integer roleId;

    /**
     * 权限 ID
     */
    private Integer permissionId;

    /**
     * 权限代码
     */
    private String permissionCode;

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionCode() {
        return permissionCode;
    }
}
