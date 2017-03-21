package com.fererlab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.io.File;

// http://docs.spring.io/spring-boot/docs/current/reference/html/auto-configuration-classes.html
//@EnableAutoConfiguration(exclude = {com.fererlab.controller.UploadController.class})
//@EnableAutoConfiguration(excludeName = {"com.fererlab.controller.UploadController"})

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class,
        JmxAutoConfiguration.class, WebSocketAutoConfiguration.class,
})
public class JustGifItApplication {

    @Value("${spring.http.multipart.location}/gif/")
    private String gifLocation;

    public static void main(String[] args) {
        SpringApplication.run(JustGifItApplication.class, args);
    }

    @PostConstruct
    private void init() {
        File gifFolder = new File(gifLocation);
        if (!gifFolder.exists()) {
            gifFolder.mkdir();
        }
    }

    @Bean
    public FilterRegistrationBean deRegisterHiddenHttpFilter(HiddenHttpMethodFilter filter) {
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.setEnabled(false);
        return bean;
    }

    @Bean
    public FilterRegistrationBean deRegisterHttpPutFilter(HttpPutFormContentFilter filter) {
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.setEnabled(false);
        return bean;
    }

    @Bean
    public FilterRegistrationBean deRegisterRequestContextFilter(RequestContextFilter filter) {
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.setEnabled(false);
        return bean;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/gif/**")
                        .addResourceLocations("file:" + gifLocation);
                super.addResourceHandlers(registry);
            }
        };
    }
}
