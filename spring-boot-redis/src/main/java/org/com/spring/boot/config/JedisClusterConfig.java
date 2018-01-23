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
