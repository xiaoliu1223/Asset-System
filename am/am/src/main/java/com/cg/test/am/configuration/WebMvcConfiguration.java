package com.cg.test.am.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


    @Autowired
    private ApiInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor)
                .addPathPatterns("/**/**")
                .excludePathPatterns("/login/**", "/login", "/sysAsset/downloadExcel","/sysApplicationRecord/downloadAddTemp")
                .excludePathPatterns("/doc.html","/doc.html/**", "/api-docs-ext/**", "/swagger-resources", "/swagger-ui.html/**", "/swagger-resources/configuration/ui/**", "/swagger-resources/configuration/security/**", "/service-worker.js", "/webjars/**", "/favicon.ico");

    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
}
 