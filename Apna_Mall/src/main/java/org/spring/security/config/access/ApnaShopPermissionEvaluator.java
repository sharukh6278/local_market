package org.spring.security.config.access;

import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ApnaShopPermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {
    final ApplicationContext applicationContext;

    public ApnaShopPermissionEvaluator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object data) {
        if(authentication==null || targetDomainObject==null || data==null){
            return false;
        }
        AccessManager accessManager=  applicationContext.getBean(targetDomainObject.toString(),AccessManager.class);
        return accessManager.checkAccess(authentication, data);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
