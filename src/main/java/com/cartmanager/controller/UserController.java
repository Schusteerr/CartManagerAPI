package com.cartmanager.controller;


import com.cartmanager.entity.User;
import com.cartmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/userpage")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> CriarUsuario(@RequestBody CreateUserDto dto) {
        try {
            var userId = userService.CriarUsuario(dto);
            return ResponseEntity.created(URI.create("/userpage/" + userId))
                    .body("Usuário " + dto.name() + " criado com sucesso. Seu ID é: " + userId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro de criação de usuário: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> ListarUsuarios() {
        try{
            var usuarios = userService.ListarUsuarios();
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> ListarPorId(@PathVariable("userId") String userId) {
        try {
            var user = userService.ListarPorId(userId);
            return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> UpdateUser(@PathVariable("userId") String userId, @RequestBody UpdateUserDto UpdateUserDto) {

        try{
            userService.UpdateUser(userId, UpdateUserDto);
            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao atualizar o usuário: " + e.getMessage());
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> DeleteUser(@PathVariable("userId") String userId) {

        try{
            userService.DeleteUser(userId);
            return ResponseEntity.ok("Usuário '" + userId + "' deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao deletar o usuário: " + e.getMessage());
        }

    }

}
