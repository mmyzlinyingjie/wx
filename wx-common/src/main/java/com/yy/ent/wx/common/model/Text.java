package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "text")
public class Text extends EntityBean{

	 @Column(name = "id", isPK = true)
	 public int id;
	 
	 @Column(name = "content")
	 public String content;

	 @Column(name = "description")
	 public String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	 
	 
}
