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

public class DBHandler implements ProductDAO {

    private Logger logger;
    private SessionFactory sessionFactory;

    public void start() {
        logger = LoggerFactory.getLogger("org.bitvector.microservice.DbHandler");

        Configuration configuration = new Configuration()
                .setProperties(new Properties(System.getProperties()))
                .addAnnotatedClass(ProductEntity.class)                         // SUPER FUCKING IMPORTANT PER COLLECTION
                .configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        logger.info("Started a DbHandler...");
    }

    public void stop() {
        sessionFactory.close();
        logger.info("Stopped a DbHandler...");
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
        List products = session.createQuery("FROM ProductEntity WHERE id=:ID")
                .setParameter("ID", id)
                .setCacheable(true)
                .list();
        session.disconnect();

        if (products.size() > 0) {
            return (ProductEntity) products.get(0);
        } else {
            return null;
        }
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
