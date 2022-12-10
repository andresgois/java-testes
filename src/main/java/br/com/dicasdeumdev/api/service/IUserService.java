package br.com.dicasdeumdev.api.service;

import br.com.dicasdeumdev.api.domain.User;

public interface IUserService {
    
    User findById(Integer id);
}
