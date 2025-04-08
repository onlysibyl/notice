package pers.catigeart.notice.service;

import pers.catigeart.notice.entity.*;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.catigeart.notice.model.PersonalMsgModel;

import java.util.List;


public interface PersMsgService extends IService<PersMsg> {

    PersonalMsgModel genModel(PersMsg persMsg);

    List<PersonalMsgModel> genModel(List<PersMsg> persMsgList);

    PersMsg initReplyMsg(PersMsg persMsg);

    int insert(PersMsg persMsg);

    List<PersMsg> findByAllReceiver(List<UserKlassRole> userKlassRoleList,
                                    List<OrgRole> orgRoleList,
                                    User user);
}
