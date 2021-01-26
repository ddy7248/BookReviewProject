package com.book.db;

import java.sql.*;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO {

	private static MemberDAO dao = new MemberDAO();
	private DataSource dataSource;
	private final String CONNECTION_POOL_RESOURCE_NAME = "jdbc/project";
	private final String TABLE_NAME = "bookMember";
	
	public MemberDAO() {
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
	
	public static MemberDAO getMemberDAO() {
		return dao;
	}
	
	//회원 목록
	public ArrayList<MemberDTO> listDAO(){
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE id != ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "admin");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setPhoneNum(rs.getString("phoneNum"));
				
				dto.setJoinDate(rs.getDate("joinDate"));
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
	
	//회원 객체 정보 획득
	public MemberDTO getMemberDTO(String id) {
		MemberDTO dto = new MemberDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPassword(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setBirth(rs.getString("birth"));
				dto.setEmail(rs.getString("email"));
				dto.setPhoneNum(rs.getString("phoneNum"));
				dto.setJoinDate(rs.getDate("joinDate"));
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

	//중복 확인
	public int checkID(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			conn = getConnection();
			sql = "SELECT COUNT(*) FROM "+TABLE_NAME+" WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getInt(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		return result;
	}
	
	//회원 등록
	public void registerMember(MemberDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;
		
		try {
			conn = getConnection();
			sql = "INSERT INTO "+TABLE_NAME+"(id, password, name, gender, birth, email, phoneNum) VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId() );
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getGender());
			pstmt.setString(5, dto.getBirth());
			pstmt.setString(6, dto.getEmail());
			pstmt.setString(7, dto.getPhoneNum());
			result = pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//회원 로그인
	public boolean loginOK(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=? AND password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) flag = true;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		return flag;
	}

	//회원 수정
	public void modify(MemberDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET password=?, name=?, gender=?, birth=?, email=?, phoneNum=? WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPassword());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getGender());
			pstmt.setString(4, dto.getBirth());
			pstmt.setString(5, dto.getEmail());
			pstmt.setString(6, dto.getPhoneNum());
			pstmt.setString(7, dto.getId());
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//회원 삭제
	public void delete(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "DELETE FROM "+TABLE_NAME+" WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
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
