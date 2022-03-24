package com.dlcode.minhasfinancas.service;

import com.dlcode.minhasfinancas.exception.ErroAutenticacao;
import com.dlcode.minhasfinancas.exception.RegraNegocioException;
import com.dlcode.minhasfinancas.model.entities.Usuario;
import com.dlcode.minhasfinancas.model.repositories.UsuarioRepository;
import com.dlcode.minhasfinancas.service.servicesImpl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl usuarioService;
    @MockBean
    UsuarioRepository usuarioRepository;

    @Test(expected = Test.None.class)
    public void deveSalvarUmUsuario() {
        //cenario
        Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder()
                .id(1l)
                .nome("nome")
                .email("email@email.com")
                .senha("senha").build();

        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //acao
        Usuario usuarioSalvo = usuarioService.salvarUsuario(new Usuario());

        //verificação
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1l);
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("nome");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("email@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test(expected = RegraNegocioException.class)
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
        //cenario
        String email = "email@email.com";
        Usuario usuario = Usuario.builder().email("email@email.com").build();
        Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(email);

        //acao
        usuarioService.salvarUsuario(usuario);

        //verificacao
        Mockito.verify( usuarioRepository, Mockito.never() ).save(usuario);

    }

    @Test(expected = Test.None.class)
    public void deveAutenticarUmUsuarioComSucesso() {
        //cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //ação
        Usuario result = usuarioService.autenticar(email, senha);

        //verificação
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComoEmailInformado() {

        //cenário
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //acao
        Throwable exception = Assertions.catchThrowable( () -> usuarioService.autenticar("email@email.com", "senha") );

        //Verificacao
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Usuário não encontrado para o email informado.");
    }

    @Test
    public void deveLancarErroQuandoSenhaNaobater() {
        //cenario
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //acao
        Throwable exception = Assertions.catchThrowable( () -> usuarioService.autenticar("email@email.com", "123") ) ;
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida");
    }

    @Test(expected = Test.None.class) //QUando usamos essa classe None.class, o nosso teste vai esperar que a função nao lance nenhuma excessão.
    public void deveValidarEmail() {

        //cenario
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        usuarioRepository.deleteAll();

        //acao
        usuarioService.validarEmail("email@email.com");
    }

    @Test(expected = RegraNegocioException.class) //Aqui espero que ele lance a exception
    public void deveLancarErroQuandoExistirEmailCadastrado() {
        //cenario
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        //acao
        usuarioService.validarEmail("email@email.com");
    }

}
