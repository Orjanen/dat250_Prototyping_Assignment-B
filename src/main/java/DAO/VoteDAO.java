package DAO;

import models.Poll;
import models.User;
import models.Vote;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class VoteDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("proto");
    EntityManager em = emf.createEntityManager();

    public List<Vote> findAllVotes(){
        return em.createQuery("SELECT v from Vote v", Vote.class).getResultList();
    }


    public List<Vote> findAllVotesForAPoll(Poll poll){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Vote> criteriaQuery = builder.createQuery(Vote.class);
        Root<Vote> c = criteriaQuery.from(Vote.class);
        criteriaQuery.select(c)
                .where(builder.equal(
                        c.get("poll"), poll
                ));
        TypedQuery<Vote> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public List<Vote> FindAllVotesByUser(User user){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Vote> criteriaQuery = builder.createQuery(Vote.class);
        Root<Vote> c = criteriaQuery.from(Vote.class);
        criteriaQuery.select(c)
                .where(builder.equal(
                        c.get("voteCastByUser"), user
                ));
        TypedQuery<Vote> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }

    public void addVote(Vote vote){
        em.getTransaction().begin();

        em.persist(vote);

        em.getTransaction().commit();
    }



}
