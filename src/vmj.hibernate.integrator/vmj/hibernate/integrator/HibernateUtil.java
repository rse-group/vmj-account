package vmj.hibernate.integrator;

import java.util.*;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    public static SessionFactory factory;

    private HibernateUtil() { }

    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            Configuration configuration = new Configuration();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			factory = configuration.buildSessionFactory(serviceRegistry);
        }
        return factory;
    }

    public static synchronized void buildSessionFactory(Configuration configuration) {
        if (factory == null) {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            factory = configuration.buildSessionFactory(serviceRegistry);
        }
    }

}