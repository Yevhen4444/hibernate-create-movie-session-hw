package mate.academy.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.CinemaHallDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.CinemaHall;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private final SessionFactory sessionFactory;

    public CinemaHallDaoImpl() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(cinemaHall);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't add cinemaHall", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHall;
    }

    @Override
    public Optional<CinemaHall> get(Long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            CinemaHall cinemaHall = session.get(CinemaHall.class, id);
            transaction.commit();
            return Optional.ofNullable(cinemaHall);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't get cinemaHall", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        Session session = null;
        List<CinemaHall> cinemaHalls = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query<CinemaHall> query = session.createQuery("FROM CinemaHall", CinemaHall.class);
            cinemaHalls = query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all CinemaHall", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return cinemaHalls;
    }
}




