package pers.catigeart.notice.service;

import pers.catigeart.notice.entity.SuperRole;
import com.baomidou.mybatisplus.extension.service.IService;


public interface SuperRoleService extends IService<SuperRole> {
    boolean isSuperRole(int roleId);
}
