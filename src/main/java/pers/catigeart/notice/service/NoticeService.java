package pers.catigeart.notice.service;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.catigeart.notice.entity.Org;

import java.util.List;


public interface NoticeService extends IService<Notice> {
    List<Notice> findByOrg(Org org);

    List<Notice> findByKlass(Klass klass);

    Integer saveAndGetId(Notice notice);
}
