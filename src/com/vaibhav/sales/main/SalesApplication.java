package com.vaibhav.sales.main;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.vaibhav.sales.beans.Product;
import com.vaibhav.sales.beans.Sales;
import com.vaibhav.sales.service.Administrator;


public class SalesApplication 
{
	public static void main(String[] args) throws ParseException {
		Scanner scan = new Scanner(System.in);
		
		Administrator admin = new Administrator();
		
		int choice = 0;
		
		do {
			System.out.println("1. Insert Stock");
			System.out.println("2. Delete Stock");
			System.out.println("3. Insert Sales");
			System.out.println("4. View Sales Report");
			System.out.print("Enter your Choice: ");
			choice = scan.nextInt();
			
			switch (choice) {
			case 1:
				Product product = new Product();
				System.out.print("Enter product ID: ");
				product.setProductID(scan.nextLine());
				System.out.print("Enter product name: ");
				product.setProductName(scan.nextLine());
				System.out.print("Enter quantity on hand: ");
				product.setQuantityOnHand(scan.nextInt());
				scan.nextLine();
				System.out.print("Enter product unit price: ");
				product.setProductUnitPrice(scan.nextDouble());
				System.out.print("Enter product reorder level: ");
				product.setReorderLevel(scan.nextInt());
				scan.nextLine();
				admin.insertStock(product);
				break;
			case 2:
				System.out.print("Enter product id to be deleted: ");
				String removeId = scan.nextLine();
				removeId = admin.deleteStock(removeId);
				if (removeId != null)
				{
					System.out.println(removeId + " removed successfully");
				}
				break;
			case 3:
				Sales sales = new Sales();
				System.out.print("Enter sales id: ");
				sales.setSalesID(scan.nextLine());
				System.out.print("Enter date (dd-mm-yyyy): ");
				String sDate = scan.nextLine();  
			    Date date = new SimpleDateFormat("dd-mm-yyyy").parse(sDate); 
				sales.setSalesDate(date);
				System.out.print("Enter product id: ");
				sales.setProductID(scan.nextLine());
				System.out.print("Enter quantity sold: ");
				sales.setQuantitySold(scan.nextInt());
				scan.nextLine();
				System.out.print("Enter sales price per unit: ");
				sales.setSalesPricePerUnit(scan.nextDouble());
				admin.insertSales(sales);
				break;
			case 4:
				admin.getSalesReport();
				break;
			default:
				System.out.println("Exiting...");
				choice = 0;
				break;
			}
		} while (choice >= 1 && choice <= 4);
		
		scan.close();
	}
}
