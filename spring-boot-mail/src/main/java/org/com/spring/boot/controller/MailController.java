package org.com.spring.boot.controller;

import org.com.spring.boot.mail.MailUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <ul>
 * <li>文件包名 : org.com.spring.boot.controller</li>
 * <li>创建时间 : 2018/1/11 13:58</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author jiaonanyue
 * @version 2.0.0
 */
@RestController
public class MailController {

    private final MailUtil mailUtil;

    public MailController(MailUtil mailUtil) {
        this.mailUtil = mailUtil;
    }

    @GetMapping("send")
    public String sendMail(){

        mailUtil.sendTxtMail();

        return "发送邮箱";
    }
}
