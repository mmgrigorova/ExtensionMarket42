package com.antman.extensionmarket42.webcontrollers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {
    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("oops");
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object request1 = request.getServletPath();

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                mav.addObject("error404", "Oops, we could not find this page ");
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                mav.addObject("error500", "Oops, there was a problem with this request");
            }
        } else {
            mav.addObject("error", "Oops, something strange happened and we could not proceed with this request");
        }
        logger.error("Exception during execution of SpringSecurity application: ");
        return mav;
    }

    @Override
    public String getErrorPath() {
        return "opps";
    }
}
