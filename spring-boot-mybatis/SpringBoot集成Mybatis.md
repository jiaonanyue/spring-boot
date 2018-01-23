**新建一个spring-boot-mybatis项目**

引入相应的jar


		<!--引入spring boot 整合mybatis相关jar包-->
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>1.3.0</version>
		</dependency>

		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.38</version>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid</artifactId>
			<version>1.0.29</version>
		</dependency>

		<!--这个jar提供mybatis公共方法-->
		<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus</artifactId>
			<version>2.0.8</version>
		</dependency>


整体pom.xml

		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
			<modelVersion>4.0.0</modelVersion>
		
			<groupId>org.com.spring.boot</groupId>
			<artifactId>spring-boot-mybatis</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<packaging>jar</packaging>
		
			<name>spring-boot-mybatis</name>
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
		
				<!--引入spring boot 整合mybatis相关jar包-->
				<dependency>
					<groupId>org.mybatis.spring.boot</groupId>
					<artifactId>mybatis-spring-boot-starter</artifactId>
					<version>1.3.0</version>
				</dependency>
		
				<dependency>
					<groupId>mysql</groupId>
					<artifactId>mysql-connector-java</artifactId>
					<version>5.1.38</version>
				</dependency>
				<dependency>
					<groupId>com.alibaba</groupId>
					<artifactId>druid</artifactId>
					<version>1.0.29</version>
				</dependency>
		
				<!--这个jar提供mybatis公共方法-->
				<dependency>
					<groupId>com.baomidou</groupId>
					<artifactId>mybatis-plus</artifactId>
					<version>2.0.8</version>
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

application.properties（项目主配置文件）配置连接数据库参数


		#可以修改tomcat默认端口
		server.port=8081
		# jdbc_config   datasource
		spring.datasource.url=jdbc:mysql://localhost:3306/oneking_member?useUnicode=true&characterEncoding=UTF8
		spring.datasource.username=root
		spring.datasource.password=
		spring.datasource.driver=com.mysql.jdbc.Driver
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
		
		
		# mybatis_config mybatis-spring扫描路径
		mybatis.mapper-locations=classpath:/mapper/*Mapper.xml 
		mybatis.typeAliasesPackage=org.com.spring.boot.entity

新建常用的包路径entity、dao、controller、service、db、msg及相应的代码


controller下BlacklistController类

		package org.com.spring.boot.controller;
		
		import org.com.spring.boot.entity.Blacklist;
		import org.com.spring.boot.service.IBlacklistServer;
		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		import org.springframework.util.StringUtils;
		import org.springframework.web.bind.annotation.*;
		
		
		/**
		 * 黑名单操作类Controller
		 * @author
		 * @create 2017-09-05 16:45
		 **/
		@RestController
		public class BlacklistController extends BaseController<IBlacklistServer,Blacklist> {
		
		    private final Logger logger = LoggerFactory.getLogger(getClass());
		
		    private final IBlacklistServer blacklistServer;
		
		
		
		    public BlacklistController(IBlacklistServer blacklistServer) {
		        this.blacklistServer = blacklistServer;
		    }
		
		    /**
		     *
		     * @param id
		     * @return
		     */
		    @GetMapping("/index")
		    public Blacklist getBlacklist(String id){
		
		        if(StringUtils.isEmpty(id)){
		            return new Blacklist();
		        }
		        long start = System.currentTimeMillis();
		
		        Blacklist blacklist = blacklistServer.getBlacklist(Long.valueOf(id));
		        long end = System.currentTimeMillis();
		
		        logger.info("总时间为：==="+(end-start));
		        return blacklist ;
		    }
		
		}


service 下接口 继承共方法接口

		package org.com.spring.boot.service;
		
		
		import com.baomidou.mybatisplus.service.IService;
		import org.com.spring.boot.entity.Blacklist;
		
		
		/**
		 * 黑名单接口
		 * @author
		 * @create 2017-09-05 16:02
		 **/
		public interface IBlacklistServer extends IService<Blacklist> {
		
		
		    Blacklist getBlacklist(Long id);
		
		}



service下接口实现类 继承公共方法实现类

		package org.com.spring.boot.service.impl;
		
		import com.baomidou.mybatisplus.service.impl.ServiceImpl;
		import org.com.spring.boot.dao.BlacklistMapper;
		import org.com.spring.boot.entity.Blacklist;
		import org.com.spring.boot.service.IBlacklistServer;
		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		import org.springframework.stereotype.Service;
		
		
		
		/**
		 * 黑名单名单
		 * @author
		 * @create 2017-09-05 16:22
		 **/
		@Service
		public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements IBlacklistServer {
		
		    private final Logger logger = LoggerFactory.getLogger(getClass());
		
		    private  final BlacklistMapper blacklistMapper;
		
		    public BlacklistServiceImpl(BlacklistMapper blacklistMapper) {
		        this.blacklistMapper = blacklistMapper;
		    }
		
		
		    @Override
		    public Blacklist getBlacklist(Long id) {
		
		        logger.info("进入查询语句");
		        Blacklist blacklist = blacklistMapper.getModel(id);
		
		        return blacklist;
		    }
		
		}

