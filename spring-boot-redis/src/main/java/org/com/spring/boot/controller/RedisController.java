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
