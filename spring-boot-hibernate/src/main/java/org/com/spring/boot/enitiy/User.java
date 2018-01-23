package org.com.spring.boot.enitiy;



import javax.persistence.*;
import java.util.Date;

/**
 * <ul>
 * <li>文件包名 : org.com.spring.boot.enitiy</li>
 * <li>创建时间 : 2018/1/10 11:36</li>
 * <li>修改记录 : 无</li>
 * </ul>
 * 类说明：
 *
 * @author jiaonanyue
 * @version 2.0.0
 */
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String userName;

    private Date birthDay;

    private String sex;

    private String address;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", birthDay=" + birthDay +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