dao下 BlacklistMapper对应的方法接口


		package org.com.spring.boot.dao;
		
		
		import com.baomidou.mybatisplus.mapper.BaseMapper;
		import org.apache.ibatis.annotations.Select;
		import org.com.spring.boot.entity.Blacklist;
		
		public interface BlacklistMapper extends BaseMapper<Blacklist> {
		
		    @Select("select b.id,b.platform,b.description,b.host from base_blacklist b where id = #{id}")
		    Blacklist getModel(Long id);
		
		}


entity 下Blacklist实体继承公共的BaseEntity实体类


		package org.com.spring.boot.entity;
		
		import com.baomidou.mybatisplus.annotations.TableField;
		import com.baomidou.mybatisplus.annotations.TableName;
		
		
		/**
		 * 黑名单表
		 */
		@TableName("base_blacklist")
		public class Blacklist extends BaseEntity {
		
		
		    /**
		     * I
		     * ip地址
		     */
		    private String host;
		
		    /**
		     * 状态 0：停用 1：使用
		     */
		    private Boolean status;
		
		    /**
		     * 黑名单账户
		     */
		    @TableField("user_name")
		    private String userName;
		
		
		    /**
		     * 说明
		     */
		    private String description;
		
		    /**
		     *
		     */
		    private Byte type ;
		
		
		
		    public String getHost() {
		        return host;
		    }
		
		    public void setHost(String host) {
		        this.host = host;
		    }
		
		    public Boolean getStatus() {
		        return status;
		    }
		
		    public void setStatus(Boolean status) {
		        this.status = status;
		    }
		
		    public String getUserName() {
		        return userName;
		    }
		
		    public void setUserName(String userName) {
		        this.userName = userName;
		    }
		
		    public String getDescription() {
		        return description;
		    }
		
		    public void setDescription(String description) {
		        this.description = description;
		    }
		
		    public Byte getType() {
		        return type;
		    }
		
		    public void setType(Byte type) {
		        this.type = type;
		    }
		
		    @Override
		    public String toString() {
		        return "Blacklist{" +
		                "host='" + host + '\'' +
		                ", status=" + status +
		                ", userName='" + userName + '\'' +
		                ", description='" + description + '\'' +
		                ", type=" + type +
		                '}';
		    }
		}



mybatis和springboot集成配置文件

		package org.com.spring.boot.db;
		
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
		 *
		 * @author jiaonanyue
		 */
		@Configuration
		@EnableTransactionManagement//enable tx
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



