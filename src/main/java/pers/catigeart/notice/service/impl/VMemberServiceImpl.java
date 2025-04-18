package pers.catigeart.notice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.VMember;
import pers.catigeart.notice.mapper.VMemberMapper;
import pers.catigeart.notice.service.VMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class VMemberServiceImpl extends ServiceImpl<VMemberMapper, VMember> implements VMemberService {
    @Autowired
    VMemberMapper vMemberMapper;
}
