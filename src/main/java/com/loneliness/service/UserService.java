package com.loneliness.service;

import com.loneliness.entity.domain.User;
import com.loneliness.exception.DataIsAlreadyExistException;
import com.loneliness.exception.NotFoundException;
import com.loneliness.repository.UserRepository;
import com.loneliness.util.MailSender;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class UserService extends CRUDService<User> implements UserDetailsService {
    @Value("${hostname}")
    private String hostname;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repository, SearchService searcher, PasswordEncoder passwordEncoder, MailSender mailSender){
        this.repository=repository;
        this.searchService=searcher;
        this.passwordEncoder=passwordEncoder;
        this.mailSender=mailSender;
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
    public Integer saveAndReturnId(@NotNull User note) {
            note.setPassword(passwordEncoder.encode(note.getPassword()));
            return super.saveAndReturnId(note);

    }

    public User addUser(@NotNull User user) {
        checkUser(user);
        setActivationCode(user);
        setPassword(user);
        user =  super.save(user);
        sendMessage(user);
        return user;
    }

    @Override
    public User save(@NotNull User note) {
        note.setPassword(passwordEncoder.encode(note.getPassword()));
        return super.save(note);
    }

    public void checkUser(User user){
        if(findByUsername(user.getUsername()).isPresent()){
            throw new DataIsAlreadyExistException("user with username "+user.getUsername()+" is exist");
        }
        else if(((UserRepository)repository).findUserByEmail(user.getEmail()).isPresent()){
            throw new DataIsAlreadyExistException("user with email "+user.getEmail()+" is exist");
        } else if (((UserRepository) repository).findUserByGoogleId(user.getGoogleId()).isPresent()){
            throw new DataIsAlreadyExistException("user with googleId "+user.getGoogleId()+" is exist");
        }
    }
    public boolean activateUser(String code) {
        User user = ((UserRepository)repository).findByActivationCode(code).orElseThrow(NotFoundException::new);
        user.setActivationCode(null);
        user.setActive(true);
        repository.save(user);
        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to congenial-doodle. Please, visit next link: http://%s/activate/%s",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    private void setActivationCode(User user){
        if(StringUtils.isEmpty(user.getActivationCode())||user.getActivationCode().isBlank()){
            user.setActivationCode(UUID.randomUUID().toString());
        }
    }

    private void setPassword(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

}
