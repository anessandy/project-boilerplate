package com.mepro.appname.controller;

import com.mepro.appname.dto.UserInfoDto;
import com.mepro.appname.provider.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
    
    protected String keyword;
    protected int page;
    protected int size;
    protected HttpServletRequest request;

    protected BaseController(HttpServletRequest request) {
        this.request = request;
    }
    
    protected String getCurrentUri() {
        return (String) request.getAttribute(
            org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE
        );
    }
    
    protected CustomUserDetails getUserDetails() {
        return (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
    
    protected UserInfoDto getUserInfo() {
        return getUserDetails().getUserInfo();
    }
    
    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("currentUri", getCurrentUri());
    }
}
