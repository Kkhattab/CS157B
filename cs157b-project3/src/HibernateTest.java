import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest {

	public static void main(String[] args) {
		Sales iPhone = new Sales();
		iPhone.setDate("4/19/17");
		iPhone.setProductName("iPhone 8");
		iPhone.setQuantity(1);
		iPhone.setUnitCost(800.00);
		iPhone.setTotalCost(800.00);

		Sales s8 = new Sales();
		s8.setDate("4/20/17");
		s8.setProductName("Samsung Galaxy S8");
		s8.setQuantity(1);
		s8.setUnitCost(800.00);
		s8.setTotalCost(800.00);

		Sales yoga = new Sales();
		yoga.setDate("1/1/17");
		yoga.setProductName("Lenovo Yoga Book");
		yoga.setQuantity(1);
		yoga.setUnitCost(1200.00);
		yoga.setTotalCost(1200.00);

		Sales nswitch = new Sales();
		nswitch.setDate("2/3/17");
		nswitch.setProductName("Nintendo Switch");
		nswitch.setQuantity(1);
		nswitch.setUnitCost(500.00);
		nswitch.setTotalCost(500.00);

		Sales macbook = new Sales();
		macbook.setDate("12/5/15");
		macbook.setProductName("Macbook");
		macbook.setQuantity(1);
		macbook.setUnitCost(1500.00);
		macbook.setTotalCost(1500.00);

		SessionFactory sf = new Configuration().configure().buildSessionFactory();

		//Create a new session
		Session s = sf.openSession();
		s.beginTransaction();
		s.save(iPhone);
		s.save(s8);
		s.save(yoga);
		s.save(nswitch);
		s.save(macbook);
		s.getTransaction().commit();

	}

}
