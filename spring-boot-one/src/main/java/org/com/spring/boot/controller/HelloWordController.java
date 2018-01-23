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
