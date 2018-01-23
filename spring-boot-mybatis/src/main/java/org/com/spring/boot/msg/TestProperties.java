package org.com.spring.boot.msg;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * <ul>
 * <li>文件包名 : org.com.spring.boot.msg</li>
 * <li>创建时间 : 2018/1/18 13:52</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author jiaonanyue
 * @version 2.0.0
 */
@Configuration
@ImportResource(locations={"classpath:properties/spring-properties.xml"})
public class TestProperties {


}
