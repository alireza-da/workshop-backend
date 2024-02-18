package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.UserRepository;
import com.workshop.workshopproject.entity.User;
import com.workshop.workshopproject.rest.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String theId) throws CustomException {
        Optional<User> result = userRepository.findById(theId);

        User theUser = null;

        if (result.isPresent()) {
            theUser = result.get();
        }
        else {
            // we didn't find the employee
            throw new CustomException("Did not find user id - " + theId);
        }

        return theUser;
    }

    @Override
    public void save(User theUser) {
//        if (userRepository.existsByUsername(theUser.getUsername())){
//            System.out.println(userRepository.findByUsername(theUser.getUsername()).getFirstName());
//            throw new ValidationException("Username already existed");
//        }
        theUser.setPassword(new BCryptPasswordEncoder().encode(theUser.getPassword()));
        userRepository.save(theUser);
    }

    @Override
    public void deleteById(String theId) {
        userRepository.deleteById(theId);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public User findByOauthId(String oauthId){
        List<User> users = userRepository.findAll();
        Collections.reverse(users);
        for (User u:users
        ) {
            if (u.getOauthId()==null){
                continue;
            }
            if (u.getOauthId().equals(oauthId)){
                return u;
            }
            if (u == null){
                continue;
            }

        }

        return null;
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
