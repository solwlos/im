package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.entity.ChatUserLink;
import com.sol.admin.modules.system.service.ChatUserLinkService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 关系表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-02-22
 */
@RestController
@RequestMapping("/chatUserLink")
public class ChatUserLinkController {

    @Autowired
    ChatUserLinkService service;


    /**
     * 根据用户 id 获取某个用户的好友
     * @param userId
     * @return
     */
    @GetMapping("/getChatUserLink")
    @Operation(summary ="根据用户 id 获取某个用户的好友")
    public ResponseEntity<List<ChatUserLink>> getChatUserLink(String userId){
        return new ResponseEntity<>(service.getChatUserLink(userId), HttpStatus.OK);
    }



    @GetMapping("/addChatUserLink")
    @Operation(summary ="根据用户 id 添加某个用户为好友")
    public ResponseEntity<Boolean> addChatUserLink(ChatUserLink link){
        return new ResponseEntity<>(service.addChatUserLink(link), HttpStatus.OK);
    }

    @PutMapping("/updateChatUserLink")
    @Operation(summary ="修改用户关系")
    public ResponseEntity<Boolean> updateChatUserLink(ChatUserLink link){
        return new ResponseEntity<>(service.updateChatUserLink(link), HttpStatus.OK);
    }


    @DeleteMapping("/deleteChatUserLink")
    @Operation(summary ="删除用户关系")
    public ResponseEntity<Boolean> deleteChatUserLink(String id){
        return new ResponseEntity<>(service.deleteChatUserLink(id), HttpStatus.OK);
    }

}
