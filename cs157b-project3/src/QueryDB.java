import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class QueryDB {

	private Session s;
	private SessionFactory sf;
	
	/**
	 * Constructor
	 */
	public QueryDB() {
		//SessionFactory is very expensive, so it is only created once
		sf = new Configuration().configure().buildSessionFactory();
	}
	
	/**
	 * Populate DB with test sales data
	 */
	public void populateDB(ArrayList<Sales> list) {
		//Create a new session and save data to the DB
		s = sf.openSession();
		s.beginTransaction();
		
		//Save all new Sales records to the DB
		for (Sales sale : list) {
			s.save(sale);
		}

		s.getTransaction().commit();
		s.close();
	}
	
	/**
	 * Query DB for a certain sale
	 * @param date Date that the sale took place
	 * @return Sales object that is associated with the date
	 */
	public Sales queryDB(String date) {
		//Reopen a session with DB
		s = sf.openSession();
		s.beginTransaction();
		Sales result = (Sales) s.get(Sales.class, date);
		s.close();
		
		return result;
	}
	
	/**
	 * Query the database for sales data for a particular product over a time period
	 * @param productName name of the product
	 * @param fromDate starting date
	 * @param toDate ending date
	 */
	public void queryDBRange(String productName, String fromDate, String toDate) {
		Sales sample1 = queryDB(fromDate);
		Sales sample2 = queryDB(toDate);
		
		System.out.printf("%s sales during 4/19/17: %d \n", sample1.getProductName(), sample1.getQuantity());
		System.out.printf("%s sales during 4/24/17: %d \n", sample2.getProductName(), sample2.getQuantity());
		
		System.out.printf("There has been a change in sales of: %d \n", sample2.getQuantity()-sample1.getQuantity());
	}
	
	/**
	 * Query DB with aggregate functions
	 * @param cmd the aggregate function
	 * @param arg the relevant parameter
	 */
	public void aggregateFunctions(String cmd, String arg) {
		s = sf.openSession();
		s.beginTransaction();
		String query = "";
		
		if (cmd.equals("ORDER BY")) {
			query = String.format("SELECT ProductName FROM Sales ORDER BY %s", arg);
			ArrayList<String> rs = (ArrayList<String>) s.createQuery(query).getResultList();
			
			for (String result : rs) {
				System.out.println(result);
			}
				
		} else if (cmd.equals("MIN")) {
			query = String.format("SELECT MIN(%s) FROM Sales", arg);
			System.out.println(s.createQuery(query).getSingleResult());

		} else if (cmd.equals("MAX")) {
			query = String.format("SELECT MAX(%s) FROM Sales",arg);
			System.out.println(s.createQuery(query).getSingleResult());
			
		} else if (cmd.equals("DISTINCT")) {
			query = String.format("SELECT COUNT(DISTINCT %s) FROM Sales", arg);
			System.out.println(s.createQuery(query).getSingleResult());
		}
		s.close();
		
	}
}
