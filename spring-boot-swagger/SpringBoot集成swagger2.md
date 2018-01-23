**SpringBoot集成swagger,构建优雅的restful API风格**

创建项目spring-boot-swagger.

引入jar

		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger2</artifactId>
			<version>2.6.1</version>
		</dependency>
		
		<dependency>
			<groupId>io.springfox</groupId>
			<artifactId>springfox-swagger-ui</artifactId>
			<version>2.6.1</version>
		</dependency>

整体pom.xml

		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
			<modelVersion>4.0.0</modelVersion>
		
			<groupId>org.com.spring.boot</groupId>
			<artifactId>spring-boot-swagger</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<packaging>jar</packaging>
		
			<name>spring-boot-swagger</name>
			<description>Demo project for Spring Boot</description>
		
			<parent>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-parent</artifactId>
				<version>1.5.9.RELEASE</version>
				<relativePath/> <!-- lookup parent from repository -->
			</parent>
		
			<properties>
				<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
				<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
				<java.version>1.8</java.version>
			</properties>
		
			<dependencies>
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-web</artifactId>
				</dependency>
		
				<dependency>
					<groupId>io.springfox</groupId>
					<artifactId>springfox-swagger2</artifactId>
					<version>2.6.1</version>
				</dependency>
		
				<dependency>
					<groupId>io.springfox</groupId>
					<artifactId>springfox-swagger-ui</artifactId>
					<version>2.6.1</version>
				</dependency>
		
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-test</artifactId>
					<scope>test</scope>
				</dependency>
			</dependencies>
		
			<build>
				<plugins>
					<plugin>
						<groupId>org.springframework.boot</groupId>
						<artifactId>spring-boot-maven-plugin</artifactId>
					</plugin>
				</plugins>
			</build>
		
		
		</project>


swagger配置文件

		# swagger config
		swagger.basepackage=org.com.spring.boot
		swagger.service.name= API support docs
		swagger.service.description= micro service
		swagger.service.developer=jiaonan.yue
		swagger.service.url=http://localhost:8080/swagger-ui.html
		swagger.service.mail=m15528310759@163.com



创建controller、config包


config包下创建SwaggerConfiguration 引入配置文件信息及和springboot集成配置


		package org.com.spring.boot.config;
		
		import org.springframework.boot.bind.RelaxedPropertyResolver;
		import org.springframework.context.EnvironmentAware;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.core.env.Environment;
		import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
		import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
		import springfox.documentation.builders.ApiInfoBuilder;
		import springfox.documentation.builders.ParameterBuilder;
		import springfox.documentation.builders.PathSelectors;
		import springfox.documentation.builders.RequestHandlerSelectors;
		import springfox.documentation.schema.ModelRef;
		import springfox.documentation.service.ApiInfo;
		import springfox.documentation.service.Contact;
		import springfox.documentation.service.Parameter;
		import springfox.documentation.spi.DocumentationType;
		import springfox.documentation.spring.web.plugins.Docket;
		import springfox.documentation.swagger2.annotations.EnableSwagger2;
		
		import java.util.ArrayList;
		import java.util.List;
		
		/**
		 * swagger配置项
		 *
		 * @description
		 * @author jiaonanyue
		 * @date 2017年8月2日
		 * @since 1.7
		 */
		@Configuration
		@EnableSwagger2
		public class SwaggerConfiguration extends WebMvcConfigurerAdapter implements EnvironmentAware {
		
			private String basePackage;
			private String creatName;
			private String serviceName;
			private RelaxedPropertyResolver propertyResolver;
			private String description;
			private String url;
			private String mail;
		
		
			/**
			 * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
			 *  <!--swagger资源配置-->
			 *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
			 *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
			 *
			 * @param registry
			 */
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("swagger-ui.html")
						.addResourceLocations("classpath:/META-INF/resources/");
				registry.addResourceHandler("/webjars*")
						.addResourceLocations("classpath:/META-INF/resources/webjars/");
			}
		
		
			
			@Bean
			public Docket createRestApi() {
				ParameterBuilder tokenPar = new ParameterBuilder();
				List<Parameter> pars = new ArrayList<Parameter>();
				tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
				pars.add(tokenPar.build());
				return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
						.select()
						.apis(RequestHandlerSelectors.basePackage(this.basePackage))
						.paths(PathSelectors.any())
						.build()
						.globalOperationParameters(pars)  ;
			}
		
			private ApiInfo apiInfo() {
				return new ApiInfoBuilder()
						.title(this.serviceName)
						.description(this.description)
						.contact(new Contact(this.creatName,this.url,this.mail)).version("1.0").build();
			}
		
			@Override
			public void setEnvironment(Environment environment) {
				this.propertyResolver = new RelaxedPropertyResolver(environment, null);
				this.basePackage = propertyResolver.getProperty("swagger.basepackage");
				this.creatName = propertyResolver.getProperty("swagger.service.developer");
				this.serviceName = propertyResolver.getProperty("swagger.service.name");
				this.description = propertyResolver.getProperty("swagger.service.description");
				this.url = propertyResolver.getProperty("swagger.service.url");
				this.mail = propertyResolver.getProperty("swagger.service.mail");
			}
		}
		

controoler包下创建UserController类进行测试

		package org.com.spring.boot.controller;
		
		import org.springframework.web.bind.annotation.DeleteMapping;
		import org.springframework.web.bind.annotation.GetMapping;
		import org.springframework.web.bind.annotation.PostMapping;
		import org.springframework.web.bind.annotation.RestController;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.controller</li>
		 * <li>创建时间 : 2018/1/11 10:24</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@RestController
		public class UserController {
		
		
		    @PostMapping("add")
		    public String add(String name){
		
		        return "添加成功";
		    }
		
		    @DeleteMapping("delete")
		    public String delete(long id){
		
		        return "删除成功";
		    }
		
		    @PostMapping("update")
		    public String update(){
		
		        return "更新成功";
		    }
		
		    @GetMapping("get")
		    public String get(){
		
		        return "查询成功";
		    }
		}


启动项目SpringBootSwaggerApplication类
访问地址：http://localhost:8080/swagger-ui.html
到页面进行相关接口测试

springboot集成swagger完成。