package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "voice")
public class Voice extends EntityBean{

	 @Column(name = "id", isPK = true)
	 public int id;
	 
	 @Column(name = "media_id")
	 public String media_id;
	 
	 @Column(name = "description")
	 public String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


	 
}
