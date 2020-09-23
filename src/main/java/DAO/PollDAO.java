package DAO;

import models.Poll;
import models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


@Repository("pollDatabase")
public class PollDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("proto");
    EntityManager em = emf.createEntityManager();

    public List<Poll> findAllPolls(){
        return em.createQuery("SELECT p from Poll p", Poll.class).getResultList();
    }

    public Poll findPollById(Long id){
        return em.find(Poll.class, id);
    }

    //TODO: Forandre til strongly typed queries
    public List<Poll> findAllPollsCreatedByUser(User user){

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Poll> criteriaQuery = builder.createQuery(Poll.class);
        Root<Poll> c = criteriaQuery.from(Poll.class);
        criteriaQuery.select(c)
                .where(builder.equal(
                        c.get("creator"), user));

        TypedQuery<Poll> query = em.createQuery(criteriaQuery);
        return query.getResultList();

    }

    public Poll updatePoll(Poll poll){

        em.getTransaction().begin();
        Poll updatedPoll = em.merge(poll);
        em.getTransaction().commit();

        return updatedPoll;
    }

    public void addNewPoll(Poll poll){
        em.getTransaction().begin();
        em.persist(poll);
        em.getTransaction().commit();

    }
}
