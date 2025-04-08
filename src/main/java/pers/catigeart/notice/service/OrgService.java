package pers.catigeart.notice.service;

import pers.catigeart.notice.entity.Org;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.model.AllGroupModel;

import java.util.List;


public interface OrgService extends IService<Org> {
    List<Org> findByUser(User user);

    AllGroupModel genAllGroupModel(Org org);

    List<AllGroupModel> genAllGroupModel(List<Org> orgList);
}
