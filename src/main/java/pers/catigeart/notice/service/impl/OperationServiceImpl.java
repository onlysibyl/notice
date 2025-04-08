package pers.catigeart.notice.service.impl;

import pers.catigeart.notice.entity.Operation;
import pers.catigeart.notice.mapper.OperationMapper;
import pers.catigeart.notice.service.OperationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {

}
