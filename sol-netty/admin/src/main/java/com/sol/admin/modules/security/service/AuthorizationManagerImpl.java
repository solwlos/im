package com.sol.admin.modules.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;

import java.util.function.Supplier;

/**
 * date: 2024/2/23
 *
 * @author sol
 */
@Slf4j
public class AuthorizationManagerImpl implements AuthorizationManager {


    @Override
    public AuthorizationDecision check(Supplier authentication, Object object) {

        log.info("AuthorizationManagerImpl.check =======================");
        return null;
    }
}
