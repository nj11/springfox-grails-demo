package com.swagger.test

import com.google.common.base.Predicates
import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.grails.SpringfoxGrailsIntegrationConfiguration
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import static com.google.common.base.Predicates.not
import springfox.documentation.service.Contact;

@EnableSwagger2
@Import([SpringfoxGrailsIntegrationConfiguration])
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Bean
    Docket api() {
        new Docket(DocumentationType.SWAGGER_2).groupName("TODO-RESTAPI")
                .apiInfo(apiInfo())
                .select()
                .apis( RequestHandlerSelectors.basePackage( "com.swagger.test" ) ) //scan this package
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))) //ignore this package
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
    }


    @Bean
    static WebMvcConfigurerAdapter webConfigurer() {
        new WebMvcConfigurerAdapter() {
            @Override
            void addResourceHandlers(ResourceHandlerRegistry registry) {
                if (!registry.hasMappingForPattern("/webjars/**")) {
                    registry
                            .addResourceHandler("/webjars/**")
                            .addResourceLocations("classpath:/META-INF/resources/webjars/")
                }
                if (!registry.hasMappingForPattern("/swagger-ui.html")) {
                    registry
                            .addResourceHandler("/swagger-ui.html")
                            .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html")
                }
            }
        }
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Demo REST Api with Swagger")
                .description(" A simple REST API for TODO application")
                .contact(new Contact("Natasha Juneja", "https://github.com/nj11", "Grails"))
                .version("1.0")
                .build();
    }
}
