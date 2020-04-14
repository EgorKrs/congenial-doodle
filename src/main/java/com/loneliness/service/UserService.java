package com.loneliness.service;

import com.loneliness.entity.domain.User;
import com.loneliness.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserService extends CRUDService<User> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repository, SearchService searcher, PasswordEncoder passwordEncoder){
        this.repository=repository;
        this.searchService=searcher;
        this.passwordEncoder=passwordEncoder;
    }

    @NotNull
    public Optional<User> findByUsername(String username){
       return  ((UserRepository)repository).findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userOptional=findByUsername(s);
        if(userOptional.isPresent())
       return userOptional.get();
        else throw new UsernameNotFoundException("No such user exist");
    }

    @Override
    public Integer saveAndReturnId(User note) {
        note.setPassword(passwordEncoder.encode(note.getPassword()));
        return super.saveAndReturnId(note);
    }

    @Override
    public User save(User note) {
        note.setPassword(passwordEncoder.encode(note.getPassword()));
        return super.save(note);
    }
}
