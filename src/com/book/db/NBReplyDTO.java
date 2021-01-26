package com.book.db;

import java.util.Date;

public class NBReplyDTO {
	private int bno;
	private int rno;
	private String id;
	private String comment;
	private Date wTime;
	
	public NBReplyDTO() {
		super();
	}
	
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getwTime() {
		return wTime;
	}
	public void setwTime(Date wTime) {
		this.wTime = wTime;
	}
	
	
}
