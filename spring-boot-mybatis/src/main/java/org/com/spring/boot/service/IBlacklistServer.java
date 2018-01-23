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
