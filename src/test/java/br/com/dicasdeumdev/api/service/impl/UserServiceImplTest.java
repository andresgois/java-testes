package br.com.dicasdeumdev.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.dicasdeumdev.api.service.exceptions.DataIntegratyViolationException;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

    @Test
    void whenFindAllThenReturnAnListOfUsers(){
        List<User> users = new ArrayList<>();
        users.add(user);
        when(repository.findAll()).thenReturn(users);

        List<User> response = service.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(0).getClass());
        assertEquals(ID, response.get(0).getId());
    }

    @Test
    void whenCreateThenReturnSucess(){
        when(repository.save(any())).thenReturn(user);

        User response = service.create(userDto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void whenCreateThenReturnAnDataIntegratyViolationException(){
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.create(userDto);
        } catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSucess(){
        when(repository.save(any())).thenReturn(user);

        User response = service.update(userDto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void whenUpdateThenReturnAnDataIntegratyViolationException(){
        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.create(userDto);
        } catch (Exception ex){
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void deleteWithSucess(){
        when(repository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyInt());
        service.delete(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithObjectNotFoundException(){
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try {
            service.delete(ID);
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
