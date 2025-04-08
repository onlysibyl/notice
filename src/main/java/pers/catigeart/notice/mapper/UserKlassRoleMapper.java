package pers.catigeart.notice.mapper;

import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.UserKlassRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  Mapper 接口
 */
@Repository
public interface UserKlassRoleMapper extends BaseMapper<UserKlassRole> {
}
