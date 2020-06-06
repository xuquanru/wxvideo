package com.rootbant.wxapp.config;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 🍁 Program: wxapp
 * <p>
 * 🍁 Description
 * <p>
 * 🍁 Author: Stephen
 * <p>
 * 🍁 Create: 2020-03-03 20:05
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){


        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //Predicate<RequestHandler> requestHandlerPredicate = RequestHandlerSelectors.basePackage("com.rootbant.freebird.controller");
        Predicate<RequestHandler> requestHandlerPredicate= requestHandler -> {
            return  requestHandler.isAnnotatedWith(ApiOperation.class);
        };
//        //设置请求头token
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());

        docket.apiInfo(apiInfo());
        ApiSelectorBuilder apis = docket.select().apis(requestHandlerPredicate);
        ApiSelectorBuilder paths = apis.paths(PathSelectors.any());
        Docket mydocket = paths.build();
        return mydocket;
    }
    private ApiInfo apiInfo(){
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("swagger 短视频API文档");
        apiInfoBuilder.description("简单优雅");
        apiInfoBuilder.version("2.0");
        apiInfoBuilder.contact(new Contact("StephenXu", "https://www.baidu.com", "1163990163@qq.com"));
        apiInfoBuilder.termsOfServiceUrl("localhost");
        return apiInfoBuilder.build();
    }
}
