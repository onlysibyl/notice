package pers.catigeart.notice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.catigeart.notice.dto.GroupDTO;
import pers.catigeart.notice.entity.Klass;
import pers.catigeart.notice.entity.Org;
import pers.catigeart.notice.entity.User;
import pers.catigeart.notice.service.*;
import pers.catigeart.notice.util.JwtUtil;
import pers.catigeart.notice.util.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    UserService userService;

    @Autowired
    UserKlassRoleService userKlassRoleService;

    @Autowired
    UserOrgRoleService userOrgRoleService;

    @Autowired
    KlassService klassService;

    @Autowired
    OrgService orgService;

    // @Autowired
    // private GroupService groupService;

    @GetMapping("/list") // 避免方法名冲突，修改请求路径
    public Result<List<GroupDTO>> getGroupList(HttpServletRequest request) {
        List<GroupDTO> groupDTOList = new ArrayList<>();

        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        User user = userService.getById(username);

        GroupDTO klassGroup = new GroupDTO();

        Klass klass = klassService.getById(user.getKlassId());
        if (klass != null) {
            klassGroup.setId(klass.getId());
            klassGroup.setName(klass.getKlassName());
            klassGroup.setType("班级");
            groupDTOList.add(klassGroup);
        }

        List<Org> orgList = orgService.findByUser(user);
        for (Org org : orgList) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setId(org.getId());
            groupDTO.setType("通知组");
            groupDTO.setName(org.getOrgName());
            groupDTOList.add(groupDTO);
        }

        return Result.success(groupDTOList);
    }

    @GetMapping("/pagelist") // 避免方法名冲突，修改请求路径
    public Result<IPage<GroupDTO>> getGroupList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder,
            HttpServletRequest request) {
        Page<GroupDTO> pageParam = new Page<>(page, pageSize);
        // 根据 sortField 和 sortOrder 进行排序处理
        if ("desc".equalsIgnoreCase(sortOrder)) {
            pageParam.addOrder(OrderItem.desc(sortField));
        } else {
            pageParam.addOrder(OrderItem.asc(sortField));
        }

        // 获取当前用户信息
        String token = request.getHeader("token");
        String username = JwtUtil.getUserId(token);
        User user = userService.getById(username);

        // 构建 GroupDTO 列表
        List<GroupDTO> groupDTOList = new ArrayList<>();

        // 添加班级信息
        GroupDTO klassGroup = new GroupDTO();
        Klass klass = klassService.getById(user.getKlassId());
        if (klass != null) {
            klassGroup.setId(klass.getId());
            klassGroup.setName(klass.getKlassName());
            klassGroup.setType("班级");
            groupDTOList.add(klassGroup);
        }

        // 添加通知组信息
        List<Org> orgList = orgService.findByUser(user);
        for (Org org : orgList) {
            GroupDTO groupDTO = new GroupDTO();
            groupDTO.setId(org.getId());
            groupDTO.setType("通知组");
            groupDTO.setName(org.getOrgName());
            groupDTOList.add(groupDTO);
        }

        // 手动分页
        int startIndex = (int) ((page - 1) * pageSize);
        int endIndex = Math.min(startIndex + pageSize, groupDTOList.size());
        List<GroupDTO> pageList = groupDTOList.subList(startIndex, endIndex);

        // 封装分页结果
        IPage<GroupDTO> groupPage = new Page<>(page, pageSize, groupDTOList.size());
        groupPage.setRecords(pageList);

        return Result.success(groupPage);
    }
}


