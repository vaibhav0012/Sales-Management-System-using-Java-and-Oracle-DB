package com.vaibhav.sales.dao;
import java.sql.*;

import com.vaibhav.sales.beans.Product;
import com.vaibhav.sales.util.DBUtil;

public class StockDao 
{
	public int insertStock(Product stock) {
		Connection conn = null;
		PreparedStatement pt = null;
		String query = "INSERT INTO TBL_STOCK VALUES(?, ?, ?, ?, ?)";
		
		try {
			conn = DBUtil.getDBConnection();
			pt = conn.prepareStatement(query);
			pt.setString(1, stock.getProductID());
			pt.setString(2, stock.getProductName());
			pt.setInt(3, stock.getQuantityOnHand());
			pt.setDouble(4, stock.getProductUnitPrice());
			pt.setInt(5, stock.getReorderLevel());
			
			if (pt.executeUpdate() == 1) 
			{
				return 1;
			}
			else
			{
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public String generateProductID(String productName) {
		Connection conn = null;
		PreparedStatement pt = null;
		String query = "SELECT SEQ_PRODUCT_ID.NEXTVAL FROM DUAL";
		
		int SEQ_PRODUCT_ID = 0;
		String out = "";
		
		try {
			conn = DBUtil.getDBConnection();
			pt = conn.prepareStatement(query);
			ResultSet rs = pt.executeQuery();
			
			rs.next();
			SEQ_PRODUCT_ID = rs.getInt(1);
			
			out += productName.substring(0, 2);
			out += SEQ_PRODUCT_ID;
			
			return out;
		}
		catch (SQLException e)
		{
			System.out.println(e);
			return null;
		}
	}
	
	
	public int updateStock(String productID, int soldQty) {
		Connection conn = null;
		PreparedStatement pt = null;
		String query = "UPDATE TBL_STOCK SET Quantity_On_Hand = Quantity_On_Hand - ?"
				+ "WHERE Product_ID = ?";
		
		try {
			conn = DBUtil.getDBConnection();
			pt = conn.prepareStatement(query);
			pt.setInt(1, soldQty);
			pt.setString(2, productID);
			
			if (pt.executeUpdate() == 1)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		} catch (Exception e) 
		{
			System.out.println(e);
			return 0;
		}
	}
	
	public Product getStock(String productID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM TBL_STOCK WHERE Product_ID = ?";
		
		try {
			conn = DBUtil.getDBConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productID);
			
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			Product product = new Product();
			product.setProductID(rs.getString(1));
			product.setProductName(rs.getString(2));
			product.setQuantityOnHand(rs.getInt(3));
			product.setProductUnitPrice(rs.getDouble(4));
			product.setReorderLevel(rs.getInt(5));
			
			return product;
		} 
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}		
	}
	
	
	public int deleteStock(String productID) {
		Connection conn = null;
		PreparedStatement pt = null;
		String query = "DELETE TBL_STOCK WHERE Product_ID = ?";
		
		try {
			conn = DBUtil.getDBConnection();
			pt = conn.prepareStatement(query);
			pt.setString(1, productID);
			
			if (pt.executeUpdate() == 1) 
			{
				return 1;
			}
			else 
			{
				return 0;
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return 0;
		}
	}
}
