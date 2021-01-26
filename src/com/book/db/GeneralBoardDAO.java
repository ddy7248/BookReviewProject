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

public class GeneralBoardDAO {
	private static GeneralBoardDAO dao = new GeneralBoardDAO();
	private DataSource dataSource;
	private final String CONNECTION_POOL_RESOURCE_NAME = "jdbc/project";
	private final String TABLE_NAME = "generalBoard";
	private int sizeOfPage = 5;
	
	public GeneralBoardDAO() {
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

	public static GeneralBoardDAO getGeneralBoardDAO() {
		return dao;
	}
	
	//DTO 정보 획득
	public GeneralBoardDTO getGeneralBoardDTO(int no, String boardType) {
		GeneralBoardDTO dto = new GeneralBoardDTO();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE no=? AND boardType=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, boardType);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setNo(rs.getInt("no"));
				dto.setBoardType(rs.getString("boardType"));
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
	
	//현재 no 확인
	public int getCurrentNum(GeneralBoardDTO dto) {
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
	
	//게시글 작성
	public void writeOK(GeneralBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int curNum = 0;
		
		try {
			conn = getConnection();
			sql = "INSERT INTO "+TABLE_NAME+"(boardType, id, title, contents) VALUES(?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBoardType());
			pstmt.setString(2, dto.getId());
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
	
	//댓글 수 구하기
	public int getMaxStepNum(int groupNum, String boardType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			conn = getConnection();
			sql = "SELECT COUNT(stepNum) FROM "+TABLE_NAME+" WHERE groupNum=? AND boardType=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
			pstmt.setString(2, boardType);
			rs = pstmt.executeQuery();
			
			if(rs.next()) result = rs.getInt(1)-1;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(rs, pstmt, conn);
		}
		return result;
	}
	
	//게시판 목록
	public ArrayList<GeneralBoardDTO> listDAO(String boardType, int curPage, String id){
		ArrayList<GeneralBoardDTO> list = new ArrayList<GeneralBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			
			if(id != null) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=? AND boardType=? AND indentNum=0 ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, boardType);
				pstmt.setInt(3, sizeOfPage*(curPage-1));
				pstmt.setInt(4, sizeOfPage);
			}
			else {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE boardType=? AND indentNum=0 ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, boardType);
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GeneralBoardDTO dto = new GeneralBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setBoardType(rs.getString("boardType"));
				dto.setId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setContents(rs.getString("contents"));
				dto.setwTime(rs.getDate("wTime"));
				dto.setrCnt(rs.getInt("rCnt"));
				dto.setGroupNum(rs.getInt("groupNum"));
				int stepNum = getMaxStepNum(rs.getInt("groupNum"), rs.getString("boardType"));
				dto.setStepNum(stepNum);
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
	public ArrayList<GeneralBoardDTO> searchDAO(String boardType, String searchType, String keyword ,int curPage){
		ArrayList<GeneralBoardDTO> list = new ArrayList<GeneralBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			if(searchType.equals("all")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND boardType=? AND MATCH(id, title, contents) AGAINST(?) ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, boardType);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, sizeOfPage*(curPage-1));
				pstmt.setInt(4, sizeOfPage);
			}
			else if(searchType.equals("title")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND boardType=? AND title LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, boardType);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setInt(3, sizeOfPage*(curPage-1));
				pstmt.setInt(4, sizeOfPage);
			}
			else if(searchType.equals("contents")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND boardType=? AND contents LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, boardType);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setInt(3, sizeOfPage*(curPage-1));
				pstmt.setInt(4, sizeOfPage);
			}
			else if(searchType.equals("mixedTitleContents")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND boardType=? AND (title LIKE ? OR contents LIKE ?) ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, boardType);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
				pstmt.setInt(4, sizeOfPage*(curPage-1));
				pstmt.setInt(5, sizeOfPage);
			}
			else if(searchType.equals("id")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND boardType=? AND id LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, boardType);
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setInt(3, sizeOfPage*(curPage-1));
				pstmt.setInt(4, sizeOfPage);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GeneralBoardDTO dto = new GeneralBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setBoardType(rs.getString("boardType"));
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
	
	//조회수 상승
	public void increaseRCnt(int no, String boardType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET rCnt=rCnt+1 WHERE no=? AND boardType=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, boardType);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

	//게시글 수정
	public void modifyOK(GeneralBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET title=?, contents=?,wTime=NOW() WHERE no=? AND boardType=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContents());
			pstmt.setInt(3, dto.getNo());
			pstmt.setString(4, dto.getBoardType());
			
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

	//게시글 삭제
	public void deleteOK(int no, String boardType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "DELETE FROM "+TABLE_NAME+" WHERE (no=? AND boardType=?) OR groupNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, boardType);
			pstmt.setInt(3, no);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

	//stepNum 갱신
	public void updateStepNum(GeneralBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET stepNum=stepNum+1 WHERE groupNum=? AND stepNum>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getGroupNum());
			pstmt.setInt(2, dto.getStepNum()+1);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//댓글 작성
	public void commentOK(GeneralBoardDTO dto) {
		updateStepNum(dto);
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "INSERT INTO "+TABLE_NAME+"(boardType, id, title, contents, groupNum, stepNum, indentNum) VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBoardType());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContents());
			pstmt.setInt(5, dto.getGroupNum());
			pstmt.setInt(6, dto.getStepNum()+1);
			pstmt.setInt(7, dto.getIndentNum()+1);
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
	public ArrayList<GeneralBoardDTO> comListDAO(int no, String boardType){
		ArrayList<GeneralBoardDTO> list = new ArrayList<GeneralBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE groupNum=? AND boardType=? AND indentNum>0 ORDER BY stepNum";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, boardType);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				GeneralBoardDTO dto = new GeneralBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setBoardType(rs.getString("boardType"));
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

	//댓글 수정
	public void modifyCom(GeneralBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET contents=? WHERE no=? AND boardType=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContents());
			pstmt.setInt(2, dto.getNo());
			pstmt.setString(3, dto.getBoardType());
			
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

	//stepNum 감소
	public void decreaseStepNum(GeneralBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET stepNum=stepNum-1 WHERE boardType=? AND groupNum=? AND stepNum>=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBoardType());
			pstmt.setInt(2, dto.getGroupNum());
			pstmt.setInt(3, dto.getStepNum()+1);
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
	public void deleteCom(int no, String boardType) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "DELETE FROM "+TABLE_NAME+" WHERE no=? AND boardType=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setString(2, boardType);
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
