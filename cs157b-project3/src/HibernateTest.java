import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {

	public static void main(String[] args) {
		Sales test = new Sales();
		test.setDate("4/19/17");
		test.setProductName("iPhone 8");
		test.setQuantity(1);
		test.setUnitCost(800.00);
		test.setTotalCost(800.00);
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		
		//Create a new session
		Session s = sf.openSession();
		s.beginTransaction();
		s.save(test);
		s.getTransaction().commit();
		
	}

}
