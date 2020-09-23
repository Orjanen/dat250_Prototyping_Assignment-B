package service;

import DAO.VoteDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final VoteDAO voteDAO;

    public VoteService(@Qualifier("pollDatabase") VoteDAO voteDAO) { this.voteDAO = voteDAO;}
}
