package pers.catigeart.notice.mapper;

import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.Klass;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 *  Mapper 接口
 */
@Repository
public interface KlassMapper extends BaseMapper<Klass> {
    Klass findByUserKlassRoleId(int id);
}
