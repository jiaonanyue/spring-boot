**SpringBoot集成Redis集群**

前提条件是安装了redis集群。此处安装省略。

创建spring-boot-redis项目，引入jar

		<dependency>
			<groupId>redis.clients</groupId>
			<artifactId>jedis</artifactId>
			<version>2.9.0</version>
		</dependency>

pom.xml文件

		<?xml version="1.0" encoding="UTF-8"?>
		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
			<modelVersion>4.0.0</modelVersion>
		
			<groupId>org.com.spring.boot</groupId>
			<artifactId>spring-boot-redis</artifactId>
			<version>0.0.1-SNAPSHOT</version>
			<packaging>jar</packaging>
		
			<name>spring-boot-redis</name>
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
					<groupId>redis.clients</groupId>
					<artifactId>jedis</artifactId>
					<version>2.9.0</version>
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

springBoot集成redis集群参数配置application.properties

		#redis集群配置
		platform.redis.pool.nodes=192.168.101.111:6379,192.168.101.112:6379
		platform.redis.pool.timeout=3000
		platform.redis.pool.maxAttempts=5


创建config、redis、controller包

config包下创建RedisProperties类，集群参数配置信息引入类

		package org.com.spring.boot.config;
		
		import org.springframework.boot.context.properties.ConfigurationProperties;
		import org.springframework.context.annotation.PropertySource;
		import org.springframework.stereotype.Component;
		
		/**
		 * <ul>
		 * <li>文件包名 : com.platform.provider.provider.redis</li>
		 * <li>创建时间 : 2017/11/17 10:37</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@Component
		@ConfigurationProperties(prefix = "platform.redis.pool")
		public class RedisProperties {
		
		    /**
		     * redis 集群节点
		     */
		    private String nodes;
		
		    /**
		     *
		     * 连接超时时间
		     */
		    private int timeout;
		
		    /**
		     * 连接重试次数
		     */
		    private int maxAttempts;
		
		
		    public String getNodes() {
		        return nodes;
		    }
		
		    public void setNodes(String nodes) {
		        this.nodes = nodes;
		    }
		
		    public int getTimeout() {
		        return timeout;
		    }
		
		    public void setTimeout(int timeout) {
		        this.timeout = timeout;
		    }
		
		    public int getMaxAttempts() {
		        return maxAttempts;
		    }
		
		    public void setMaxAttempts(int maxAttempts) {
		        this.maxAttempts = maxAttempts;
		    }
		
		    @Override
		    public String toString() {
		        return "RedisProperties{" +
		                "nodes='" + nodes + '\'' +
		                ", timeout=" + timeout +
		                ", maxAttempts=" + maxAttempts +
		                '}';
		    }
		}

config包下创建JedisClusterConfig（集群注入类）

		package org.com.spring.boot.config;
		
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import redis.clients.jedis.HostAndPort;
		import redis.clients.jedis.JedisCluster;
		
		import java.util.HashSet;
		import java.util.Set;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.config</li>
		 * <li>创建时间 : 2018/1/10 16:06</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@Configuration
		public class JedisClusterConfig {
		
		    private final RedisProperties redisProperties;
		
		
		    public JedisClusterConfig(RedisProperties redisProperties) {
		        this.redisProperties = redisProperties;
		    }
		
		    /**
		     * 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
		     * @return
		     */
		    @Bean
		    public JedisCluster getJedisCluster(){
		        String[] serverArray = redisProperties.getNodes().split(",");
		        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		        for(String ipPost:serverArray){
		            String[] ipPortPair = ipPost.split(":");
		            nodes.add(new HostAndPort(ipPortPair[0].trim(),Integer.valueOf(ipPortPair[1].trim())));
		        }
		
		        return new JedisCluster(nodes,redisProperties.getTimeout(),redisProperties.getMaxAttempts());
		    }
		}


