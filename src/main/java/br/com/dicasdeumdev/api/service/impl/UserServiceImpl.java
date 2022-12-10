package br.com.dicasdeumdev.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.repository.UserRepository;
import br.com.dicasdeumdev.api.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    
    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElse(null);
    }
    
}
