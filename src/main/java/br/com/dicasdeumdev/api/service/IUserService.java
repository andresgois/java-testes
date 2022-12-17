package br.com.dicasdeumdev.api.service;

import java.util.List;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;

public interface IUserService {
    
    User findById(Integer id);
    List<User> findAll();
    User create(UserDTO user);
}
