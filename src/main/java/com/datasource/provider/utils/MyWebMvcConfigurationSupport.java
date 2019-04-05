package com.datasource.provider.utils;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器中，只能有一个类集成WebMvcConfigurationSupport，出现多个类集成WebMvcConfigurationSupport时，配置是不生效的
 */
@Configuration
public class MyWebMvcConfigurationSupport extends WebMvcConfigurationSupport {
    @Value("${spring.jackson.date-format}")
    private String dataFormat;
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
            //1.需要先定义一个convert 转换消息的对象
            FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
            //2.添加fastjson配置信息
            FastJsonConfig fastJsonConfig =new FastJsonConfig();
            /**
             * 空值特别处理
             * WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
             * WriteNullStringAsEmpty   将字符串类型字段的空值输出为空字符串 ""
             * WriteNullBooleanAsFalse  将数值类型字段的空值输出为0
             * WriteNullNumberAsZero    将Boolean类型字段的空值输出为false
             */
        fastJsonConfig.setDateFormat(dataFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteNullNumberAsZero);
        ValueFilter valueFilter = new ValueFilter() {
            //o 是class
            //s 是key值
            //o1 是value值
            public Object process(Object o, String s, Object o1) {
                if (null == o1){
                    o1 = "";
                }
                return o1;
            }
        };
        fastJsonConfig.setSerializeFilters(valueFilter);
        // 处理中文乱码问题
        List<MediaType> fastMediaTypes =new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        //在convert添加配置信息
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //将convert添加到converts中
        converters.add(fastJsonHttpMessageConverter);
    }

}

