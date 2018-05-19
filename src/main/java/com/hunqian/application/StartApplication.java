package com.hunqian.application;



import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hunqian.model.TestJsonModel;

import freemarker.template.TemplateException;

@SpringBootApplication //启动spring boot的自动配置等
@RestController //区别于Controller
// extends WebMvcConfigurationSupport ()
public class StartApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
		
		//端口号修改方法一（共三种）
		//SpringApplication.run(StartApplication.class, "--server.port=5000");
		//关闭banner
		/*SpringApplication sp = new SpringApplication(StartApplication.class);
		sp.setBannerMode(Banner.Mode.OFF);
		sp.run(args);*/
	}
	
	@RequestMapping("hw")
	public String hello(){
		return "hello world！！";
	}
	
	@RequestMapping("teststr")
	public String str(){
		return "字符串编码测试！！";
	}
	
	@RequestMapping("json")
	public TestJsonModel json() {
		TestJsonModel json = new TestJsonModel();
		json.setId(1);
		json.setName("魂牵");
		json.setTime(new Date());
		return json;
	}
	
	@RequestMapping("jsp")
	public ModelAndView testJsp() {
		ModelAndView m = new ModelAndView("testjsp");
		Map<String,Object> map = m.getModel();
		TestJsonModel json = new TestJsonModel();
		json.setId(1);
		json.setName("EL表达式");
		json.setTime(new Date());
		map.put("json", json);
		map.put("str", "字符串编码测试");
		return m;
	}
	
	@RequestMapping("ftljsp")
	public ModelAndView testFtlJsp() {
		ModelAndView m = new ModelAndView("testjsp");
		Map<String,Object> map = m.getModel();
		TestJsonModel json = new TestJsonModel();
		json.setId(1);
		json.setName("EL表达式");
		json.setTime(new Date());
		map.put("json", json);
		map.put("str", "字符串编码测试");
		return m;
	}
	
	@RequestMapping("ftl")
	public ModelAndView testFtl() {
		ModelAndView m = new ModelAndView("helloFtl");
		Map<String,Object> map = m.getModel();
		TestJsonModel json = new TestJsonModel();
		json.setId(1);
		json.setName("EL表达式");
		json.setTime(new Date());
		map.put("json", json);
		map.put("str", "字符串编码测试");
		return m;
	}
	
	// 自定义消息转化器
	/*该方法仅限于修改编码方式
	 * */
	/*@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		编码方式修改
		//默认Utf-8 源码（ java.nio.charset.Charset中第613行）
		StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		//反向去测试
		//StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("ISO-8859-1"));
		return converter;
	}*/

	/*
	 * json格式修改
	 * */
	/*@Bean
	public HttpMessageConverters useConverters() {
		FastJsonHttpMessageConverter fast = new FastJsonHttpMessageConverter();
		//处理中文乱码问题(不然出现中文乱码)
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);	
        fast.setSupportedMediaTypes(fastMediaTypes);
        //StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		HttpMessageConverters httpMessageConverters = new HttpMessageConverters(fast);
		return httpMessageConverters;
	}*/
	
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		System.out.println("=================自定义json数据解析开始====================");
		super.configureMessageConverters(converters);
		//定义FastJsonHttpMessageConverter实体类
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setFeatures(Feature.SupportArrayToBean);//这边可以自己定义
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //处理中文乱码问题(不然出现中文乱码)
        List<MediaType> fastMediaTypes = new ArrayList<MediaType>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        converters.add(fastConverter);
        System.out.println("=================自定义json数据解析结束====================");
        System.out.println("=================自定义编码方式开始====================");
        //编码格式修改
        //StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("ISO-8859-1"));
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(converter);
        System.out.println("=================自定义编码方式结束====================");
	}
	
	
	/*
	 * ftl页面控制
	 * 
	 * */
	@Bean
    public FreeMarkerViewResolver getFmViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setOrder(0);
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        //configurer.setTemplateLoaderPaths("file:绝对路径","http://www.xxx.com/");
        configurer.setTemplateLoaderPaths("file:\\C:\\Users\\Administrator\\Desktop\\临时文件\\csdn文件\\spring-boot-hello-master (1)\\spring-boot-hello-master\\src\\main\\webapp\\WEB-INF\\ftl");
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }
    
    /*
     * jsp
     * */
    @Bean
    public ViewResolver getJspViewResolver() {
    	InternalResourceViewResolver jsp = new InternalResourceViewResolver(); 
        jsp.setPrefix("/WEB-INF/jsp/");
        jsp.setSuffix(".jsp");
        jsp.setOrder(1);
        return jsp;
    }
    
}
