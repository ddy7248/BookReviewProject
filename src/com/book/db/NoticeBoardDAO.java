package com.book.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class NoticeBoardDAO {
	private static NoticeBoardDAO dao = new NoticeBoardDAO();
	private DataSource dataSource;
	private final String CONNECTION_POOL_RESOURCE_NAME = "jdbc/project";
	private final String TABLE_NAME = "noticeBoard";
	private int sizeOfPage = 5;
	
	public NoticeBoardDAO() {
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/"+CONNECTION_POOL_RESOURCE_NAME);
		}
		catch(NamingException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
			if(conn != null) conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static NoticeBoardDAO getNoticeBoardDAO() {
		return dao;
	}
	
	//DTO 정보 획득
	public NoticeBoardDTO getNoticeBoardDTO(int no) {
		NoticeBoardDTO dto = new NoticeBoardDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setNo(rs.getInt("no"));
				dto.setType(rs.getString("type"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setwTime(rs.getDate("wTime"));
				dto.setrCnt(rs.getInt("rCnt"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setStepNum(rs.getInt("stepNum"));
				dto.setIndentNum(rs.getInt("indentNum"));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		return dto;
	}

	//총 페이지 수 획득
	public int getPageNum(String query) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int contentsNum = 0;
		int pageNum = 0;
		int result = 0;
		
		try {
			conn = getConnection();
			sql = "SELECT COUNT(*) FROM "+TABLE_NAME+" "+query;
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) contentsNum = rs.getInt(1);
			
			result = contentsNum/sizeOfPage;
			pageNum = contentsNum%sizeOfPage == 0 ? result : result+1;
		}
		catch(SQLException e ) {
			e.printStackTrace();
		}
		return pageNum;
	}
	
	//공지사항 목록
	public ArrayList<NoticeBoardDTO> listDAO(int curPage){
		ArrayList<NoticeBoardDTO> list = new ArrayList<NoticeBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sizeOfPage*(curPage-1));
			pstmt.setInt(2, sizeOfPage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeBoardDTO dto = new NoticeBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setType(rs.getString("type"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setwTime(rs.getDate("wTime"));
				dto.setrCnt(rs.getInt("rCnt"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setStepNum(rs.getInt("stepNum"));
				dto.setIndentNum(rs.getInt("indentNum"));
				list.add(dto);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//검색결과
	public ArrayList<NoticeBoardDTO> searchDAO(String searchType, String keyword ,int curPage){
		ArrayList<NoticeBoardDTO> list = new ArrayList<NoticeBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			if(searchType.equals("all")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE MATCH(type, title, contents) AGAINST(?) ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			else if(searchType.equals("type")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE type LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			else if(searchType.equals("title")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE title LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			else if(searchType.equals("contents")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE contents LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeBoardDTO dto = new NoticeBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setType(rs.getString("type"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setwTime(rs.getDate("wTime"));
				dto.setrCnt(rs.getInt("rCnt"));
				dto.setGroupNum(rs.getInt("groupNum"));
				dto.setStepNum(rs.getInt("stepNum"));
				dto.setIndentNum(rs.getInt("indentNum"));
				list.add(dto);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		return list;
	}
	
	//현재 no 확인
	public int getCurrentNum(NoticeBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int curNum = 0;
		
		try {
			conn = getConnection();
			sql = "SELECT MAX(no) FROM "+TABLE_NAME+" WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			rs = pstmt.executeQuery();
			
			if(rs.next()) curNum = rs.getInt(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		return curNum;
	}
	
	//groupNum 갱신
	public void updateGroupNum(int curNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET groupNum=? WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, curNum);
			pstmt.setInt(2, curNum);
			pstmt.executeUpdate();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//공지사항 등록
	public void writeOK(NoticeBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int curNum = 0;
		
		try {
			conn = getConnection();
			sql = "INSERT INTO "+TABLE_NAME+"(id, type, title, contents) VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getType());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContents());
			pstmt.executeUpdate();
			
			curNum = getCurrentNum(dto);
			updateGroupNum(curNum);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

	//조회수 상승
	public void increaseRCnt(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET rCnt=rCnt+1 WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

	//공지사항 수정
	public void modifyOK(NoticeBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET type=?, title=?, contents=?,wTime=NOW() WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getType());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContents());
			pstmt.setInt(4, dto.getNo());
			
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//공지사항 삭제
	public void deleteOK(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "DELETE FROM "+TABLE_NAME+" WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

}
