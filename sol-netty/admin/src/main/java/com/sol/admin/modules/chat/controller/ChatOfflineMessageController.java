package com.sol.admin.modules.chat.controller;

import com.sol.admin.modules.chat.entity.ChatOfflineMessage;
import com.sol.admin.modules.chat.service.ChatOfflineMessageService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 离线消息表 前端控制器
 * </p>
 *
 * @author sol
 * @since 2024-06-16
 */
@RestController
@RequestMapping("/chatOfflineMessage")
@Tag(name = "/chatOfflineMessage", description = "离线消息")
public class ChatOfflineMessageController {


    @Autowired
    ChatOfflineMessageService service;

    /**
     * 根据用户 id 获取某个用户离线消息
     * @param userId
     * @return
     */
    @GetMapping("/getChatUserLink")
    @Operation(summary ="根据用户 id 获取某个用户离线消息")
    public ResponseEntity<List<ChatOfflineMessage>> getChatOfflineMessage(String userId){
        return new ResponseEntity<>(service.getChatOfflineMessage(userId), HttpStatus.OK);
    }
}
