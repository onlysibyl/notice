package pers.catigeart.notice.model;

import lombok.Data;
import pers.catigeart.notice.entity.MsgType;
import pers.catigeart.notice.entity.Operation;


@Data
public class PersonalMsgModel {
    private Integer id;
    private String name;
    private MsgType msgType;
    private Boolean isOneSend;
    private AllGroupModel sendAllGroup;
    private AllRoleModel sendAllRole;
    private String sendUsername;
    private Boolean isOneReceive;
    private AllGroupModel receiveAllGroup;
    private AllRoleModel receiveAllRole;
    private String receiveUsername;
    private String content;
    private Operation operation;
    private Boolean isDone;
}
