package com.tekenable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.tekenable.controller, com.tekenable.servlet")
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean(name="multipartResolver")
  public StandardServletMultipartResolver resolver(){
    return new StandardServletMultipartResolver();
  }

  @Bean
  public InternalResourceViewResolver getInternalResourceViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Bean
  public WebContentInterceptor webContentInterceptor() {
    WebContentInterceptor interceptor = new WebContentInterceptor();
    interceptor.setCacheSeconds(0);
    interceptor.setUseExpiresHeader(true);
    interceptor.setUseCacheControlHeader(true);
    interceptor.setUseCacheControlNoStore(true);

    return interceptor;
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/bower_components/**").addResourceLocations("/bower_components/");
    registry.addResourceHandler("/views/**").addResourceLocations("/views/");
    registry.addResourceHandler("/app/**").addResourceLocations("/app/");
    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    registry.addResourceHandler("/demo/**").addResourceLocations("/demo/");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(webContentInterceptor());
  }
}