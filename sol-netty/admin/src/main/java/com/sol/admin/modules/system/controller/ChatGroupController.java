package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.entity.ChatGroup;
import com.sol.admin.modules.system.service.IChatGroupService;
import com.sol.admin.modules.system.service.IChatGroupUserService;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 群聊表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/chatGroup")
public class ChatGroupController {

    @Resource
    IChatGroupService service;

    /**
     * 根据 用户id 查询某个用户的所有群聊
     * @param userId
     * @return
     */
    @GetMapping("/selectGroupByUserID")
    public ResponseEntity<List<ChatGroup>> selectGroupByUserID(String userId){
        return new ResponseEntity<List<ChatGroup>>(service.selectGroupByUserID(userId), HttpStatus.OK);
    }

}
