package com.loneliness.service;

import com.loneliness.entity.domain.User;
import com.loneliness.repository.UserRepository;
import com.loneliness.util.search.Searcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService extends CRUDService<User>{
    public UserService(UserRepository repository, Searcher searcher){
        this.repository=repository;
        this.searcher=searcher;
    }
}
