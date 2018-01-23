package org.com.spring.boot.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;


/**
 * 黑名单表
 */
@TableName("base_blacklist")
public class Blacklist extends BaseEntity {


    /**
     * I
     * ip地址
     */
    private String host;

    /**
     * 状态 0：停用 1：使用
     */
    private Boolean status;

    /**
     * 黑名单账户
     */
    @TableField("user_name")
    private String userName;


    /**
     * 说明
     */
    private String description;

    /**
     *
     */
    private Byte type ;



    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "host='" + host + '\'' +
                ", status=" + status +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}