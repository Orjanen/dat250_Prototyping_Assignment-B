package DAO;


import models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;


public class UserDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("proto");
    EntityManager em = emf.createEntityManager();

    public List<User> findAllUsers(){
        return em.createQuery("SELECT u from appUser u", User.class).getResultList();
    }

    public User findUserById(Long id){
        return em.find(User.class, id);
    }

    public void addNewUser(User user){
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public User updateUser(User user){
        em.getTransaction().begin();
        User updatedUser = em.merge(user);
        em.getTransaction().commit();
        return updatedUser;
    }

    public void deleteUser(User user){
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }




}
