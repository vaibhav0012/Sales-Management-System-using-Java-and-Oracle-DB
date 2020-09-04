package com.vaibhav.sales.dao;
import java.util.*;
import java.util.Date;
import java.sql.*;

import com.vaibhav.sales.beans.*;
import com.vaibhav.sales.util.*;

public class SalesDao 
{
	public int insertSales(Sales sales)
	{
		Connection conn = null;
		PreparedStatement pt = null;
		String query = "INSERT INTO TBL_SALES VALUES (?,?,?,?,?)";
		java.sql.Date sqlDate = new java.sql.Date(sales.getSalesDate().getTime());
		
		try
		{
			conn=DBUtil.getDBConnection();
			pt=conn.prepareStatement(query);
			pt.setString(1, sales.getSalesID());
			pt.setDate(2, sqlDate);
			pt.setString(3, sales.getProductID());
			pt.setInt(4, sales.getQuantitySold());
			pt.setDouble(5, sales.getSalesPricePerUnit());
			
			if(pt.executeUpdate()==1)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			return 0;
		}
		
	}
	public String generateSalesID(Date salesDate) {		
		Connection conn = null;
		PreparedStatement pt = null;
		String query = "SELECT SEQ_SALES_ID.NEXTVAL FROM DUAL";
		
		int SEQ_SALES_ID = 0;
		String out = salesDate.toString().substring(salesDate.toString().length()-2, salesDate.toString().length());
		
		try {
			conn = DBUtil.getDBConnection();
			pt = conn.prepareStatement(query);
			ResultSet rs = pt.executeQuery();
			
			rs.next();
			SEQ_SALES_ID = rs.getInt(1);
			
			out += SEQ_SALES_ID;
			return out;
		} 
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}
	}
	public ArrayList<SalesReport> getSalesReport() {
		Connection conn = null;
		PreparedStatement pt = null;
		String query = "SELECT * FROM V_SALES_REPORT";
		
		ArrayList<SalesReport> list = new ArrayList<SalesReport>();
		
		try {
			conn = DBUtil.getDBConnection();
			pt = conn.prepareStatement(query);
			ResultSet rs = pt.executeQuery();
			
			while (rs.next()) {
				SalesReport salesReport = new SalesReport();
				salesReport.setSalesID(rs.getString(1));
				salesReport.setSalesDate(rs.getDate(2));
				salesReport.setProductID(rs.getString(3));
				salesReport.setProductName(rs.getString(4));
				salesReport.setQuantitySold(rs.getInt(5));
				salesReport.setProductUnitPrice(rs.getDouble(6));
				salesReport.setSalesPricePerUnit(rs.getDouble(7));
				salesReport.setProfitAmount(rs.getDouble(8));
				list.add(salesReport);
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
			return null;
		}		
		
		return list;
	}

}
