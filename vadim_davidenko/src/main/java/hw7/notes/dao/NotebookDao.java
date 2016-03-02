package hw7.notes.dao;

import hw7.notes.domain.Notebook;
import hw7.notes.domain.Vendor;

import java.util.List;

/**
 * Created by Вадим on 14.02.2016.
 */
public interface NotebookDao {
    Long create(Notebook notebook);

    Notebook read(Long id);

    boolean update(Notebook notebook);

    boolean delete(Notebook notebook);

    List findAll();


    List findByPortion(int position, int size);

    List findByCpuVendor(Vendor cpuVendor);

    List findAllOnStore();

    List findGtAmount(int amount);
}
