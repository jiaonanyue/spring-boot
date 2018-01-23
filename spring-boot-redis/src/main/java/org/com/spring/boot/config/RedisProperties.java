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
