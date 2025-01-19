package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Util util = new Util();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE users (id bigint auto_increment primary key, " +
                    "name varchar(80), lastName varchar(80), age tinyint)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
            System.out.println("Таблица пользователей создалась");
        } catch (Exception e) {
            System.out.println("Таблица пользователей не создалась " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "DROP TABLE users";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();

            transaction.commit();
            System.out.println("Таблица пользователей удалилась");
        } catch (Exception e) {
            System.out.println("Таблица пользователей не удалилась" + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            //session.getTransaction().commit();
            transaction.commit();
            System.out.println("Пользователь успешно добавлен в таблицу");
        } catch (Exception e) {
            System.out.println("Не удалось добавить пользователя в таблицу" + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User WHERE id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Пользователь удалился по id");
        } catch (Exception e) {
            System.out.println("Пользователь не удалился по id" + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SessionFactory sessionFactory = util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("from User");
            users = (List<User>) query.getResultList();
            for (User u : users) {
                System.out.println(u);
            }
            transaction.commit();
            System.out.println("Все пользователи в коллекции");
        } catch (Exception e) {
            System.out.println("При попытке достать всех пользователей из базы данных произошло исключение" + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Пользователи удалились из таблицы");
        } catch (Exception e) {
            System.out.println("При очистки таблицы произошло исключение" + e);
        }
    }
}
