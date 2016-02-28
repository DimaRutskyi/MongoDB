package hw7.notes.dao;



import hw7.notes.domain.Notebook;
import hw7.notes.domain.Vendor;
import hw7.notes.service.NotebookServiceImpl;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Admin on 17.02.2016.
 */
public class NotebookDaoImpl implements NotebookDao {

    public NotebookDaoImpl(){}

    private SessionFactory sessionFactory = NotebookServiceImpl.getSessionFactory();

    @Override
    public Long create(Notebook notebook) {
        Long id = null;
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            id = (Long)session.save(notebook);
            session.getTransaction().commit();
            return id;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return null;
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public Notebook read(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return (Notebook) session.get(Notebook.class, id);
        } catch (HibernateException e){
            return null;
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public boolean update(Notebook notebook) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(notebook);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public boolean delete(Notebook notebook) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(notebook);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e){
            session.getTransaction().rollback();
            return false;
        } finally {
            session.close();
            sessionFactory.close();
        }
    }

    @Override
    public List findAll() {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from hw7.notes.domain.Notebook");
        session.close();
        sessionFactory.close();
        return query.list();
    }

    @Override
    public List getNotebooksByPortion(int size) {
        return null;
    }

    @Override
    public List getNotebooksGtAmount(int amount) {

        return null;
    }

    @Override
    public List getNotebooksByCpuVendor(Vendor cpuVendor) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from hw7.notes.domain.Notebook n " +
                "where n.cpu c c.vendor like " + cpuVendor);

        session.close();
        sessionFactory.close();
        return query.list();
    }

    @Override
    public List getNotebooksFromStore() {
        return null;
    }

    @Override
    public List getNotebooksStorePresent() {
        return null;
    }
}
