package com.book.db;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class NBReplyDAO {
	private static NBReplyDAO dao = new NBReplyDAO();
	private DataSource dataSource;
	private final String CONNECTION_POOL_RESOURCE_NAME = "jdbc/project";
	private final String TABLE_NAME = "noticeReply";
	private int sizeOfPage = 5;
	
	public NBReplyDAO() {
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

	public static NBReplyDAO getNBReplyDAO() {
		return dao;
	}
	
	//DTO 정보 획득
	public NBReplyDTO getNBReplyDTO(int bno, int rno) { 
		NBReplyDTO dto = new NBReplyDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE bno=?,  rno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			pstmt.setInt(2, rno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setBno(rs.getInt("bno"));
				dto.setRno(rs.getInt("rno"));
				dto.setId(rs.getString("id"));
				dto.setComment(rs.getString("comment"));
				dto.setwTime(rs.getDate("wTime"));
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
	
	//댓글 번호 순서 확인 
	public int getRno(int bno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			conn = getConnection();
			sql = "SELECT COUNT(*) FROM "+TABLE_NAME+" WHERE bno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getInt(1)+1;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		return result;
	}
	
	//댓글 작성
	public void commentOK(NBReplyDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "INSERT INTO "+TABLE_NAME+"(bno, rno, id, comment) VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			pstmt.setInt(2, dto.getRno());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getComment());
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//댓글 목록 불러오기
	public ArrayList<NBReplyDTO> comListDAO(int bno){
		ArrayList<NBReplyDTO> list = new ArrayList<NBReplyDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE bno=? ORDER BY rno DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NBReplyDTO dto = new NBReplyDTO();
				dto.setBno(rs.getInt("bno"));
				dto.setRno(rs.getInt("rno"));
				dto.setId(rs.getString("id"));
				dto.setComment(rs.getString("comment"));
				dto.setwTime(rs.getDate("wTime"));
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

	//댓글 수정
	public void modifyCom(NBReplyDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET comment=?, wTime=NOW() WHERE bno=? AND rno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getComment());
			pstmt.setInt(2, dto.getBno());
			pstmt.setInt(3, dto.getRno());
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//댓글 삭제
	public void deleteCom(NBReplyDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "DELETE FROM "+TABLE_NAME+" WHERE bno=? AND rno=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getBno());
			pstmt.setInt(2, dto.getRno());
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
