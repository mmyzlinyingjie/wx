package com.yy.ent.wx.common.model;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "router")
public class Router extends EntityBean {

	@Column(name = "id", isPK = true)
	public int id;

	@Column(name = "intercept")
	public String intercept;

	@Column(name = "type")
	public int type;

	@Column(name = "type_id")
	public int type_id;

	@Column(name = "sortord")
	public int sortord;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIntercept() {
		return intercept;
	}

	public void setIntercept(String intercept) {
		this.intercept = intercept;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public int getSortord() {
		return sortord;
	}

	public void setSortord(int sortord) {
		this.sortord = sortord;
	}

	public String toString() {
		return "id:" + id + "\nintercept:" + intercept + "\ntype:"
				+ type + "\ntype_id:" + type_id + "\nsort_id:" + sortord;
	}
}
