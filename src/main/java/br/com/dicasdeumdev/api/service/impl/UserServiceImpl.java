package br.com.dicasdeumdev.api.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.repository.UserRepository;
import br.com.dicasdeumdev.api.service.IUserService;
import br.com.dicasdeumdev.api.service.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements IUserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public User findById(Integer id) {
        Optional<User> obj = userRepository.findById(id);
        return obj.orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
}
