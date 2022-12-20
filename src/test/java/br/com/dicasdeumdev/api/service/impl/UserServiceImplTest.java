package br.com.dicasdeumdev.api.service.impl;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repository.UserRepository;

@SpringBootTest
public class UserServiceImplTest {
    
    private static final int ID = 1;
    // Criar uma instância real
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;
    private User user;
    private UserDTO userDto;
    private Optional<User> optionalUser;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }
    
    @Test
    void whenFindByIdThenReturnAnUserInstance() {
       Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
       User response = service.findById(ID);
       Assertions.assertEquals(User.class, response.getClass());
    }
    
    private void startUser() {
        user = new User(ID, "Andre", "andre@email.com", "123");
        userDto = new UserDTO(ID, "Andre", "andre@email.com", "123");
        optionalUser = Optional
                .of(new User(ID, "Andre", "andre@email.com", "123"));
    }
}
