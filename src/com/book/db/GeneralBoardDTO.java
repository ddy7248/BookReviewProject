package com.book.db;

import java.util.Date;

public class GeneralBoardDTO {
	private int no;
	private String boardType;
	private String id;
	private String title;
	private String contents;
	private Date wTime;
	private int rCnt;
	private int groupNum;
	private int stepNum;
	private int indentNum;
	
	public GeneralBoardDTO() {
		super();
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
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
	
	
	
}
