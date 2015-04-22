package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "fans_idol")
public class FansIdol extends EntityBean{

	 @Column(name = "id", isPK = true)
	 public int id;
	 
	 @Column(name = "fans_id")
	 public String fans_id;

	 @Column(name = "idol_id")
	 public int idol_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFans_id() {
		return fans_id;
	}

	public void setFans_id(String fans_id) {
		this.fans_id = fans_id;
	}

	public int getIdol_id() {
		return idol_id;
	}

	public void setIdol_id(int idol_id) {
		this.idol_id = idol_id;
	}
}
