package com.fanyin.configuration;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Properties;

/**
 * 公用配置,所有web模块所公用的配置信息均可在该配置文件中声明
 * @author 二哥很猛
 * @date 2018/9/13 11:19
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {


    /**
     * 图形验证码
     * @return bean
     */
    @Bean("producer")
    public DefaultKaptcha captcha(){
        DefaultKaptcha captcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_BORDER,"no");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"black");
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH,"125");
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT,"45");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE,"5");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING,"abcdefhkmnprstwxy23456789ABCEFGHGKMNPRSTWXY");
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES,"宋体");
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL,"com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        captcha.setConfig(config);
        return captcha;
    }


    /**
     * url中如果包含 "." 默认情况下后面的会被截取,将参数设置为false则会全部匹配
     * @param configurer configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseRegisteredSuffixPatternMatch(false);
    }

    @Bean
    public MBeanExporter mBeanExporter(){
        MBeanExporter exporter = new MBeanExporter();
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("kaptcha:name=DefaultKaptcha",captcha());
        exporter.setBeans(map);
        return exporter;
    }

}
