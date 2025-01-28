package com.github.JoseAngelGiron.model.entity;


import com.github.JoseAngelGiron.model.services.UsuarioServices;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UserTest {


    @Test
    void createUserTest() {
        Usuario usuario = new Usuario();
        usuario.setNombre("JoseAngelGiron");
        usuario.setContrasena("123456");
        usuario.setEmail("joseangelgiron@gmail.com");
        usuario.setFechaRegistro(LocalDate.now());

        UsuarioServices usuarioServices = new UsuarioServices();
        boolean inserted = usuarioServices.save(usuario);

        if(inserted) {
            System.out.println("Usuario registrado con éxito");
        }else{
            System.out.println("Usuario no registrado");
        }

    }

    @Test
    void updateUserTest() {
        UsuarioServices usuarioServices = new UsuarioServices();
        Usuario retrievedUser =usuarioServices.findById(8);
        System.out.println(retrievedUser);
        if(retrievedUser != null) {
            retrievedUser.setNombre("Pepe");
            retrievedUser.setContrasena("5678");
            usuarioServices.update(retrievedUser);
        }


    }

    @Test
    void deleteUserTest() {
        UsuarioServices usuarioServices = new UsuarioServices();
        boolean deleted = usuarioServices.delete(8);
        System.out.println(deleted);
    }
}
