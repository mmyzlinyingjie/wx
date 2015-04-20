package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

/**
 * 报名实体
 */
@Entity(table = "sign_up")
public class Demo extends EntityBean {

    @Column(name = "uid", isPK = true)
    public Long uid;

    @Column(name = "yyno")
    private Long yyno;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "resname")
    private String resname;

    @Column(name = "image")
    private String image;

    @Column(name = "video")
    private String video;

    @Column(name = "state")
    private String state;

    @Column(name = "resid")
    private long resid;

    @Column(name = "create_time")
    private String create_time;


    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getYyno() {
        return yyno;
    }

    public void setYyno(Long yyno) {
        this.yyno = yyno;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setResname(String resName) {
        this.resname = resName;
    }

    public String getResname() {
        return resname;
    }


}