package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "fans")
public class Fans extends EntityBean {

	@Column(name = "fans_id", isPK = true)
	public String FansId;

	public String getFansId() {
		return FansId;
	}

	public void setFansId(String fansId) {
		FansId = fansId;
	}



}
