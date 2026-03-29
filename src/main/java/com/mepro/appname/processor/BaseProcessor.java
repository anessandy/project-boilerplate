package com.mepro.appname.processor;

import com.mepro.appname.dao.MenuDao;
import com.mepro.appname.dto.MenuDto;
import com.mepro.appname.dto.MenuGroupDto;
import com.mepro.appname.dto.UserInfoDto;
import com.mepro.appname.provider.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BaseProcessor {
    protected HttpServletRequest request;
    protected RedirectAttributes redirectAttributes;
    
    public BaseProcessor() {		
    }
    
    public BaseProcessor(HttpServletRequest request) {
        this.request = request;
    }

    public BaseProcessor(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        this.request = request;
        this.redirectAttributes = redirectAttributes;
    }
    
    public Map<String, Object> getLoginSession(CustomUserDetails details) {
        Map<String, Object> map = new HashMap<>();
        String requestUrl = request.getRequestURI();
        String contextPath = request.getContextPath();
        String requestPath = requestUrl.substring(contextPath.length());
        UserInfoDto userLogin = details.getUserInfo();
        map.put("userLogin", userLogin);
        return map;
    }
    
    protected Map<String, Object> restoreParameters(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        Enumeration<String> mapNames = request.getParameterNames();
        if (mapNames != null) {
            while (mapNames.hasMoreElements()) {
                String key = mapNames.nextElement();
                Object value = request.getParameter(key);
                if (StringUtils.contains(key, "[")) {
                    key = StringUtils.replace(key, "[", "");
                    key = StringUtils.replace(key, "]", "");
                }
                map.put(key, value);
            }
        }

        return map;
    }
    
    public List<MenuGroupDto> buildMenu(List<Object[]> rows, String currentUri) {
        Map<String, MenuGroupDto> map = new LinkedHashMap<>();

        for (Object[] row : rows) {
            String groupName = (String) row[0];
            String menuName = (String) row[1];
            String url = (String) row[2];
            String namespace = (String) row[3];

            MenuGroupDto group = map.get(groupName);
            if (group == null) {
                group = new MenuGroupDto();
                group.setName(groupName);
                map.put(groupName, group);
            }

            if (menuName != null) {
                MenuDto menu = new MenuDto();
                menu.setName(menuName);
                menu.setUrl(url);
                menu.setNamespace(namespace);
                
                if (currentUri.startsWith(namespace)) {
                    menu.setActive(true);
                    group.setOpen(true);
                }
                group.getMenus().add(menu);
            }
        }

        return new ArrayList<>(map.values());
    }
    
    public Map<String, Object> prepareBaseForm(MenuDao menuDao, UserInfoDto userInfo, 
            Long nik, String currentUri) throws Exception {
        Map <String, Object> map = new HashMap<>();
        List<Object[]> rows = menuDao.getMenuRaw(nik);
        List<MenuGroupDto> menuGroups = buildMenu(rows, currentUri);
        map.put("menuGroups", menuGroups);
        map.put("userInfo", userInfo);
        return map;
    }
}
