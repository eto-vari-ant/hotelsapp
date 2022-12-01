package com.travel.hotelsapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collection;
import java.util.Collections;

@EnableWebMvc
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.removeConvertible(String.class, Collection.class);
        registry.addConverter(String.class, Collection.class, noCommaSplitStringToCollectionConverter());
    }

    public Converter<String, Collection> noCommaSplitStringToCollectionConverter() {
        return Collections::singletonList;
    }
}
