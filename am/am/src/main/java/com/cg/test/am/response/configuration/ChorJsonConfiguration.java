package com.cg.test.am.response.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * json序列化配置
 * @author hao.yan
 */
@Configuration
public class ChorJsonConfiguration implements WebMvcConfigurer {

	/**
	 * 替换框架json为fastjson
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);

//		super.configureMessageConverters(converters);
		// 1.需要先定义一个convert 转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		// FastJsonHttpMessageConverter fastConverter = new
		// FastJsonHttpMessageConverterImpl();
		// 2.添加fastJson的配置信息,比如，是否需要格式化返回的json数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// 空值特别处理
		// WriteNullListAsEmpty 将Collection类型字段的字段空值输出为[]
		// WriteNullStringAsEmpty 将字符串类型字段的空值输出为空字符串 ""
		// WriteNullNumberAsZero 将数值类型字段的空值输出为0
		// WriteNullBooleanAsFalse 将Boolean类型字段的空值输出为false
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue
				, SerializerFeature.PrettyFormat
				, SerializerFeature.WriteNullListAsEmpty
				, SerializerFeature.WriteNullStringAsEmpty
				, SerializerFeature.WriteMapNullValue);

		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
		// 处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		// 3.在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		// 4.将convert添加到converters当中
		converters.add(fastConverter);
	}

}