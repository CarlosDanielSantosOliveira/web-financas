package com.dlcode.minhasfinancas.service;

import com.dlcode.minhasfinancas.exception.RegraNegocioException;
import com.dlcode.minhasfinancas.model.entities.Usuario;
import com.dlcode.minhasfinancas.model.repositories.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test(expected = Test.None.class) //QUando usamos essa classe None.class, o nosso teste vai esperar que a função nao lance nenhuma excessão.
    public void deveValidarEmail() {

        //cenario
        usuarioRepository.deleteAll();

        //acao
        usuarioService.validarEmail("email@email.com");
    }

    @Test(expected = RegraNegocioException.class) //Aqui espero que ele lance a exception
    public void deveLancarErroQuandoExistirEmailCadastrado() {
        //cenario
        Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
        usuarioRepository.save(usuario);

        //acao
                usuarioService.validarEmail("email@email.com");
    }

}
