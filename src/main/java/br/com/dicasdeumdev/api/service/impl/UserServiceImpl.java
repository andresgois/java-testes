package br.com.dicasdeumdev.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repository.UserRepository;
import br.com.dicasdeumdev.api.service.IUserService;
import br.com.dicasdeumdev.api.service.exceptions.DataIntegratyViolationException;
import br.com.dicasdeumdev.api.service.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImpl implements IUserService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    private ModelMapper mapper;
    
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

    @Override
    public User create(UserDTO user) {
        findByEmail(user);
        User nUser = mapper.map(user, User.class);
        return userRepository.save(nUser);
    }
    
    public void findByEmail(UserDTO user) {
        Optional<User> u = userRepository.findByEmail(user.getEmail());
        if(u.isPresent() && !u.get().getId().equals(user.getId())) {
            throw new DataIntegratyViolationException("E-mail já cadastrado no sistema");
        }
    }

    @Override
    public User update(UserDTO user) {
        findByEmail(user);
        User nUser = mapper.map(user, User.class);
        return userRepository.save(nUser);
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }
    
}
