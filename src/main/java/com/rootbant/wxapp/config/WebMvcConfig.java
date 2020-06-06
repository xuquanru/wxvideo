package com.rootbant.wxapp.config;

import com.rootbant.wxapp.interceptor.MiniInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-05 14:33
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${myfile.path}")
    private String filepath;
    @Value("${myfile.bgmpath}")
    private String bgmpath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:"+filepath+"/")
                .addResourceLocations("file:"+bgmpath+"/")
                .addResourceLocations("classpath:/META-INF/resources/");
    }


    @Bean
    public MiniInterceptor miniInterceptor() {
        return new MiniInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(miniInterceptor()).addPathPatterns("/user/**")
//                .addPathPatterns("/video/upload", "/video/uploadCover",
//                        "/video/userLike", "/video/userUnLike",
//                        "/video/saveComment")
//                .addPathPatterns("/bgm/**")
//                .excludePathPatterns("/user/queryPublisher");

     //   registry.addInterceptor(miniInterceptor())
     //           .addPathPatterns("/user/query");
      //  WebMvcConfigurer.super.addInterceptors(registry);
    }
}
