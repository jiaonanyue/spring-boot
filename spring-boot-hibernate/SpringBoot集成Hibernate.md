**Spring Boot集成Hibernate,创建web项目spring-boot-hibernate。**

 引入相关jar，使用MySQL数据库如下：

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>

替换默认的连接池使用druid

		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
			<version>1.0.29</version>
		</dependency>

整体pom.xml

		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
			<modelVersion>4.0.0</modelVersion>
		
			<groupId>org.com.spring.boot</groupId>
			<artifactId>spring-boot-hibernate</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<packaging>jar</packaging>
		
			<name>spring-boot-hibernate</name>
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
					<artifactId>spring-boot-starter-data-jpa</artifactId>
				</dependency>
				<dependency>
					<groupId>mysql</groupId>
					<artifactId>mysql-connector-java</artifactId>
				</dependency>
				<dependency>
					<groupId>com.alibaba</groupId>
					<artifactId>druid</artifactId>
					<version>1.0.29</version>
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
		
		


**数据库连接配置application.properties:**

注意：指定数据库连接编码 characterEncoding=utf8

		spring.datasource.url = jdbc:mysql://localhost:3306/test1?characterEncoding=utf8
		spring.datasource.username = root
		spring.datasource.password =
		spring.datasource.driverClassName = com.mysql.jdbc.Driver
		spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
		#druid_config
		spring.datasource.maxActive=50
		spring.datasource.initialSize=1
		spring.datasource.minIdle=3
		spring.datasource.maxWait=60000
		spring.datasource.timeBetweenEvictionRunsMillis=60000
		spring.datasource.minEvictableIdleTimeMillis=300000
		spring.datasource.testWhileIdle=true
		spring.datasource.testOnBorrow=false
		spring.datasource.testOnReturn=false
		spring.datasource.poolPreparedStatements=true
		spring.datasource.filters=stat,wall,slf4j
		spring.datasource.urlMappings=/druid/*
		spring.datasource.loginUsername=admin
		spring.datasource.loginPassword=admin
		spring.datasource.resetEnable=false
		#白名单
		spring.datasource.allow=
		#黑名单用，分割
		spring.datasource.deny=
		spring.datasource.urlPatterns=/*
		spring.datasource.exclusions=*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*
		
		
		# Specify the DBMS
		spring.jpa.database = MYSQL
		# Show or not log for each sql query
		spring.jpa.show-sql = true
		# Hibernate ddl auto (create, create-drop, update)
		spring.jpa.hibernate.ddl-auto = update
		# Naming strategy
		spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy
		# stripped before adding them to the entity manager)
		spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect



**在启动类加入注解**@EnableJpaRepositories(basePackages = "org.com.spring.boot.dao")@EntityScan(basePackages = "org.com.spring.boot")

		package org.com.spring.boot;
		
		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.boot.autoconfigure.domain.EntityScan;
		import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
		
		@SpringBootApplication
		@EnableJpaRepositories(basePackages = "org.com.spring.boot.dao")
		@EntityScan(basePackages = "org.com.spring.boot")
		public class SpringBootHibernateApplication {
		
			public static void main(String[] args) {
				SpringApplication.run(SpringBootHibernateApplication.class, args);
			}
		}

**编写dao、controller、entity、config**

config包下创建druid配置类

		package org.com.spring.boot.config;
		
		import com.alibaba.druid.pool.DruidDataSource;
		import com.alibaba.druid.support.http.StatViewServlet;
		import com.alibaba.druid.support.http.WebStatFilter;
		import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
		import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
		import org.springframework.boot.bind.RelaxedPropertyResolver;
		import org.springframework.boot.web.servlet.FilterRegistrationBean;
		import org.springframework.boot.web.servlet.ServletRegistrationBean;
		import org.springframework.context.EnvironmentAware;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.core.env.Environment;
		import org.springframework.transaction.annotation.EnableTransactionManagement;
		
		import javax.sql.DataSource;
		import java.sql.SQLException;
		import java.util.HashMap;
		import java.util.Map;
		
		/**
		 * druid data source configure
		 * driod 配置
		 * @author jiaonanyue
		 */
		@Configuration
		@EnableTransactionManagement
		public class DruidConfigure implements EnvironmentAware {
		
		    private RelaxedPropertyResolver propertyResolver;
		
		    public void setEnvironment(Environment env) {
		        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
		    }
		
		    @Bean
		    public DataSource dataSource() {
		        DruidDataSource datasource = new DruidDataSource();
		        datasource.setUrl(propertyResolver.getProperty("url"));
		        datasource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
		        datasource.setUsername(propertyResolver.getProperty("username"));
		        datasource.setPassword(propertyResolver.getProperty("password"));
		        datasource.setInitialSize(Integer.valueOf(propertyResolver.getProperty("initial-size")));
		        datasource.setMinIdle(Integer.valueOf(propertyResolver.getProperty("min-idle")));
		        datasource.setMaxWait(Long.valueOf(propertyResolver.getProperty("max-wait")));
		        datasource.setMaxActive(Integer.valueOf(propertyResolver.getProperty("max-active")));
		        datasource.setMinEvictableIdleTimeMillis(
		                Long.valueOf(propertyResolver.getProperty("min-evictable-idle-time-millis")));
		        try {
		            datasource.setFilters("stat,wall");
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return datasource;
		    }
		
		    /**
		     * 访问地址http://localhost:8776/druid/datasource.html
		     * @return
		     */
		    @Bean
		    public ServletRegistrationBean druidServlet() {
		        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
		        servletRegistrationBean.setServlet(new StatViewServlet());
		        servletRegistrationBean.addUrlMappings("/druid/*");
		        Map<String, String> initParameters = new HashMap<String, String>();
		        initParameters.put("loginUsername", "admin");// 用户名
		        initParameters.put("loginPassword", "admin");// 密码
		        initParameters.put("resetEnable", "false");// 禁用HTML页面上的“Reset All”功能
		        initParameters.put("allow", ""); // IP白名单 (没有配置或者为空，则允许所有访问)
		        // initParameters.put("deny", "192.168.20.38");// IP黑名单
		        // (存在共同时，deny优先于allow)
		        servletRegistrationBean.setInitParameters(initParameters);
		        return servletRegistrationBean;
		    }
		
		    @Bean
		    public FilterRegistrationBean filterRegistrationBean() {
		        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		        filterRegistrationBean.setFilter(new WebStatFilter());
		        filterRegistrationBean.addUrlPatterns("/*");
		        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		        return filterRegistrationBean;
		    }
		
		    // 按照BeanId来拦截配置 用来bean的监控
		    @Bean(value = "druid-stat-interceptor")
		    public DruidStatInterceptor DruidStatInterceptor() {
		        return new DruidStatInterceptor();
		    }
		
		    @Bean
		    public BeanNameAutoProxyCreator beanNameAutoProxyCreator() {
		        BeanNameAutoProxyCreator beanNameAutoProxyCreator = new BeanNameAutoProxyCreator();
		        beanNameAutoProxyCreator.setProxyTargetClass(true);
		        // 设置要监控的bean的id
		        //beanNameAutoProxyCreator.setBeanNames("UserMapper","UsersController");
		        beanNameAutoProxyCreator.setInterceptorNames("druid-stat-interceptor");
		        return beanNameAutoProxyCreator;
		    }
		
		}


entity中User实体

		package org.com.spring.boot.enitiy;
		
		
		
		import javax.persistence.*;
		import java.util.Date;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.enitiy</li>
		 * <li>创建时间 : 2018/1/10 11:36</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@Entity
		@Table(name="user")
		public class User {
		
		    @Id
		    @GeneratedValue(strategy= GenerationType.AUTO)
		    private Long id;
		
		    private String userName;
		
		    private Date birthDay;
		
		    private String sex;
		
		    private String address;
		
		
		    public Long getId() {
		        return id;
		    }
		
		    public void setId(Long id) {
		        this.id = id;
		    }
		
		    public String getUserName() {
		        return userName;
		    }
		
		    public void setUserName(String userName) {
		        this.userName = userName;
		    }
		
		    public Date getBirthDay() {
		        return birthDay;
		    }
		
		    public void setBirthDay(Date birthDay) {
		        this.birthDay = birthDay;
		    }
		
		    public String getSex() {
		        return sex;
		    }
		
		    public void setSex(String sex) {
		        this.sex = sex;
		    }
		
		    public String getAddress() {
		        return address;
		    }
		
		    public void setAddress(String address) {
		        this.address = address;
		    }
		
		    @Override
		    public String toString() {
		        return "User{" +
		                "id=" + id +
		                ", userName='" + userName + '\'' +
		                ", birthDay=" + birthDay +
		                ", sex='" + sex + '\'' +
		                ", address='" + address + '\'' +
		                '}';
		    }
		}


dao包下，Userdao接口继承CrudRepository类

		package org.com.spring.boot.dao;
		
		import org.com.spring.boot.enitiy.User;
		import org.springframework.beans.factory.annotation.Qualifier;
		import org.springframework.data.jpa.repository.Query;
		import org.springframework.data.repository.CrudRepository;
		import org.springframework.data.repository.query.Param;
		import org.springframework.stereotype.Repository;
		
		import javax.persistence.Table;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.dao</li>
		 * <li>创建时间 : 2018/1/10 11:38</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@Repository
		@Table(name="user")
		public interface UserDao extends CrudRepository<User, Long > {
		
		    /**
		     *
		     * @param id
		     * @return
		     */
		    @Override
		    User findOne(Long id);
		
		    /**
		     *
		     * @param u
		     * @return
		     */
		    @Override
		    User save(User u);
		
		    @Query("select t from User t where t.userName=:name")
		    User findUserByName(@Param("name") String name);
		
		}



controller 编写访问映射

		package org.com.spring.boot.controller;
		
		import org.com.spring.boot.dao.UserDao;
		import org.com.spring.boot.enitiy.User;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
		import org.springframework.stereotype.Controller;
		import org.springframework.web.bind.annotation.*;
		
		import java.util.Date;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.controller</li>
		 * <li>创建时间 : 2018/1/10 11:42</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@RestController
		@RequestMapping("/hibernate")
		public class HibernateController {
		
		    private final UserDao userRepository;
		
		    public HibernateController(UserDao userRepository) {
		        this.userRepository = userRepository;
		    }
		
		    @GetMapping("getUserById")
		    public User getUserById(Long id) {
		
		        User u = userRepository.findOne(id);
		        System.out.println("userRepository: " + userRepository);
		        System.out.println("id: " + id);
		        return u;
		    }
		
		    @GetMapping("saveUser")
		    public void saveUser() {
		        User u = new User();
		        u.setUserName("wan");
		        u.setAddress("江西省上饶市鄱阳县");
		        u.setBirthDay(new Date());
		        u.setSex("男");
		        userRepository.save(u);
		    }
		
		
		}

到MySQL数据库创建test1数据库指定编码为utf-8,然后启动项目SpringBootHibernateApplication

访问地址：http://localhost:8080/hibernate/saveUser 如果页面没有报错说明执行成功，查看数据库是不是创建了user表并插入另一条数据
在访问地址：http://localhost:8080/hibernate/getUserById?id=4 查出相关数据

访问数据库监控地址：http://localhost:8080/druid/ 用户名和密码是 admin

简单的springboot集成tomcat就完成了