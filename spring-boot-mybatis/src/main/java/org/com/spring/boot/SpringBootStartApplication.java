package org.com.spring.boot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * <ul>
 * <li>文件包名 : org.com.spring.boot</li>
 * <li>创建时间 : 2018/1/17 11:09</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author jiaonanyue
 * @version 2.0.0
 */
public class SpringBootStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的SpringBootMybatisApplication启动类
        return builder.sources(SpringBootMybatisApplication.class);
    }

}
