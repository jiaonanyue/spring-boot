package org.com.spring.boot.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.com.spring.boot.dao.BlacklistMapper;
import org.com.spring.boot.entity.Blacklist;
import org.com.spring.boot.service.IBlacklistServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * 黑名单名单
 * @author
 * @create 2017-09-05 16:22
 **/
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements IBlacklistServer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private  final BlacklistMapper blacklistMapper;

    public BlacklistServiceImpl(BlacklistMapper blacklistMapper) {
        this.blacklistMapper = blacklistMapper;
    }


    @Override
    public Blacklist getBlacklist(Long id) {

        logger.info("进入查询语句");
        Blacklist blacklist = blacklistMapper.getModel(id);

        return blacklist;
    }

}
