package pers.catigeart.notice.service.impl;

import pers.catigeart.notice.entity.MsgType;
import pers.catigeart.notice.mapper.MsgTypeMapper;
import pers.catigeart.notice.service.MsgTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class MsgTypeServiceImpl extends ServiceImpl<MsgTypeMapper, MsgType> implements MsgTypeService {

}
