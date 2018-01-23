**SpringBoot打包war部署**

创建springBoot项目spring-boot-mybatis,修改成war部署到外部的tomcat中。

1：修改pom.xml文件
		

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<!-- 移除嵌入式tomcat插件 -->
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-tomcat</artifactId>
				</exclusion>
			</exclusions>
		
		</dependency>
		
		<!--添加servlet容器jar-->
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>3.1.0</version>
			<scope>provided</scope>
		</dependency>


2：修改启动类 ，在启动类SpringBootMybatisApplication同目录下创建SpringBootStartApplication代码如下

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


这样就修改完了 在idea下打包，将打包好的war放到tomcat下启动访问：
http://localhost:8080/spring-boot-mybatis-0.0.1-SNAPSHOT/index?id=9