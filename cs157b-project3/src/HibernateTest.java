import java.util.ArrayList;

public class HibernateTest {

	public static void main(String[] args) {
		QueryDB test = new QueryDB();
		ArrayList<Sales> sales = new ArrayList<Sales>();
		
		//Test sales data
		Sales iPhone = new Sales();
		iPhone.setDate("4/19/17");
		iPhone.setProductName("iPhone 8");
		iPhone.setQuantity(10000000);
		iPhone.setUnitCost(800.00);
		iPhone.setTotalCost(8000000000.00);

		Sales iPhone1 = new Sales();
		iPhone1.setDate("5/24/17");
		iPhone1.setProductName("iPhone 8");
		iPhone1.setQuantity(8000000);
		iPhone1.setUnitCost(800.00);
		iPhone1.setTotalCost(6400000000.00);
		
		Sales s8 = new Sales();
		s8.setDate("4/20/17");
		s8.setProductName("Samsung Galaxy S8");
		s8.setQuantity(8000000);
		s8.setUnitCost(800.00);
		s8.setTotalCost(6400000000.00);
		
		Sales s81 = new Sales();
		s81.setDate("5/25/17");
		s81.setProductName("Samsung Galaxy S8");
		s81.setQuantity(6000000);
		s81.setUnitCost(800.00);
		s81.setTotalCost(4800000000.00);

		Sales yoga = new Sales();
		yoga.setDate("1/1/17");
		yoga.setProductName("Lenovo Yoga Book");
		yoga.setQuantity(2000000);
		yoga.setUnitCost(1200.00);
		yoga.setTotalCost(2400000000.00);

		Sales nswitch = new Sales();
		nswitch.setDate("2/3/17");
		nswitch.setProductName("Nintendo Switch");
		nswitch.setQuantity(100000000);
		nswitch.setUnitCost(500.00);
		nswitch.setTotalCost(50000000000.00);

		Sales macbook = new Sales();
		macbook.setDate("12/5/15");
		macbook.setProductName("Macbook");
		macbook.setQuantity(100000000);
		macbook.setUnitCost(1500.00);
		macbook.setTotalCost(150000000000.00);

		//Add all test sales data to an array list
		sales.add(macbook);
		sales.add(nswitch);
		sales.add(yoga);
		sales.add(s8);
		sales.add(s81);
		sales.add(iPhone);
		sales.add(iPhone1);
		
		//Pass test sales data into populateDB, to be saved into the DB
		test.populateDB(sales);

		//Retrieve and display one test Sales object
		Sales sample = test.queryDB("2/3/17");
		System.out.println("Date | Product Name | Quantity | Total Cost | Unit Cost");
		System.out.println(sample.getDate() + " | " + sample.getProductName() + " | " + sample.getQuantity() + " | " + sample.getTotalCost() + " | " + sample.getUnitCost());
		
		//Retrieve sales for a given product over a given time period
		Sales sample1 = test.queryDB("4/19/17");
		Sales sample2 = test.queryDB("5/24/17");
		
		System.out.printf("iPhone 8 sales during 4/19/17: %d \n", sample1.getQuantity());
		System.out.printf("iPhone 8 sales during 4/24/17: %d \n", sample2.getQuantity());
		
		System.out.printf("There has been a change in sales of: %d \n", sample2.getQuantity()-sample1.getQuantity());
		
		//Test aggregate functions
		test.aggregateFunctions("ORDER BY", "ProductName");
		test.aggregateFunctions("MAX", "UnitCost");
		test.aggregateFunctions("MIN", "UnitCost");
		test.aggregateFunctions("DISTINCT", "ProductName");
	}

}
