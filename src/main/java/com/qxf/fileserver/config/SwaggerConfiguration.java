package com.qxf.fileserver.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    @Autowired
    private TypeResolver typeResolver;

    /**
     * 对外服务swagger文档分组
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {

        ModelRef errorModel = new ModelRef("ExceptionVO");

        List<ResponseMessage> responseMessages = Arrays.asList(
                new ResponseMessageBuilder().code(409).message("Conflict")
                        .responseModel(new ModelRef("FieldExceptionVO")).build(),
                new ResponseMessageBuilder().code(403).message("Forbiddon").responseModel(null).build(),
                new ResponseMessageBuilder().code(404).message("Not Found").responseModel(errorModel).build(),
                new ResponseMessageBuilder().code(500).message("Intenal Error").responseModel(null).build());

        List<Parameter> headerParameter = new ArrayList<Parameter>();

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.qxf")).paths(PathSelectors.any())
                .build()// .groupName("外部接口")
                .globalOperationParameters(headerParameter).apiInfo(apiInfo()).useDefaultResponseMessages(false)
                //.additionalModels(typeResolver.resolve(FieldExceptionVO.class), typeResolver.resolve(ExceptionVO.class))
                .globalResponseMessage(RequestMethod.POST, responseMessages)
                .globalResponseMessage(RequestMethod.PUT, responseMessages)
                .globalResponseMessage(RequestMethod.GET, responseMessages)
                .globalResponseMessage(RequestMethod.DELETE, responseMessages);

    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("QXF", "", "1348717049@qq.com");
        return new ApiInfoBuilder().title("QXF web API  DOC").contact(contact).description("文件服务").build();
    }

    private String getAddress() {
        String hostAddress;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostAddress = "127.0.0.1";
        }
        return hostAddress;
    }

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        int serverPort = event.getEmbeddedServletContainer().getPort();
        System.err.println("swagger访问地址:http://" + getAddress() + ":" + serverPort + "/swagger-ui.html");
    }
}
