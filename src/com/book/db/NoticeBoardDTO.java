package com.book.db;

import java.util.Date;

public class NoticeBoardDTO {
	private int no;
	private String type;
	private String id;
	private String title;
	private String contents;
	private Date wTime;
	private int rCnt;
	private int groupNum;
	private int stepNum;
	private int indentNum;
	
	public NoticeBoardDTO() {
		super();
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getwTime() {
		return wTime;
	}

	public void setwTime(Date wTime) {
		this.wTime = wTime;
	}

	public int getrCnt() {
		return rCnt;
	}

	public void setrCnt(int rCnt) {
		this.rCnt = rCnt;
	}

	public int getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(int groupNum) {
		this.groupNum = groupNum;
	}

	public int getStepNum() {
		return stepNum;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

	public int getIndentNum() {
		return indentNum;
	}

	public void setIndentNum(int indentNum) {
		this.indentNum = indentNum;
	}

	public NoticeBoardDTO(int no, String type, String id, String title, String contents, Date wTime, int rCnt,
			int groupNum, int stepNum, int indentNum) {
		super();
		this.no = no;
		this.type = type;
		this.id = id;
		this.title = title;
		this.contents = contents;
		this.wTime = wTime;
		this.rCnt = rCnt;
		this.groupNum = groupNum;
		this.stepNum = stepNum;
		this.indentNum = indentNum;
	}
	
	
}
