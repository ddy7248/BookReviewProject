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


public class ReviewBoardDAO {
	private static ReviewBoardDAO dao = new ReviewBoardDAO();
	private DataSource dataSource;
	private final String CONNECTION_POOL_RESOURCE_NAME = "jdbc/project";
	private final String TABLE_NAME = "reviewBoard";
	private int sizeOfPage = 5;
	
	public ReviewBoardDAO() {
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

	public static ReviewBoardDAO getReviewBoardDAO() {
		return dao;
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
	
	//DTO 정보 획득
	public ReviewBoardDTO getReviewBoardDTO(int no) {
		ReviewBoardDTO dto = new ReviewBoardDTO();
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
				dto.setId(rs.getString("id"));
				dto.setImgURL(rs.getString("imgURL"));
				dto.setBookTitle(rs.getString("bookTitle"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setStarPoint(rs.getInt("starPoint"));
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
	
	//댓글 수 구하기
	public int getMaxStepNum(int groupNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int result = 0;
		
		try {
			conn = getConnection();
			sql = "SELECT COUNT(stepNum) FROM "+TABLE_NAME+" WHERE groupNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, groupNum);
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
	
	//리스트
	public ArrayList<ReviewBoardDTO> listDAO(int curPage){
		ArrayList<ReviewBoardDTO> list = new ArrayList<ReviewBoardDTO>();
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
				ReviewBoardDTO dto = new ReviewBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setImgURL(rs.getString("imgURL"));
				dto.setBookTitle(rs.getString("bookTitle"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setStarPoint(rs.getInt("starPoint"));
				dto.setContents(rs.getString("contents"));
				dto.setwTime(rs.getDate("wTime"));
				dto.setrCnt(rs.getInt("rCnt"));
				dto.setGroupNum(rs.getInt("groupNum"));
				int stepNum = getMaxStepNum(rs.getInt("groupNum"));
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
	public ArrayList<ReviewBoardDTO> searchDAO(String searchType, String keyword ,int curPage){
		ArrayList<ReviewBoardDTO> list = new ArrayList<ReviewBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			if(searchType.equals("all")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND MATCH(id,bookTitle,author,publisher) AGAINST(?) ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, keyword);
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			else if(searchType.equals("bookTitle")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND bookTitle LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			else if(searchType.equals("author")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND author LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			else if(searchType.equals("publisher")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND publisher LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			else if(searchType.equals("id")) {
				sql = "SELECT * FROM "+TABLE_NAME+" WHERE indentNum=0 AND id LIKE ? ORDER BY groupNum DESC, stepNum ASC LIMIT ?,?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setInt(2, sizeOfPage*(curPage-1));
				pstmt.setInt(3, sizeOfPage);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewBoardDTO dto = getReviewBoardDTO(rs.getInt("no"));
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
	
	//내 게시글 확인
	public ArrayList<ReviewBoardDTO> checkPost(String id, int curPage){
		ArrayList<ReviewBoardDTO> list = new ArrayList<ReviewBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE id=? AND indentNum=0 ORDER BY wTime DESC LIMIT ?,?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, sizeOfPage*(curPage-1));
			pstmt.setInt(3, sizeOfPage);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewBoardDTO dto = new ReviewBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setImgURL(rs.getString("imgURL"));
				dto.setBookTitle(rs.getString("bookTitle"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setStarPoint(rs.getInt("starPoint"));
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
	public int getCurrentNum(ReviewBoardDTO dto) {
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
	
	//서평 작성
	public void writeReview(ReviewBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int curNum = 0;
		
		try {
			conn = getConnection();
			sql = "INSERT INTO "+TABLE_NAME+"(id, imgURL,  bookTitle, author, publisher, starPoint, contents) VALUES(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getImgURL());
			pstmt.setString(3, dto.getBookTitle());
			pstmt.setString(4, dto.getAuthor());
			pstmt.setString(5, dto.getPublisher());
			pstmt.setInt(6, dto.getStarPoint());
			pstmt.setString(7, dto.getContents());
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

	//서평 수정
	public void modifyReview(ReviewBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET bookTitle=?, author=?, starPoint=?, contents=?,wTime=NOW() WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getBookTitle());
			pstmt.setString(2, dto.getAuthor());
			pstmt.setInt(3, dto.getStarPoint());
			pstmt.setString(4, dto.getContents());
			pstmt.setInt(5, dto.getNo());
			
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}
	
	//서평 삭제
	public void deleteReview(int no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "DELETE FROM "+TABLE_NAME+" WHERE no=? OR groupNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			pstmt.setInt(2, no);
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			close(null, pstmt, conn);
		}
	}

	//stepNum 증가
	public void updateStepNum(ReviewBoardDTO dto) {
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
	public void commentOK(ReviewBoardDTO dto) {
		updateStepNum(dto);
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "INSERT INTO "+TABLE_NAME+"(id, imgURL, bookTitle, author, publisher, starPoint, contents, groupNum, stepNum, indentNum) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getImgURL());
			pstmt.setString(3, dto.getBookTitle());
			pstmt.setString(4, dto.getAuthor());
			pstmt.setString(5, dto.getPublisher());
			pstmt.setInt(6, dto.getStarPoint());
			pstmt.setString(7, dto.getContents());
			pstmt.setInt(8, dto.getGroupNum());
			pstmt.setInt(9, dto.getStepNum()+1);
			pstmt.setInt(10, dto.getIndentNum()+1);
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
	public ArrayList<ReviewBoardDTO> comListDAO(int no){
		ArrayList<ReviewBoardDTO> list = new ArrayList<ReviewBoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "SELECT * FROM "+TABLE_NAME+" WHERE groupNum=? AND indentNum>0 ORDER BY stepNum";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewBoardDTO dto = new ReviewBoardDTO();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setImgURL(rs.getString("imgURL"));
				dto.setBookTitle(rs.getString("bookTitle"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setStarPoint(rs.getInt("starPoint"));
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
	public void modifyCom(ReviewBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET contents=? WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getContents());
			pstmt.setInt(2, dto.getNo());
			
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
	public void decreaseStepNum(ReviewBoardDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = getConnection();
			sql = "UPDATE "+TABLE_NAME+" SET stepNum=stepNum-1 WHERE groupNum=? AND stepNum>=?";
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
	
	//댓글 삭제
	public void deleteCom(int no) {
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
