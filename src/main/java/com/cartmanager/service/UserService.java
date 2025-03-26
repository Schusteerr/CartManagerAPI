package com.cartmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cartmanager.controller.CreateUserDto;
import com.cartmanager.controller.UpdateUserDto;
import com.cartmanager.entity.User;
import com.cartmanager.repository.UserRepository;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID CriarUsuario(CreateUserDto dto) {

        var entity = new User(
                UUID.randomUUID(),
                "interessado",
                dto.name(),
                dto.email(),
                dto.password()
        );

        entity.setCart(new ArrayList<>());

        var UsuarioSalvo = userRepository.save(entity);
        return UsuarioSalvo.getId();
    }


    public List<User> ListarUsuarios(){
        return userRepository.findAll();
    }

    public Optional<User> ListarPorId(String userId) {

        var id = UUID.fromString(userId);
        return userRepository.findById(id);

    }

    public void AtualizarUsuario(String userId, UpdateUserDto updateUserDto) {
        var id = UUID.fromString(userId);
        var UserEntity = userRepository.findById(id);

        if (UserEntity.isPresent()) {
            var user = UserEntity.get();

            if(updateUserDto.name() != null) {
                user.setName(updateUserDto.name());
            }
            if(updateUserDto.email() != null) {
                if (updateUserDto.email().contains("@")) {
                    user.setEmail(updateUserDto.email());
                }else{
                    log.error("Email invalido");
                }

            }
            if(updateUserDto.password() != null) {
                if (updateUserDto.password().equals(user.getPassword())) {
                    log.info("Sua senha nao pode ser igual a senha atual");
                }else{
                    user.setPassword(updateUserDto.password());
                }

            }

            userRepository.save(user);
            log.info("Usuário {} atualizado com sucesso.", user.getName());
        } else {
            log.error("Erro: Usuário com ID {} não encontrado.", userId);
        }
    }

    public void DeletarUsuario(String userId) {

        var id = UUID.fromString(userId);
        var UsuarioExiste = userRepository.existsById(id);

        if(UsuarioExiste){
            userRepository.deleteById(id);
        }else{
            log.error("Erro: Usuário com ID {} não encontrado.", userId);
        }

    }


}
