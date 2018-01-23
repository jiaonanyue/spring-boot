package org.com.spring.boot.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <ul>
 * <li>文件包名 : org.com.spring.boot.controller</li>
 * <li>创建时间 : 2018/1/11 10:24</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author jiaonanyue
 * @version 2.0.0
 */
@RestController
public class UserController {


    @PostMapping("add")
    public String add(String name){

        return "添加成功";
    }

    @DeleteMapping("delete")
    public String delete(long id){

        return "删除成功";
    }

    @PostMapping("update")
    public String update(){

        return "更新成功";
    }

    @GetMapping("get")
    public String get(){

        return "查询成功";
    }
}
