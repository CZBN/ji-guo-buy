package edu.nnuzb.jiguo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${image.upload.path:uploads/images/}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File uploadDir = new File(uploadPath);
        registry.addResourceHandler("/image/**")
                .addResourceLocations(uploadDir.toURI().toString())
                .addResourceLocations("classpath:/static/image/");
    }
}
