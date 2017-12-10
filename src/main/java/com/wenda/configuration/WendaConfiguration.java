package com.wenda.configuration;

import com.wenda.interceptor.LoginRequiredInterceptor;
import com.wenda.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by evilhex on 2017/12/10.
 */
@Component
public class WendaConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    private PassportInterceptor passportInterceptor;

    @Autowired
    private  LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }

}
