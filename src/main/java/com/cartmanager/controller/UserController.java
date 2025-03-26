package com.cartmanager.controller;


import com.cartmanager.entity.User;
import com.cartmanager.service.UserService;
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
        var userId = userService.CriarUsuario(dto);
        return ResponseEntity.created(URI.create("/userpage/" + userId)).body("Usuário " + dto.name() + " criado com sucesso. Seu ID é: "+userId);
    }

    @GetMapping
    public ResponseEntity<List<User>> ListarUsuarios() {
        var usuarios = userService.ListarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> ListarPorId(@PathVariable("userId") String userId) {

        var user = userService.ListarPorId(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> AtualizarUsuario(@PathVariable("userId") String userId, @RequestBody UpdateUserDto UpdateUserDto) {
        userService.AtualizarUsuario(userId, UpdateUserDto);
        return ResponseEntity.ok("Usuário atualizado com sucesso.");
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> DeletarUsuario(@PathVariable("userId") String userId) {
        userService.DeletarUsuario(userId);
        return ResponseEntity.ok("Usuario '" + userId + "' deletado com sucesso.");
    }


}
