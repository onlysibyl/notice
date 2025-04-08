package pers.catigeart.notice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.catigeart.notice.dto.KlassDTO;
import pers.catigeart.notice.dto.NoticeDTO;
import pers.catigeart.notice.dto.ReplyDTO;
import pers.catigeart.notice.dto.SupplyDTO;
import pers.catigeart.notice.entity.*;
import pers.catigeart.notice.service.*;
import pers.catigeart.notice.util.EmailUtil;
import pers.catigeart.notice.util.Result;
import pers.catigeart.notice.vo.NoticeVO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private static final Logger log = LoggerFactory.getLogger(NoticeController.class);

    @Autowired
    NoticeService noticeService;

    @Autowired
    OrgService orgService;

    @Autowired
    KlassService klassService;

    @Autowired
    SupplyService supplyService;

    @Autowired
    ReplyService replyService;

    @Autowired
    OrgRoleService orgRoleService;

    @Autowired
    KlassNoticeService klassNoticeService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    UserService userService;

    @Autowired
    PersMsgService persMsgService;

    @GetMapping
    public Result<List<NoticeDTO>> findByOrgOrKlass(@RequestParam int id) {
        log.info("开始根据组织或班级ID查询通知列表，ID: {}", id);
        List<Notice> noticeList;
        try {
            if (id < 10000) {
                Org org = orgService.getById(id);
                if (org == null) {
                    log.warn("未找到对应的组织，ID: {}", id);
                    return Result.fail("未找到对应的组织");
                }
                noticeList = noticeService.findByOrg(org);
            } else {
                Klass klass = klassService.getById(id);
                if (klass == null) {
                    log.warn("未找到对应的班级，ID: {}", id);
                    return Result.fail("未找到对应的班级");
                }
                noticeList = noticeService.findByKlass(klass);
            }
        } catch (Exception e) {
            log.error("查询通知列表时出现异常", e);
            return Result.fail("查询通知列表失败，请稍后重试");
        }

        List<NoticeDTO> noticeDTOList = new ArrayList<>();
        for (Notice notice : noticeList) {
            NoticeDTO noticeDTO = new NoticeDTO();
            OrgRole orgRole = null;
            if (notice.getOrgRoleId() != null) {
                orgRole = orgRoleService.getById(notice.getOrgRoleId());
            }

            // 信息流范围受限
            List<SupplyDTO> supplyList = supplyService.findByNoticeWithLimit(notice, id);
            List<Reply> replyList = replyService.findByNoticeWithLimit(notice, id);
            List<ReplyDTO> replyDTOList = new ArrayList<>();
            for (Reply reply : replyList) {
                ReplyDTO replyDTO = replyService.reply2ReplyDTO(reply);
                replyDTOList.add(replyDTO);
            }

            noticeDTO.setId(notice.getId());
            noticeDTO.setNoticeName(notice.getNoticeName());
            noticeDTO.setNoticeType(notice.getNoticeType());
            noticeDTO.setContent(notice.getContent());
            noticeDTO.setOrgRole(orgRole);
            noticeDTO.setKlassRoleName(notice.getKlassRoleName());
            noticeDTO.setBeginTime(notice.getBeginTime());
            noticeDTO.setEndTime(notice.getEndTime());
            noticeDTO.setIsNeedReply(notice.getIsNeedReply());
            noticeDTO.setSupplyList(supplyList);
            noticeDTO.setReplyList(replyDTOList);

            noticeDTOList.add(noticeDTO);
        }
        log.info("根据组织或班级ID查询通知列表成功，共查询到 {} 条通知", noticeDTOList.size());
        return Result.success(noticeDTOList);
    }

    @PostMapping(value = "/add")
    public Result<Boolean> addNotice(@RequestBody @Valid NoticeVO noticeVO) {
        log.info("开始处理添加通知请求，通知名称: {}", noticeVO.getNoticeName());
        String roleName;
        Notice notice = new Notice();
        try {
            notice.setNoticeName(noticeVO.getNoticeName());
            notice.setNoticeType(noticeVO.getNoticeType());
            notice.setContent(noticeVO.getContent());

            if (noticeVO.getGroupId() < 10000) {
                OrgRole orgRole = getOrgRole(noticeVO.getRoleId());
                if (orgRole == null) {
                    return Result.fail("未找到对应的组织角色");
                }
                notice.setOrgRoleId(noticeVO.getRoleId());
                roleName = orgRole.getRoleName();
                notice.setKlassRoleName(noticeVO.getKlassRole());

                if (noticeVO.getKlassRole() != null && !noticeVO.getKlassRole().isEmpty()) {
                    sendPersMsgToClassCommittee(noticeVO);
                }
            } else {
                UserKlassRole userKlassRole = getUserKlassRole(noticeVO.getRoleId());
                if (userKlassRole == null) {
                    return Result.fail("未找到对应的班级角色");
                }
                notice.setKlassRoleName(userKlassRole.getKlassRoleName());
                roleName = userKlassRole.getKlassRoleName();
            }

            notice.setBeginTime(LocalDateTime.now());
            notice.setEndTime(noticeVO.getEndTime());
            notice.setIsNeedReply(noticeVO.getIsNeedReply());

            noticeService.saveAndGetId(notice);
            int noticeId = notice.getId();
            log.info("通知保存成功，通知ID: {}", noticeId);

            saveKlassNotices(noticeId, noticeVO.getCheckedKlassList());

            if (noticeVO.getIsSendEmail()) {
                sendNoticeEmail(roleName, noticeVO);
                log.info("已发送通知邮件");
            }
        } catch (Exception e) {
            log.error("添加通知时出现异常", e);
            return Result.fail("添加通知失败，请稍后重试");
        }
        log.info("添加通知请求处理完成");
        return Result.success();
    }

    private OrgRole getOrgRole(Integer roleId) {
        if (roleId == null) {
            log.warn("组织发送通知时，角色ID为空");
            return null;
        }
        OrgRole orgRole = orgRoleService.getById(roleId);
        if (orgRole == null) {
            log.warn("未找到对应的组织角色，ID: {}", roleId);
        }
        return orgRole;
    }

    private UserKlassRole getUserKlassRole(Integer roleId) {
        if (roleId == null) {
            log.warn("班级发送通知时，角色ID为空");
            return null;
        }
        UserKlassRole userKlassRole = userKlassRoleService.getById(roleId);
        if (userKlassRole == null) {
            log.warn("未找到对应的班级角色，ID: {}", roleId);
        }
        return userKlassRole;
    }

    private void sendPersMsgToClassCommittee(NoticeVO noticeVO) {
        List<KlassDTO> klassDTOList = noticeVO.getCheckedKlassList();
        if (klassDTOList != null) {
            for (KlassDTO klassDTO : klassDTOList) {
                Klass klass = klassService.getById(klassDTO.getId());
                if (klass != null) {
                    List<User> userList = userService.findByKlass(klass);
                    for (User user : userList) {
                        List<UserKlassRole> userKlassRoleList = userKlassRoleService.findByUsernameAndKlassRoleName(user.getUsername(), noticeVO.getKlassRole());
                        for (UserKlassRole userKlassRole : userKlassRoleList) {
                            PersMsg persMsg = new PersMsg();
                            persMsg.setMsgName("班委对接提醒");
                            persMsg.setMsgTypeId(4);
                            persMsg.setContent("你有新的需要对接的通知事务：" +
                                    noticeVO.getContent() +
                                    "，请前往通知管理页面查看！");
                            persMsg.setOperationId(1);
                            persMsg.setIsDone(0);
                            persMsg.setIsOneSend(0);
                            persMsg.setSendGroupId(noticeVO.getGroupId());
                            persMsg.setSendRoleId(noticeVO.getRoleId());
                            persMsg.setIsOneReceive(0);
                            persMsg.setReceiveGroupId(klass.getId());
                            persMsg.setReceiveRoleId(userKlassRole.getId());
                            persMsgService.save(persMsg);
                        }
                    }
                } else {
                    log.warn("未找到对应的班级，ID: {}", klassDTO.getId());
                }
            }
        }
    }

    private void saveKlassNotices(int noticeId, List<KlassDTO> checkedKlassList) {
        if (checkedKlassList != null) {
            List<KlassNotice> klassNoticeList = new ArrayList<>();
            for (KlassDTO klassDTO : checkedKlassList) {
                KlassNotice klassNotice = new KlassNotice();
                klassNotice.setNoticeId(noticeId);
                klassNotice.setKlassId(klassDTO.getId());
                klassNoticeList.add(klassNotice);
            }
            klassNoticeService.saveBatch(klassNoticeList);
        }
    }

    private void sendNoticeEmail(String roleName, NoticeVO noticeVO) throws Exception {
        EmailUtil.genNoticeEmail(roleName, noticeVO);
        EmailUtil.sendEmail(EmailUtil.genNoticeEmail(roleName, noticeVO));
    }
}
