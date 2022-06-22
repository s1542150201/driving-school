

package io.driving.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**
 * 生成验证码配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        // 图片边框
        properties.put("kaptcha.border", "no");
        //字体颜色
        properties.put("kaptcha.textproducer.font.color", "blue");
        //文字间隔
        properties.put("kaptcha.textproducer.char.space", "4");
        //验证码长度
        properties.put("kaptcha.textproducer.char.length", "4");
        //设置验证码图片宽度
        properties.setProperty("kaptcha.image.width", "90");
        //设置验证码图片高度
        properties.setProperty("kaptcha.image.height", "40");
        //文本集合，验证码值从此集合中获取 0qwertyuiopasdfghjklzxcvbnm
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        //设置验证码文字大小
        properties.setProperty("kaptcha.textproducer.font.size", "26");
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
