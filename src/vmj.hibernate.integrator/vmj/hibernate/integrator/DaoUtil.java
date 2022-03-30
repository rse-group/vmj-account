package vmj.hibernate.integrator;

import java.util.UUID;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DaoUtil<Y> {
    private Class<? extends Y> componentClass;

    public DaoUtil(Class<? extends Y> componentClass){ 
        this.componentClass = componentClass;
    }

    public void saveObject(Y object) {
        Transaction transaction = null;

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void updateObject(Y object) {
        Transaction transaction = null;

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    // public Y getObject(UUID id) {
    public Y getObject(int id) {
        Transaction transaction = null;
        Y object = null;

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            transaction = session.beginTransaction();
            object = session.get(componentClass, id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return object;
    }

    public <Z> List<Y> getListObject(String tableName, String columnName, Z columnValue){
        Transaction transaction = null;
        List<Y> objectList = null;

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            transaction = session.beginTransaction();
            String queryStr = "FROM " + tableName + " WHERE " + columnName + " = :columnValue";
            Query<Y> query = session.createQuery(queryStr);
            query.setParameter("columnValue", columnValue);
            objectList = query.list();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return objectList;
    }

    public List<Y> getAllObject(String tableName){
        Transaction transaction = null;
        List<Y> objectList = null;

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            transaction = session.beginTransaction();
            String queryStr = "FROM " + tableName;
            objectList = session.createQuery(queryStr).getResultList();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return objectList;
    }

    // public void deleteObject(UUID id) {
    public void deleteObject(int id) {
        Transaction transaction = null;
        Y savedInstance = null;

        try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
            transaction = session.beginTransaction();
            savedInstance = session.get(componentClass, id);
            if (savedInstance != null) {
                session.delete(savedInstance);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
    
    // public <T> T getProxyObject(Class<T> type, UUID idObject) {
    public <T> T getProxyObject(Class<T> type, int idObject) {
        Transaction transaction = null;
        T object = null;

            try (Session session = (HibernateUtil.getSessionFactory()).openSession()) {
                transaction = session.beginTransaction();
                object = session.load(type, idObject);
                transaction.commit();
            } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return object;
    }
}
