package hw7.notes.dao;


import hw7.notes.domain.Notebook;

import java.util.List;

/**
 * Created by s_okhoda on 09.02.2016.
 */
public interface NotebookDao {
    Long create(Notebook ntb);
    hw7.notes.domain.Notebook read(Long id);
    boolean update(Notebook ntb);
    boolean delete(Notebook ntb);
    List findAll();


}
