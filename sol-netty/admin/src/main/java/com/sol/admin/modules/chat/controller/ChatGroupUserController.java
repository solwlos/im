package com.sol.admin.modules.chat.controller;

import com.sol.admin.modules.chat.entity.ChatGroupUser;

import com.sol.admin.modules.chat.service.ChatGroupUserService;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@Tag(name = "/chatGroupUser", description = "群聊成员管理")
public class ChatGroupUserController {


    @Autowired
    ChatGroupUserService service;

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

    /**
     * 将某个成员移除群聊
     * @param groupId
     * @param userId
     * @return
     */
    @DeleteMapping("/delGroupUser")
    @Operation(summary ="将某个成员移除群聊")
    public ResponseEntity<Boolean> delGroupUser(String groupId,String userId){
        return new ResponseEntity<>(service.delGroupUser(groupId,userId), HttpStatus.OK);
    }

    /**
     * 将某个成员加入群聊
     * @param groupId
     * @param userId
     * @return
     */
    @PostMapping("/addGroupUser")
    @Operation(summary ="将某个成员加入群聊")
    public ResponseEntity<Boolean> addGroupUser(String groupId,String userId){
        return new ResponseEntity<>(service.delGroupUser(groupId,userId), HttpStatus.OK);
    }

}
