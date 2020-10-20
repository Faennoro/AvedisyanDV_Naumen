package entity;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        //проверка на наличие sessionFactory, при отсутствии - инициализируется
        if (sessionFactory==null){
            try{
                //конфигурация для сессии с привязкой класса NoteEntity
                Configuration config = new Configuration().configure("resources/hibernate.cfg.xml");
                config.addAnnotatedClass(NoteEntity.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                sessionFactory = config.buildSessionFactory(serviceRegistry);
                return sessionFactory;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
