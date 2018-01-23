package org.com.spring.boot.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.com.spring.boot.entity.Blacklist;

public interface BlacklistMapper extends BaseMapper<Blacklist> {

    @Select("select b.id,b.platform,b.description,b.host from base_blacklist b where id = #{id}")
    Blacklist getModel(Long id);

}