package com.javaex.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookSelectOne {

	public static void main(String[] args) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
		// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/book_db";
			conn = DriverManager.getConnection(url, "book", "book");
		// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select  b.book_id, ";
			query += " 			b.title, ";
			query += " 			b.pubs, ";
			query += " 			b.pub_date, ";
			query += " 			b.author_id, ";
			query += " 			a.author_name, ";
			query += " 			a.author_desc ";
			query += " from book b ";
			query += " left join author a on b.author_id = a.author_id ";
			
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			int bookId = rs.getInt("b.book_id");
			String title = rs.getString("b.title");
			String pubs = rs.getString("b.pubs");
			String pubDate = rs.getString("b.pub_date");
			int authorId = rs.getInt("b.author_id");
			String authorName = rs.getString("a.author_name");
			String authorDesc = rs.getString("a.author_desc");

			System.out.println(bookId + ".\t" + title + "\t" + pubs + "\t" + pubDate + "\t" + authorId + "\t" + authorName + "\t" + authorDesc);
			
		// 4.결과처리
		} catch (ClassNotFoundException e) {
		System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
		System.out.println("error:" + e);
		} finally {
		// 5. 자원정리
		try {
		if (rs != null) {
		rs.close();
		}
		if (pstmt != null) {
		pstmt.close();
		}
		if (conn != null) {
		conn.close();
		}
		} catch (SQLException e) {
		System.out.println("error:" + e);
		}
		}
		
	}

}
