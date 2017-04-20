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
		Session s = sf.openSession();
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
		//Retrieve data from the DB
		s = sf.openSession(); //Reopen a session with DB
		s.beginTransaction();
		return (Sales) s.get(Sales.class, date);
	}
}
