package com.yy.ent.wx.common.model;

import java.util.Date;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "local_media")
public class LocalMedia extends EntityBean{

	 @Column(name = "id", isPK = true)
	 public int id;
	 
	 @Column(name = "md5")
	 public String md5;

	 @Column(name = "type")
	 public int type;
	 
	 @Column(name = "createTime")
	 public Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
