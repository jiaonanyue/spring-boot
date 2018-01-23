package org.com.spring.boot.entity;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity 公共的entity实体
 * @author jiaonanyue
 * @create 2017-08-02 16:17
 **/
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 表id
     */
    private Long id;

    /**
     * 创建时间
     */
    @TableField("crt_time")
    private Date crtTime;

    /**
     * 创建者
     */
    @TableField("crt_user")
    private String crtUser;

    /**
     * 创建者名称
     */
    @TableField("crt_name")
    private String crtName;

    /**
     * 创建者IP
     */
    @TableField("crt_host")
    private String crtHost;

    /**
     * 修改时间
     */
    @TableField("upd_time")
    private Date updTime;

    /**
     * 修改者
     */
    @TableField("upd_user")
    private String updUser;

    /**
     * 修改者名称
     */
    @TableField("upd_name")
    private String updName;

    /**
     * 修改者ip
     */
    @TableField("upd_host")
    private String updHost;

    /**
     * 逻辑删除字段
     * 1：已经删除 0：未删除
     */
    @TableField("de_type")
    private Short deType;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser;
    }

    public String getCrtName() {
        return crtName;
    }

    public void setCrtName(String crtName) {
        this.crtName = crtName;
    }

    public String getCrtHost() {
        return crtHost;
    }

    public void setCrtHost(String crtHost) {
        this.crtHost = crtHost;
    }

    public Date getUpdTime() {
        return updTime;
    }

    public void setUpdTime(Date updTime) {
        this.updTime = updTime;
    }

    public String getUpdUser() {
        return updUser;
    }

    public void setUpdUser(String updUser) {
        this.updUser = updUser;
    }

    public String getUpdName() {
        return updName;
    }

    public void setUpdName(String updName) {
        this.updName = updName;
    }

    public String getUpdHost() {
        return updHost;
    }

    public void setUpdHost(String updHost) {
        this.updHost = updHost;
    }

    public Short getDeType() {
        return deType;
    }

    public void setDeType(Short deType) {
        this.deType = deType;
    }


    @Override
    public String toString() {
        return "BaseEntity{" +
                "id='" + id + '\'' +
                ", crtTime=" + crtTime +
                ", crtUser='" + crtUser + '\'' +
                ", crtName='" + crtName + '\'' +
                ", crtHost='" + crtHost + '\'' +
                ", updTime=" + updTime +
                ", updUser='" + updUser + '\'' +
                ", updName='" + updName + '\'' +
                ", updHost='" + updHost + '\'' +
                ", deType=" + deType +
                '}';
    }
}
