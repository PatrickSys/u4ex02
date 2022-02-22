package utils;

import entity.CustomEntity;
import entity.LibrosEntity;
import entity.PrestamosEntity;
import entity.SociosEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.Entity;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

/************************************************************************
 Made by        PatrickSys
 Date           03/02/2022
 Package        services
 Description:
 ************************************************************************/


public class HibernateUtil {

    Configuration configuration;
    ServiceRegistry serviceRegistry;
    SessionFactory sessionFactory;
    Session session;


    public HibernateUtil() {
        this.initializeConfig();
    }

    public void persist(Object object) {

        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        this.session.persist(object);
        this.session.flush();
        this.session.close();

    }

    public void update(Object object) {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        this.session.update(object);
        this.session.flush();
        this.session.close();
    }

    public void delete(Object object) {
        this.session = this.sessionFactory.openSession();
        this.session.beginTransaction();
        this.session.delete(object);
        this.session.flush();
        this.session.close();
    }


    public CustomEntity<?> getFromDb(int id, CustomEntity<?> entity) {
        this.session.beginTransaction();
        CustomEntity<?> retrievedEntity = this.session.get(entity.getClass(), id);
        this.session.flush();
        this.session.close();
        return retrievedEntity;
    }

    private void initializeConfig() {

        if (this.sessionFactory == null) {
            // loads configuration and mappings
            this.configuration = new Configuration().configure();
            this.configuration.addAnnotatedClass(LibrosEntity.class);
            this.configuration.addAnnotatedClass(PrestamosEntity.class);
            this.configuration.addAnnotatedClass(SociosEntity.class);
            this.serviceRegistry
                    = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            // builds a session factory from the service registry
            this.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }


        this.session = this.sessionFactory.openSession();

    }



}
