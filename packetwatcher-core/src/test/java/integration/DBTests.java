package integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import com.jfs415.packetwatcher_core.model.packets.ArchivedFlaggedPacketRecord;
import com.jfs415.packetwatcher_core.model.packets.FlaggedPacketRecord;

public class DBTests {

	private final DBConfig config = new DBConfig();

	@Test
	public void testConnect() {
		assert config.getSession() != null;
	}

	@Test
	public void testInsert() {
		Session session = config.getCurrentSession();
		Transaction trans = session.beginTransaction();
		session.save(FlaggedPacketRecord.createTestPacket());
		trans.commit();
	}

	@Test
	public void testUpdate() {

	}

	@Test
	public void testDelete() {

	}

	private static class DBConfig {

		private SessionFactory sessionFactory;

		public DBConfig() {
			init();
		}

		private void init() {
			Configuration config = new Configuration();

			config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
			config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/packetwatcher");
			config.setProperty("hibernate.connection.username", "");
			config.setProperty("hibernate.connection.password", "");
			config.setProperty("hibernate.connection.pool_size", "1");
			config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
			config.setProperty("hibernate.jdbc.batch_size", "150");
			config.setProperty("hibernate.show_sql", "false");
			config.setProperty("hibernate.format_sql", "false");
			config.setProperty("hibernate.hbm2ddl.auto", "update");
			config.setProperty("hibernate.order_inserts", "true");
			config.setProperty("hibernate.order_updates", "true");
			config.setProperty("hibernate.current_session_context_class", "thread");

			config.addAnnotatedClass(FlaggedPacketRecord.class);
			config.addAnnotatedClass(ArchivedFlaggedPacketRecord.class);

			sessionFactory = config.buildSessionFactory();
		}

		protected Session getSession() {
			return sessionFactory.openSession();
		}

		protected Session getCurrentSession() {
			return sessionFactory.getCurrentSession();
		}

	}

}
