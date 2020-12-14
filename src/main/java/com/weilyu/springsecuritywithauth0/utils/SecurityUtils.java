package com.weilyu.springsecuritywithauth0.utils;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;
import java.util.Optional;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            return "UserDetails";
//            return springSecurityUser.getUsername();
        } else if (authentication instanceof JwtAuthenticationToken) {
            // return "JwtAuthenticationToken";
            return (String) ((JwtAuthenticationToken) authentication).getToken().getClaims().toString();
        } else if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            return "DefaultOidcUser";
//            Map<String, Object> attributes = ((DefaultOidcUser) authentication.getPrincipal()).getAttributes();
//            if (attributes.containsKey("email")) {
//                return (String) attributes.get("email");
//            }
        } else if (authentication.getPrincipal() instanceof String) {
            return "String";
           // return (String) authentication.getPrincipal();
        }
        return null;
    }
}