在redis包下创建一些通用的redis操作类 RedisTemplateUtil


		package org.com.spring.boot.redis;
		
		import org.com.spring.boot.config.RedisProperties;
		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.stereotype.Component;
		import redis.clients.jedis.JedisCluster;
		
		
		/**
		 * <ul>
		 * <li>文件包名 : com.platform.provider.provider.config.redis</li>
		 * <li>创建时间 : 2017/11/17 15:08</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@Component
		public class RedisTemplateUtil {
		
		    private static final Logger LOGGER = LoggerFactory.getLogger(RedisTemplateUtil.class);
		
		
		    private final JedisCluster jedisCluster;
		
		    private final RedisProperties redisProperties;
		
		    private static final String KEY_SPLIT = ":";
		
		
		    public RedisTemplateUtil(JedisCluster jedisCluster, RedisProperties redisProperties) {
		        this.jedisCluster = jedisCluster;
		        this.redisProperties = redisProperties;
		    }
		
		    /**
		     *  缓存
		     * @param prefix 前缀
		     * @param key key值
		     * @param value value值
		     */
		    public void set(String prefix,String key ,String value){
		
		        jedisCluster.set(prefix + KEY_SPLIT + key,value);
		        LOGGER.debug("RedisUtil:set cache key={},value={}",prefix+KEY_SPLIT+key,value);
		
		    }
		
		    /**
		     *  缓存
		     * @param prefix
		     * @param key
		     * @param value
		     * @param time 过期时间
		     */
		    public void setWithExpireTime(String prefix,String key, String value, int time){
		
		        jedisCluster.setex(prefix + KEY_SPLIT + key,time,value);
		        LOGGER.debug("RedisUtil:setex cache key={},value={},time={}",prefix+KEY_SPLIT+key,value,time);
		
		
		    }
		
		    /**
		     *  配置文件中配置过期时间
		     * @param prefix
		     * @param key
		     * @param value
		     */
		    public void setWithExpireTime(String prefix,String key,String value){
		        int time = redisProperties.getTimeout();
		        jedisCluster.setex(prefix + KEY_SPLIT + key,time,value);
		        LOGGER.debug("RedisUtil:setex cache key={},value={},time={}",prefix+KEY_SPLIT+key,value,time);
		    }
		
		
		    /**
		     * 查询
		     * @param prefix
		     * @param key
		     * @return
		     */
		    public String get(String prefix,String key){
		
		        return jedisCluster.get(prefix +KEY_SPLIT + key);
		    }
		
		    /**
		     * 删除
		     * @param prefix
		     * @param key
		     */
		    public void deleteWithPrefix(String prefix,String key){
		        jedisCluster.del(prefix + KEY_SPLIT + key);
		    }
		
		    /**
		     * 删除
		     * @param key
		     */
		    public void delete(String key){
		
		        jedisCluster.del(key);
		    }
		}


在controller下创建测试类RedisController

		package org.com.spring.boot.controller;
		
		import org.com.spring.boot.redis.RedisTemplateUtil;
		import org.springframework.web.bind.annotation.GetMapping;
		import org.springframework.web.bind.annotation.RestController;
		
		/**
		 * <ul>
		 * <li>文件包名 : org.com.spring.boot.controller</li>
		 * <li>创建时间 : 2018/1/10 16:19</li>
		 * <li>修改记录 : 无</li>
		 * </ul>
		 * 类说明：
		 *
		 * @author jiaonanyue
		 * @version 2.0.0
		 */
		@RestController
		public class RedisController {
		
		    private final RedisTemplateUtil redisTemplateUtil;
		
		    public RedisController(RedisTemplateUtil redisTemplateUtil) {
		        this.redisTemplateUtil = redisTemplateUtil;
		    }
		
		    @GetMapping("add")
		    public void add(){
		        redisTemplateUtil.set("a","a","张三");
		
		    }
		
		    @GetMapping("get")
		    public String get(){
		        return redisTemplateUtil.get("a","a");
		    }
		}


启动项目SpringBootRedisApplication类。

访问地址为：http://localhost:8080/add 向redis集群中添加数据
访问地址为：http://localhost:8080/get 获取刚刚添加在redis集群的数据

spring boot 集成redis集群完成了。