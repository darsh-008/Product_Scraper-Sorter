package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import sortProduct.sortProduct;
import Scrapper.productScrapper;

public class ProductSearchEngine {


	public static void main(String[] args) throws Exception {
		System.out.println("\t********************************"+ 
							"\n\tWelcome to Product Scrapper and Sorter\n"+
							"\t********************************");

		// Continue the code
		callingFunction();
	}

	private static void callingFunction() throws FileNotFoundException, IOException, InterruptedException {
		String input = null;
//		String value = null;
		System.out.print("\n\n-----------Select from below Choices?-----------\n\n");
		System.out.println("1. Sort product based on criteria");
		System.out.println("2. Get Product data from Google");
		System.out.println("3. Exit from search engine \n");
		System.out.println("Selection:: ");

		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();

		switch (input) {

		case "1":
			System.out.println("Select the sorting criteria from below: \n(1) Name  \n(2) Price  \n(3) Site");
			System.out.println("Selection:: ");
			int choice = sc.nextInt();
			// Call method for sorting
			sortProduct sp = new sortProduct();
			sp.sortCriteria(choice);
			break;
		
		case "2":
			System.out.println("Geting Product data from Google and saving it into product_data.txt");
			productScrapper ps = new productScrapper();
			ps.getProductData();
			
		case "3":
			System.out.println("  <<<<<<------Thank you!------>>>>>>>");
			System.out.println("<<<<<<---------Exiting--------->>>>>>>");
			sc.close();
			System.exit(0);
		default:
			System.out.println("Please enter a valid input");
		}

		callingFunction();
	}
}