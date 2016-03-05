package hw6.notes.dao;

import hw6.notes.domain.Notebook;

import java.util.Date;
import java.util.List;

/**
 * Created by Пк2 on 09.02.2016.
 */
public interface NotebookDao {
    Long create(Notebook ntb);
    Notebook read(Long id);
    boolean update(Notebook ntb);
    boolean delete(Notebook ntb);
    List findAll();
    List findByModel(String model);
    List findByVendor(String vendor);
    List findByPriceManufDate(Double price, Date date);
    List findBetweenPriceLtDateByVendor(Double priceFrom, Double priceTo, Date date, String vendor);
}