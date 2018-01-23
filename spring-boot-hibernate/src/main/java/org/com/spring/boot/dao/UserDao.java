package org.com.spring.boot.dao;

import org.com.spring.boot.enitiy.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

/**
 * <ul>
 * <li>文件包名 : org.com.spring.boot.dao</li>
 * <li>创建时间 : 2018/1/10 11:38</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author jiaonanyue
 * @version 2.0.0
 */
@Repository
@Table(name="user")
public interface UserDao extends CrudRepository<User, Long > {

    /**
     *
     * @param id
     * @return
     */
    @Override
    User findOne(Long id);

    /**
     *
     * @param u
     * @return
     */
    @Override
    User save(User u);

    /**
     *
     * @param name
     * @return
     */
    @Query("select t from User t where t.userName=:name")
    User findUserByName(@Param("name") String name);

}
