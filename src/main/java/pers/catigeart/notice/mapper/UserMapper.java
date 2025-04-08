package pers.catigeart.notice.mapper;

import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  Mapper 接口
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> findByOrg(Org org);

    List<User> findByKlass(Klass klass);

    List<User> findWithRoleByKlass(Klass klass);
}
