package com.yy.ent.wx.common.model;

import java.util.Date;

import com.yy.ent.cherroot.entity.EntityBean;
import com.yy.ent.cherroot.entity.annotation.Column;
import com.yy.ent.cherroot.entity.annotation.Entity;

@Entity(table = "mass_article")
public class MassNews extends EntityBean {

	@Column(name = "id", isPK = true)
	public int id;

	@Column(name = "title")
	public String title;

	@Column(name = "content")
	public String content;

	@Column(name = "thumb_media_id")
	public String thumbMediaId;

	@Column(name = "author")
	public String author;

	@Column(name = "content_source_url")
	public String contentSourceUrl;

	@Column(name = "digest")
	public String digest;

	@Column(name = "show_cover_pic")
	public boolean showCoverPic;

	@Column(name = "type")
	public int type;

	@Column(name = "sortord")
	public int sortord;

	@Column(name = "createTime")
	public Date createTime;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContentSourceUrl() {
		return contentSourceUrl;
	}

	public void setContentSourceUrl(String contentSourceUrl) {
		this.contentSourceUrl = contentSourceUrl;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public boolean isShowCoverPic() {
		return showCoverPic;
	}

	public void setShowCoverPic(boolean showCoverPic) {
		this.showCoverPic = showCoverPic;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
