package br.com.dicasdeumdev.api.service.impl;

import java.util.Optional;

import br.com.dicasdeumdev.api.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    private static final int ID = 1;
    public static final String NAME = "Andre";
    public static final String EMAIL = "andre@email.com";
    public static final String PASSWORD = "123";
    // Criar uma instância real
    @InjectMocks    // cria uma instância real
    private UserServiceImpl service;
    @Mock           // fingi busca os dados
    private UserRepository repository;
    @Mock
    private ModelMapper mapper;
    private User user;
    private UserDTO userDto;
    private Optional<User> optionalUser;
    
    @BeforeEach // antes de tudo faça isso
    void setUp() {
        MockitoAnnotations.openMocks(this); // inicia os mocks
        startUser();
    }
    
    @Test
    void whenFindByIdThenReturnAnUserInstance() {
       when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
       User response = service.findById(ID);
       assertEquals(User.class, response.getClass());
       assertEquals(ID, response.getId());
       assertNotNull(response);
    }

    @Test
    void whenFindByIdTheReturnAnObjectNotFoundException(){
        when(repository.findById(ArgumentMatchers.anyInt()))
                .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try {
            service.findById(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }
    
    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional
                .of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}
