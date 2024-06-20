package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.entity.ChatGroupUser;

import com.sol.admin.modules.system.service.IChatGroupUserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 群聊用户表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/chatGroupUser")
public class ChatGroupUserController {


    @Autowired
    IChatGroupUserService service;

    /**
     * 根据群聊 id查询群聊成员
     * @param groupId
     * @return
     */
    @GetMapping("/getGroupUser")
    @Operation(summary ="根据群聊 id查询群聊成员")
    public ResponseEntity<List<ChatGroupUser>> getGroupUser(String groupId){
        return new ResponseEntity<>(service.getGroupUser(groupId), HttpStatus.OK);
    }


}
