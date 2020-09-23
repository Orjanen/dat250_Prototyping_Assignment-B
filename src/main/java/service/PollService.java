package service;

import DAO.PollDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PollService {

    private final PollDAO pollDAO;

    @Autowired
    public PollService(@Qualifier("pollDatabase") PollDAO pollDAO) {this.pollDAO = pollDAO;}
}
