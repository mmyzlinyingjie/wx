package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "wx1931")
public class Wx1931 extends EntityBean{

	 @Column(name = "id", isPK = true)
	 public int id;
	 
	 @Column(name = "title")
	 public String title;
	 
	 @Column(name = "description")
	 public String description;
	 
	 @Column(name = "type")
	 public int type;
	 
	 @Column(name = "sortord")
	 public int sortord;
	 
	 @Column(name = "url")
	 public String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSortord() {
		return sortord;
	}

	public void setSortord(int sortord) {
		this.sortord = sortord;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	 
	 
}
