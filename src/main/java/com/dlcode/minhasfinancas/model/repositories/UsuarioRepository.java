package com.dlcode.minhasfinancas.model.repositories;

import com.dlcode.minhasfinancas.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Optional<Usuario> findByEmail(String email);//é optional porque pode existir, ou não.
    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

}
