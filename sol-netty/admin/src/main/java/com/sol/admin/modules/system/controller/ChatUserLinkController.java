package com.sol.admin.modules.system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("/selectChatUserLink")
    public ResponseEntity<Object> getChatUserLink(){


        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
