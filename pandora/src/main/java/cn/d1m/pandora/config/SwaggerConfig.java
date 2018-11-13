package cn.d1m.pandora.config;

import cn.d1m.pandora.utils.CommonConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Timestamp;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile({"dev", "local"})
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String profile;


    @Bean
    public Docket docket() {

        final Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("d1m.cn")
                .select().apis(RequestHandlerSelectors.basePackage("cn.d1m.pandora.controller"))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .globalOperationParameters(getGlobalParameters())
                .apiInfo(baseAPIInfo())
                .directModelSubstitute(Timestamp.class, Long.class);
        if (!"local".equalsIgnoreCase(profile)) {
            docket.host(profile + ".wechat.d1m.cn/pandora");
        }
        return docket;

    }

    private List<Parameter> getGlobalParameters() {
        final Parameter token1 = new ParameterBuilder()
                .name("token")
                .description("访问令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        final Parameter token2 = new ParameterBuilder()
                .name(CommonConstants.X_SESSION_TOKEN)
                .description("访问令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        return Lists.newArrayList(token1, token2);
    }

    private ApiInfo baseAPIInfo() {
        return new ApiInfo(
                "pandora-service",
                "Pandora API Documentation",
                "1.0",
                "Terms of service",
                new Contact("Jone.wang", "https://www.d1m.cn", "82099359@qq.com"),
                "License of API",
                "https://www.d1m.cn", Lists.newArrayList(new StringVendorExtension("author", "jone"),
                new StringVendorExtension("author", "swagger2")));
    }
}
