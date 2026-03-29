package com.mepro.appname.controller;

import com.mepro.appname.dao.MenuDao;
import com.mepro.appname.processor.HomeProcessor;
import com.mepro.appname.response.ProcessorResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController extends BaseController {
    protected HomeController(HttpServletRequest request) {
        super(request);
    }
    
    @Autowired
    private MenuDao menuDao;
    
    @RequestMapping(value="/home", method= RequestMethod.GET)
    public Object index(HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception {
        HomeProcessor processor = new HomeProcessor(request, redirectAttributes, getUserInfo(), 
                getUserDetails(), getCurrentUri(), menuDao);
        ProcessorResult result = processor.getListMenu();
        return result.getModelAndView();
    }
}
