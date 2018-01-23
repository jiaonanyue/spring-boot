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
