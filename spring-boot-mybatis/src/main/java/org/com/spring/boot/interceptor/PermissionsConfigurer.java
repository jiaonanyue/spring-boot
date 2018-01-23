package org.com.spring.boot.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <ul>
 * <li>文件包名 : org.com.spring.boot.interceptor</li>
 * <li>创建时间 : 2018/1/18 10:22</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author jiaonanyue
 * @version 2.0.0
 */
@Configuration
public class PermissionsConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PermissionsInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