mybatis-plus公共方法配置文件


		package org.com.spring.boot.db;
		
		import com.baomidou.mybatisplus.MybatisConfiguration;
		import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
		import com.baomidou.mybatisplus.entity.GlobalConfiguration;
		import com.baomidou.mybatisplus.enums.DBType;
		import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
		import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
		import org.apache.ibatis.mapping.DatabaseIdProvider;
		import org.apache.ibatis.plugin.Interceptor;
		import org.mybatis.spring.annotation.MapperScan;
		import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
		import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.core.io.DefaultResourceLoader;
		import org.springframework.core.io.ResourceLoader;
		import org.springframework.util.ObjectUtils;
		import org.springframework.util.StringUtils;
		
		import javax.annotation.Resource;
		import javax.sql.DataSource;
		
		
		@Configuration
		@MapperScan("org.com.spring.boot.dao*")
		public class MybatisPlusConfigure {
		
		    @Resource
		    private DataSource dataSource;
		
		    @Resource
		    private MybatisProperties properties;
		
		    @Resource
		    private ResourceLoader resourceLoader = new DefaultResourceLoader();
		
		    @Autowired(required = false)
		    private Interceptor[] interceptors;
		
		    @Autowired(required = false)
		    private DatabaseIdProvider databaseIdProvider;
		
		
		    /**
		     * mybatis-plus分页插件<br>
		     * <p>
		     * 文档：http://mp.baomidou.com<br>
		     */
		    @Bean
		    public PaginationInterceptor paginationInterceptor() {
		        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		        paginationInterceptor.setDialectType(DBType.MYSQL.getDb());
		        return paginationInterceptor;
		    }
		
		
		    /**
		     * 这里全部使用mybatis-autoconfigure 已经自动加载的资源。不手动指定
		     * 配置文件和mybatis-boot的配置文件同步
		     */
		    @Bean
		    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
		        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
		        mybatisPlus.setDataSource(dataSource);
		        mybatisPlus.setVfs(SpringBootVFS.class);
		        if (StringUtils.hasText(this.properties.getConfigLocation())) {
		            mybatisPlus.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
		        }
		        mybatisPlus.setConfiguration(properties.getConfiguration());
		        if (!ObjectUtils.isEmpty(this.interceptors)) {
		            mybatisPlus.setPlugins(this.interceptors);
		        }
		        // MP 全局配置，更多内容进入类看注释
		        GlobalConfiguration globalConfig = new GlobalConfiguration();
		        //配置公共字段自动填写
		        //globalConfig.setMetaObjectHandler(myMetaObjectHandler);
		        globalConfig.setDbType(DBType.MYSQL.name());
		        // ID 策略 AUTO->`0`("数据库ID自增") INPUT->`1`(用户输入ID") ID_WORKER->`2`("全局唯一ID") UUID->`3`("全局唯一ID")
		        globalConfig.setIdType(1);
		        mybatisPlus.setGlobalConfig(globalConfig);
		        MybatisConfiguration mc = new MybatisConfiguration();
		        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		        mybatisPlus.setConfiguration(mc);
		        if (this.databaseIdProvider != null) {
		            mybatisPlus.setDatabaseIdProvider(this.databaseIdProvider);
		        }
		        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
		            mybatisPlus.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		        }
		        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
		            mybatisPlus.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		        }
		        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
		            mybatisPlus.setMapperLocations(this.properties.resolveMapperLocations());
		        }
		        return mybatisPlus;
		    }
		}


Blacklist对应的xml文件

		<?xml version="1.0" encoding="UTF-8" ?>
		<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
		<mapper namespace="org.com.spring.boot.dao.BlacklistMapper" >
		  <resultMap id="BaseResultMap" type="org.com.spring.boot.entity.Blacklist" >
		    <id column="ID" property="id" jdbcType="VARCHAR" />
		    <result column="HOST" property="host" jdbcType="VARCHAR" />
		    <result column="STATUS" property="status" jdbcType="BIT" />
		    <result column="USER_NAME" property="userName" jdbcType="VARCHAR" />
		    <result column="crt_time" jdbcType="DATE" property="crtTime" />
		    <result column="crt_user" jdbcType="VARCHAR" property="crtUser" />
		    <result column="crt_name" jdbcType="VARCHAR" property="crtName" />
		    <result column="crt_host" jdbcType="VARCHAR" property="crtHost" />
		    <result column="upd_time" jdbcType="DATE" property="updTime" />
		    <result column="upd_user" jdbcType="VARCHAR" property="updUser" />
		    <result column="upd_name" jdbcType="VARCHAR" property="updName" />
		    <result column="upd_host" jdbcType="VARCHAR" property="updHost" />
		    <result column="DESCRIPTION" property="description" jdbcType="VARCHAR" />
		    <result column="TYPE" property="type" jdbcType="TINYINT" />
		    <result column="de_type" property="deType" jdbcType="TINYINT" />
		    <result column="platform" jdbcType="INTEGER" property="platform" />
		
		  </resultMap>
		
		</mapper>


返回统一的实体类ObjectRestResponse

		package org.com.spring.boot.msg;
		
		
		/**
		 *
		 * @param <T>
		 */
		public class ObjectRestResponse<T> {
		    /**
		     * boolean 状态
		     */
		    boolean rel;
		    /**
		     * 返回参数说明
		     */
		    String msg;
		    /**
		     * 返回对象
		     */
		    T result;
		
		    public boolean isRel() {
		        return rel;
		    }
		
		    public void setRel(boolean rel) {
		        this.rel = rel;
		    }
		
		    public String getMsg() {
		        return msg;
		    }
		
		    public void setMsg(String msg) {
		        this.msg = msg;
		    }
		
		    public T getResult() {
		        return result;
		    }
		
		    public void setResult(T result) {
		        this.result = result;
		    }
		
		    public ObjectRestResponse rel(boolean rel) {
		        this.setRel(rel);
		        return this;
		    }
		
		    public ObjectRestResponse msg(String msg) {
		        this.setMsg(msg);
		        return this;
		    }
		
		    public ObjectRestResponse result(T result) {
		        this.setResult(result);
		        return this;
		    }
		}

导入相关sql语句，放在项目目录下的\spring-boot-mybatis\base_blacklist.sql
启动项目然后访问 http://localhost:8081/index?id=9 查出相关数据
简单的spring boot集成myabtis完成。查看相关项目代码