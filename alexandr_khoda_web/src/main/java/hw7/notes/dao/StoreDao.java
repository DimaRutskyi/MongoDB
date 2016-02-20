package hw7.notes.dao;

import hw7.notes.domain.Memory;
import hw7.notes.domain.Store;
import hw7.notes.domain.Vendor;
import org.hibernate.HibernateException;

import java.util.List;

/**
 * Created by s_okhoda on 16.02.2016.
 */
public interface StoreDao {
    Long create(Store store);
    Store read(Long id);
    boolean update(Store store);
    boolean delete(Store store);
    List findAll();
}
