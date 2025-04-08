package pers.catigeart.notice.service;

import pers.catigeart.notice.dto.ReplyDTO;
import pers.catigeart.notice.entity.Notice;
import pers.catigeart.notice.entity.Reply;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ReplyService extends IService<Reply> {
    List<Reply> findByNotice(Notice notice);

    ReplyDTO reply2ReplyDTO(Reply reply);

    List<Reply> findByNoticeWithLimit(Notice notice, int groupId);
}
