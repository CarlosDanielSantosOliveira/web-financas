package com.dlcode.minhasfinancas.service;

import com.dlcode.minhasfinancas.model.entities.Usuario;

public interface UsuarioService {
    //Nesta classe vamos definir os métodos para trabalhar com a entidade usuario.
    Usuario autenticar(String email, String senha);
    Usuario salvarUsuario(Usuario usuario);
    void validarEmail(String email);
}
