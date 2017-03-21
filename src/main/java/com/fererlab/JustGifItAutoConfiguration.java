package com.fererlab;

import com.fererlab.services.ConverterService;
import com.fererlab.services.GifEncoderService;
import com.fererlab.services.VideoDecoderService;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import org.bytedeco.javacv.FFmpegFrameFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
@ConditionalOnClass({FFmpegFrameFilter.class, AnimatedGifEncoder.class})
public class JustGifItAutoConfiguration {


    @Value("${spring.http.multipart.location}/gif/")
    private String gifLocation;

    @ConditionalOnProperty(prefix = "com.fererlab",
            name = "create-result-dir")
    private boolean createResultDir() {
        File gifFolder = new File(gifLocation);
        if (!gifFolder.exists()) {
            gifFolder.mkdir();
        }
        return true;
    }

    @Bean
    @ConditionalOnMissingBean(VideoDecoderService.class)
    public VideoDecoderService videoDecoderService() {
        return new VideoDecoderService();
    }


    @Bean
    @ConditionalOnMissingBean(GifEncoderService.class)
    public GifEncoderService gifEncoderService() {
        return new GifEncoderService();
    }


    @Bean
    @ConditionalOnMissingBean(ConverterService.class)
    public ConverterService converterService() {
        return new ConverterService();
    }

    @Configuration
    @ConditionalOnWebApplication
    public static class WebConfiguration {

        @Value("${spring.http.multipart.location}/gif/")
        private String gifLocation;

        @Bean
        @ConditionalOnProperty(prefix = "com.fererlab", name = "optimize")
        public FilterRegistrationBean deRegisterHiddenHttpFilter(HiddenHttpMethodFilter filter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(filter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        @ConditionalOnProperty(prefix = "com.fererlab", name = "optimize")
        public FilterRegistrationBean deRegisterHttpPutFilter(HttpPutFormContentFilter filter) {
            FilterRegistrationBean bean = new FilterRegistrationBean(filter);
            bean.setEnabled(false);
            return bean;
        }

        @Bean
        @ConditionalOnProperty(prefix = "com.fererlab", name = "optimize")
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
}
