package org.bitvector.microservice3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DbHandler implements ProductDAI { // SUPER IMPORTANT PER ENTITY
    private Logger logger;
    private SessionFactory sessionFactory;

    DbHandler() {
        logger = LoggerFactory.getLogger("org.bitvector.microservice3.DbHandler");
        logger.info("Starting DbHandler...");

        Configuration configuration = new Configuration()
                .setProperties(new Properties(System.getProperties()))
                .addAnnotatedClass(ProductEntity.class) // SUPER IMPORTANT PER ENTITY
                .configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public void close() {
        logger.info("Stopping DbHandler...");
        sessionFactory.close();
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        Session session = sessionFactory.openSession();
        List objs = session.createQuery("FROM ProductEntity")
                .setCacheable(true)
                .list();
        session.disconnect();

        List<ProductEntity> products = new ArrayList<>();
        for (Object obj : objs) {
            products.add((ProductEntity) obj);
        }
        return products;
    }

    @Override
    public ProductEntity getProductById(Integer id) {
        Session session = sessionFactory.openSession();
        List objs = session.createQuery("FROM ProductEntity WHERE id=:ID")
                .setParameter("ID", id)
                .setCacheable(true)
                .list();
        session.disconnect();

        return (ProductEntity) objs.get(0);
    }

    @Override
    public void addProduct(ProductEntity product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(product);
        tx.commit();
        session.disconnect();
    }

    @Override
    public void updateProduct(ProductEntity product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(product);
        tx.commit();
        session.disconnect();
    }

    @Override
    public void deleteProduct(ProductEntity product) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(product);
        tx.commit();
        session.disconnect();
    }
}
