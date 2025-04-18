package pers.catigeart.notice.mapper;

import org.springframework.stereotype.Repository;
import pers.catigeart.notice.entity.SuperRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 *  Mapper 接口
 */
@Repository
public interface SuperRoleMapper extends BaseMapper<SuperRole> {
    List<SuperRole> findAll();
}
