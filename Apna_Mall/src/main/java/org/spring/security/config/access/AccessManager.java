package org.spring.security.config.access;

import org.springframework.security.core.Authentication;

public interface AccessManager {
    boolean checkAccess(Authentication authentication, Object data);
}
