package com.javaex.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSelectAll {

	public static void main(String[] args) {
		
		List<BookVo> bookList = new ArrayList<BookVo>();

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
					
					while(rs.next()) {
						
						int bookId = rs.getInt("b.book_id");
						String title = rs.getString("b.title");
						String pubs = rs.getString("b.pubs");
						String pubDate = rs.getString("b.pub_date");
						int authorId = rs.getInt("b.author_id");
						String authorName = rs.getString("a.author_name");
						String authorDesc = rs.getString("a.author_desc");
						
						BookVo bookVo = new BookVo(bookId, title, pubs, pubDate, authorId, authorName, authorDesc);
						
						bookList.add(bookVo);
						
					}
					
					System.out.println(bookList.toString());
					
					for(int i=0; i<bookList.size(); i++) {
						int bookId = bookList.get(i).getBookId();
						String title = bookList.get(i).getTitle();
						String pubs = bookList.get(i).getPubs();
						String pubDate = bookList.get(i).getPubDate();
						int authorId = bookList.get(i).getAuthorId();
						String authorName = bookList.get(i).getAuthorName();
						String authorDesc = bookList.get(i).getAuthorDesc();
						
						System.out.println(bookId + "." + title + "\t" + pubs + "\t" + pubDate + "\t" + authorId + "\t" + authorName + "\t" + authorDesc);
					}

					
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
