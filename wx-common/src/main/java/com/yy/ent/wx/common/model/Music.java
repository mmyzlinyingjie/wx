package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "music")
public class Music extends EntityBean{

	 @Column(name = "id", isPK = true)
	 public int id;
	 
	 @Column(name = "title")
	 public String title;
	 
	 @Column(name = "desc")
	 public String desc;
	 
	 @Column(name = "hqMusicUrl")
	 public String hqMusicUrl;
	 
	 @Column(name = "musicUrl")
	 public String musicUrl;
	 
	 @Column(name = "thumbMediaId")
	 public int thumbMediaId;
	 
}
