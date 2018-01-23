**springboot集成发送邮箱功能**

创建spring-boot-mail项目

引入相关jar

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
整体pom.xml
		
		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
			<modelVersion>4.0.0</modelVersion>
		
			<groupId>org.com.spring.boot</groupId>
			<artifactId>spring-boot-mail</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<packaging>jar</packaging>
		
			<name>spring-boot-mail</name>
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
					<artifactId>spring-boot-starter-mail</artifactId>
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

参数配置 application.properties

		spring.mail.host=smtp.163.com
		spring.mail.username=XXX@163.com
		spring.mail.password=XXXX
		spring.mail.port=25
		spring.mail.protocol=smtp
		spring.mail.default-encoding=UTF-8

创建mail、controller包

在mail下创建 MailUtil类，编写常用的邮箱发送格式。

		package org.com.spring.boot.mail;
		
		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.beans.factory.annotation.Value;
		import org.springframework.core.io.FileSystemResource;
		import org.springframework.mail.SimpleMailMessage;
		import org.springframework.mail.javamail.JavaMailSenderImpl;
		import org.springframework.mail.javamail.MimeMessageHelper;
		import org.springframework.stereotype.Component;
		
		import javax.mail.internet.MimeMessage;
		import java.io.File;
		
		/**
		 * <ul>
		 * <li>文件包名 : com.platform.provider.provider.mail</li>
		 * <li>创建时间 : 2017/12/8 16:38</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：邮箱工具类
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@Component
		public class MailUtil {
		
		    private  final Logger logger = LoggerFactory.getLogger(getClass());
		
		
		    @Autowired
		    private JavaMailSenderImpl mailSender;
		
		    @Value("${spring.mail.username}")
		    private String userName;
		
		
		    /**
		     * 发送包含简单文本的邮件
		     */
		    public void sendTxtMail() {
		        logger.info("发送邮箱开始");
		        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		        // 设置收件人，寄件人
		        simpleMailMessage.setTo(new String[] {"1038612639@qq.com","281228865@qq.com"});
		        simpleMailMessage.setFrom(userName);
		        logger.info("配置文件中userName==="+userName);
		        simpleMailMessage.setSubject("激活邮箱");
		        simpleMailMessage.setText("<h1>此邮件为官方激活邮件</h1>\",\"text/html;charset=UTF-8");
		        // 发送邮件
		        mailSender.send(simpleMailMessage);
		
		        logger.info("发送邮箱结束");
		    }
		
		    /**
		     * 发送包含HTML文本的邮件
		     * @throws Exception
		     */
		    public void sendHtmlMail() throws Exception {
		
		        logger.info("发送邮箱开始");
		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
		        mimeMessageHelper.setTo("miles02@163.com");
		        mimeMessageHelper.setFrom("miles02@163.com");
		        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【HTML】");
		
		        StringBuilder sb = new StringBuilder();
		        sb.append("<html><head></head>");
		        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
		        sb.append("</html>");
		
		        // 启用html
		        mimeMessageHelper.setText(sb.toString(), true);
		        // 发送邮件
		        mailSender.send(mimeMessage);
		
		        logger.info("发送邮箱结束");
		
		    }
		
		    /**
		     * 发送包含内嵌图片的邮件
		     * @throws Exception
		     */
		    public void sendAttachedImageMail() throws Exception {
		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        // multipart模式
		        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		        mimeMessageHelper.setTo("miles02@163.com");
		        mimeMessageHelper.setFrom("miles02@163.com");
		        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【图片】");
		
		        StringBuilder sb = new StringBuilder();
		        sb.append("<html><head></head>");
		        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p>");
		        // cid为固定写法，imageId指定一个标识
		        sb.append("<img src=\"cid:imageId\"/></body>");
		        sb.append("</html>");
		
		        // 启用html
		        mimeMessageHelper.setText(sb.toString(), true);
		
		        // 设置imageId
		        FileSystemResource img = new FileSystemResource(new File("E:/1.jpg"));
		        mimeMessageHelper.addInline("imageId", img);
		
		        // 发送邮件
		        mailSender.send(mimeMessage);
		
		        System.out.println("邮件已发送");
		    }
		
		    /**
		     * 发送包含附件的邮件
		     * @throws Exception
		     */
		    public void sendAttendedFileMail() throws Exception {
		        MimeMessage mimeMessage = mailSender.createMimeMessage();
		        // multipart模式
		        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		        mimeMessageHelper.setTo("miles02@163.com");
		        mimeMessageHelper.setFrom("miles02@163.com");
		        mimeMessageHelper.setSubject("Spring Boot Mail 邮件测试【附件】");
		
		        StringBuilder sb = new StringBuilder();
		        sb.append("<html><head></head>");
		        sb.append("<body><h1>spring 邮件测试</h1><p>hello!this is spring mail test。</p></body>");
		        sb.append("</html>");
		
		        // 启用html
		        mimeMessageHelper.setText(sb.toString(), true);
		        // 设置附件
		        FileSystemResource img = new FileSystemResource(new File("E:/1.jpg"));
		        mimeMessageHelper.addAttachment("image.jpg", img);
		
		        // 发送邮件
		        mailSender.send(mimeMessage);
		
		        System.out.println("邮件已发送");
		    }
		
		
		}

controller包下创建测试类 MailController

		package org.com.spring.boot.controller;
		
		import org.com.spring.boot.mail.MailUtil;
		import org.springframework.web.bind.annotation.GetMapping;
		import org.springframework.web.bind.annotation.RestController;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.controller</li>
		 * <li>创建时间 : 2018/1/11 13:58</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@RestController
		public class MailController {
		
		    private final MailUtil mailUtil;
		
		    public MailController(MailUtil mailUtil) {
		        this.mailUtil = mailUtil;
		    }
		
		    @GetMapping("send")
		    public String sendMail(){
		
		        mailUtil.sendTxtMail();
		
		        return "发送邮箱";
		    }
		}

启动项目类SpringBootMailApplication

访问地址：http://localhost:8080/send

发送邮箱功能完成。