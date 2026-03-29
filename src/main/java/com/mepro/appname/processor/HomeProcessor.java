package com.mepro.appname.processor;

import com.mepro.appname.dao.MenuDao;
import com.mepro.appname.dto.MenuDto;
import com.mepro.appname.dto.MenuGroupDto;
import com.mepro.appname.dto.UserInfoDto;
import com.mepro.appname.provider.CustomUserDetails;
import com.mepro.appname.response.ProcessorResult;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class HomeProcessor extends BaseProcessor {
    private static final Logger logger = LoggerFactory.getLogger(HomeProcessor.class);

    private MenuDao menuDao;
    private UserInfoDto userInfo;
    private CustomUserDetails userDetails;
    private String currentUri;
    
    public HomeProcessor(HttpServletRequest request, RedirectAttributes redirectAttributes, 
            UserInfoDto userInfo, CustomUserDetails userDetails, String currentUri,
            MenuDao menuDao) {
        this.request = request;
        this.redirectAttributes = redirectAttributes;
        this.menuDao = menuDao;
        this.userInfo = userInfo;
        this.userDetails = userDetails;
        this.currentUri = currentUri;
    }
    
    public ProcessorResult getListMenu() throws Exception {
        ProcessorResult result = new ProcessorResult();
        Map<String, Object> map = getLoginSession(userDetails);
        map.putAll(restoreParameters(request));

        List<Object[]> rows = menuDao.getMenuRaw(userInfo.getNik());
        List<MenuGroupDto> menuGroups = buildMenu(rows, currentUri);
        map.put("menuGroups", menuGroups);
        map.put("userInfo", userInfo);
        ModelAndView response = null;
        response = new ModelAndView("home");
        response.addAllObjects(map);
        result.setModelAndView(response);
        return result;
    }
}
