package com.sol.admin.modules.system.controller;

import com.sol.admin.modules.system.service.IChatGroupUserService;
import com.sol.admin.modules.system.service.IChatUserLinkService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    IChatGroupUserService service;

    @Autowired
    IChatUserLinkService service;


    /**
     * 查看某个用户的好友
     * @param id
     * @return
     */
    @GetMapping("/selectChatUserLink")
    public ResponseEntity<Object> getChatUserLink(String id){
        return new ResponseEntity<>(service.getChatUserLink(id), HttpStatus.OK);
    }

}
