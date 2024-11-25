package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnection;
import VO.GameVO;

public class GameDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public GameDAO() throws ClassNotFoundException, SQLException {
		con = new DBConnection().getConnection();
	}
	
	public boolean insert_score1(String id, int score) { // Math Quiz 게임 점수 추가
		String sql = "INSERT INTO member_score(id, score1) VALUES(?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setInt(2, score);
			
			pstmt.executeUpdate();
			
			System.out.println("INSERT SUCCESS!");
		} catch(SQLException e) {
			System.out.println("INSERT ERROR!");
			System.out.println(e);
			
			return false;
		}
		
		return true;
	}
	
	public boolean insert_score2(String id, int score) { // Hangman 게임 점수 추가
		String sql = "INSERT INTO member_score(id, score2) VALUES(?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, id);
			pstmt.setInt(2, score);
			
			pstmt.executeUpdate();
			
			System.out.println("INSERT SUCCESS!");
		} catch(Exception e) {
			System.out.println("INSERT ERROR!");
			
			return false;
		}
		
		return true;
	}
	
	public ArrayList<GameVO> getSumScore() throws SQLException { // 평균 게임 점수(ArrayList 형태로 값 저장)
		ArrayList<GameVO> list = new ArrayList<GameVO>();
		
		StringBuffer sql = new StringBuffer();
		
		// 모든 게임의 평균 점수와 ID를 출력하는 Query문
		sql.append("SELECT m.id, (AVG(ms.score1) + AVG(ms.score2)) / 2 avg_game \n");
		sql.append("FROM member m, member_score ms 								\n");
		sql.append("WHERE m.id = ms.id 											\n");
		sql.append("GROUP BY ms.id 										  		\n");
		sql.append("ORDER BY avg_game DESC 										  ");
		
		pstmt = con.prepareStatement(sql.toString());
		
		rs = pstmt.executeQuery();
		
		while(rs.next()) { // list에서 데이터를 한 줄씩 꺼냄
			String id = rs.getString("id");
			int sum = rs.getInt("avg_game");
			
			GameVO vo = new GameVO(id, sum);
			
			list.add(vo);
		}
		
		return list;
	}
}