package org.com.spring.boot.controller;

import org.com.spring.boot.entity.Blacklist;
import org.com.spring.boot.service.IBlacklistServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 黑名单操作类Controller
 * @author
 * @create 2017-09-05 16:45
 **/
@RestController
public class BlacklistController extends BaseController<IBlacklistServer,Blacklist> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IBlacklistServer blacklistServer;



    public BlacklistController(IBlacklistServer blacklistServer) {
        this.blacklistServer = blacklistServer;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/index")
    public Blacklist getBlacklist(String id){

        if(StringUtils.isEmpty(id)){
            return new Blacklist();
        }
        long start = System.currentTimeMillis();

        Blacklist blacklist = blacklistServer.getBlacklist(Long.valueOf(id));
        long end = System.currentTimeMillis();

        logger.info("总时间为：==="+(end-start));
        return blacklist ;
    }

}
