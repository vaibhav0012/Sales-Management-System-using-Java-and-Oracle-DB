package com.vaibhav.sales.service;
import java.util.*;
import java.util.Date;

import com.vaibhav.sales.beans.Product;
import com.vaibhav.sales.beans.Sales;
import com.vaibhav.sales.beans.SalesReport;
import com.vaibhav.sales.dao.SalesDao;
import com.vaibhav.sales.dao.StockDao;

public class Administrator 
{
	private static StockDao stockDao = new StockDao();
	private static SalesDao salesDao = new SalesDao();
	
	public synchronized String insertStock(Product product)
	{		
		if (product != null && product.getProductName().length() >= 2) {
			String productID = stockDao.generateProductID(product.getProductName());
			product.setProductID(productID);
			if (stockDao.insertStock(product) == 1)
				return productID;
			else
				return "Data not Valid for insertion";
		} else {
			return "Data not Valid for insertion";
		}
	}
	public String deleteStock(String ProductID) 
	{		
		if (stockDao.deleteStock(ProductID) == 1)
		{
			return "deleted";
		}
		else 
		{
			return "record cannot be deleted";
		}
	}
	public String insertSales(Sales sales) 
	{		
		if (sales == null) 
		{
			return "Object not valid for insertion";
		}
		
		if (stockDao.getStock(sales.getProductID()) == null)
		{
			return "Unknown Product for sales";
		}
		
		if (stockDao.getStock(sales.getProductID()).getQuantityOnHand() < sales.getQuantitySold())
		{
			return "Not enough stock on hand for sales";
		}
		
		if (sales.getSalesDate().before(new Date()))
		{
			return "Invalid date";
		}
		
		String salesID = salesDao.generateSalesID(sales.getSalesDate());
		sales.setSalesID(salesID);
		
		if (salesDao.insertSales(sales) == 1)
		{
			if (stockDao.updateStock(sales.getProductID(), sales.getQuantitySold()) == 1)
			{
				return "sales record inserted successfully";
			}
			else 
			{
				return "Error";
			}
		}
		else 
		{
			return "Error";
		}
	}
	public ArrayList<SalesReport> getSalesReport()
	{	
		return salesDao.getSalesReport();
	}
}
