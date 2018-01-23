**简介**

	spring boot它设计目的就是为了简化开发，开启了各种自动装配，不用写各种配置文件，引入相关的依赖包就可以快速搭建起一个web工程。

**准备工作**

	jdk1.8 +
	maven 3.0 +
	idea 2017.1.3
	打开idea->File->new->project->spring Initializr->next->填写Group，Artifact->next->web勾选web->next->修改或者不修改项目目录地址->Finish
	
**功能演示**

新建controller包,在包下面新建一个HelloWordController如下：

		package org.com.spring.boot.controller;
		
		import org.springframework.web.bind.annotation.GetMapping;
		import org.springframework.web.bind.annotation.RestController;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.controller</li>
		 * <li>创建时间 : 2018/1/9 14:33</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@RestController
		public class HelloWordController {
		
		
		    @GetMapping("index")
		    public String HelloWord(){
		
		        return "简单的Spring Boot Demo";
		    }
		}


启动SpringBootOneApplication类

		package org.com.spring.boot;
		
		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		
		@SpringBootApplication
		public class SpringBootOneApplication {
		
			public static void main(String[] args) {
				SpringApplication.run(SpringBootOneApplication.class, args);
			}
		}

因为spring boot内嵌套tomcat默认端口为8080，访问地址为：http://localhost:8080/index 出现简单的Spring Boot Demo。
就这样简单的spring boot项目就完成了。

查看项目结构
		- src
		    -main
		        -java
		            -package(org.com.spring.boot自定义包路径)
		                -SpringbootOneApplication
		        -resouces
		            - statics
		            - templates
		            - application.yml(或者application.properties)
		    -test
		- pom

查看pom.xml

		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
			<modelVersion>4.0.0</modelVersion>
		
			<groupId>org.com.spring.boot</groupId>
			<artifactId>spring-boot-one</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<packaging>jar</packaging>
		
			<name>spring-boot-one</name>
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

是不是感觉很简单啊，其实spring boot就是让开发变简单。