package hw7.springsnotes.dao;

import hw7.springsnotes.domain.Sales;

import java.util.List;
import java.util.Map;



/**
 * Created by Admin on 17.02.2016.
 */
public interface SalesDao {
    Long create(Sales sales);
    Sales read(Long ig);
    boolean update(Sales sales);
    boolean delete(Sales sales);
    List findAll();
    Map getSalesByDays();
}
