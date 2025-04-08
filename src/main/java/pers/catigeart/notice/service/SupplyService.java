package pers.catigeart.notice.service;

import pers.catigeart.notice.dto.SupplyDTO;
import pers.catigeart.notice.entity.Notice;
import pers.catigeart.notice.entity.Supply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SupplyService extends IService<Supply> {
    List<Supply> findByNotice(Notice notice);

    List<SupplyDTO> findByNoticeWithLimit(Notice notice, int belongingId);
}
