package org.com.spring.boot.controller;

import org.com.spring.boot.dao.UserDao;
import org.com.spring.boot.enitiy.User;
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
