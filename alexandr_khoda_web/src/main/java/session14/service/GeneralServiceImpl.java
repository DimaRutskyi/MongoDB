package session14.service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import session14.dao.CompanyDao;
import session14.dao.CompanyDaoImpl;
import session14.dao.EmployeeDao;
import session14.dao.EmployeeDaoImpl;

import java.util.Locale;

/**
 * Created by s_okhoda on 14.02.2016.
 */
public class GeneralServiceImpl {
    private SessionFactory factory;

    public GeneralServiceImpl() {
        factory = getSessionFactory();
        EmployeeDao empDao = new EmployeeDaoImpl(factory);
        CompanyDao companyDaoDao = new CompanyDaoImpl(factory);
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }



    public SessionFactory getSessionFactory() {
        Locale.setDefault(Locale.ENGLISH);
        Configuration cfg =
                new Configuration().configure("session14/hibernate.cfg.xml");
        StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
        sb.applySettings(cfg.getProperties());
        StandardServiceRegistry standardServiceRegistry = sb.build();

        return cfg.buildSessionFactory(standardServiceRegistry);
    }

}
