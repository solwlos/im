package com.sol.admin.modules.chat.controller;

import com.sol.admin.modules.chat.entity.ChatGroup;
import com.sol.admin.modules.chat.service.ChatGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Tag(name = "/chatGroup", description = "群聊管理")
public class ChatGroupController {

    @Resource
    ChatGroupService service;

    /**
     * 根据 用户id 查询某个用户的所有群聊
     */
    @GetMapping("/getGroupByUserID")
    @Operation(summary ="根据 用户id 查询某个用户的所有群聊")
    public ResponseEntity<List<ChatGroup>> getGroupByUserID(String userId){
        return new ResponseEntity<List<ChatGroup>>(service.getGroupByUserID(userId), HttpStatus.OK);
    }

    @PostMapping("/addChatGroup")
    @Operation(summary ="创建群聊")
    public ResponseEntity<Boolean> addChatGroup(@RequestBody ChatGroup chatGroup){
        return new ResponseEntity<>(service.addChatGroup(chatGroup), HttpStatus.OK);
    }

    @PutMapping("/updateChatGroup")
    @Operation(summary ="修改群聊")
    public ResponseEntity<Boolean> updateChatGroup(@RequestBody ChatGroup chatGroup){
        return new ResponseEntity<>(service.updateChatGroup(chatGroup), HttpStatus.OK);
    }

    @DeleteMapping("/deleteChatGroup")
    @Operation(summary ="删除群聊")
    public ResponseEntity<Boolean> deleteChatGroup(String id){
        return new ResponseEntity<>(service.deleteChatGroup(id), HttpStatus.OK);
    }

}
