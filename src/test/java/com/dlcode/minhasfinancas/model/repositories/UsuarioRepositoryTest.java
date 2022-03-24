package com.dlcode.minhasfinancas.model.repositories;

import com.dlcode.minhasfinancas.model.entities.Usuario;
import com.dlcode.minhasfinancas.service.UsuarioService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Ela serve para saobreescrever qualquer conrfiguração que eu tenha feito no ambiente de teste e ele nao desconfigura o banco de dados. Ele cria uma instancia propria no banco de testes da memoria
@DataJpaTest //Ela cria uma instancia no banco de dados da memoria e ao finalizar a bateria de testes ela deleta da memoria.
@ExtendWith(SpringExtension.class)
@ActiveProfiles("application-test") //Com essa notação ele vai procurar o application-test e carregar as nossas confgurações para fazer os testes
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager testEntityManager; //Classe responsável por efetuar operações na base de dados

    //Todos o métodos de teste sao do tipo void
    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
    //Cenário
    Usuario usuario = criarUsuario();
    testEntityManager.persist(usuario);

    //Ação/Execução
    boolean result = repository.existsByEmail("usuario@email.com");
    //Verificação
    Assertions.assertThat(result).isTrue(); //verificando se o retorno desses dados é verdadeiro ou falso.
    }

    //Verificar se nao existe nenhum usuario com o email passado.
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        //cenário

        //ação
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        //cenário
        Usuario usuario = criarUsuario();

        //acao
        Usuario usuarioSalvo = repository.save(usuario);

        //Verificação
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail() {
        //cenário
        Usuario usuario = criarUsuario();
        testEntityManager.persist(usuario); //Quando estamos utilizando o persist, a instância que estamos passando nos parâmetros, não pode ter id.

        //verificação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQUandoNaoExisteNaBase() {
        //cenário

        //verificação
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isFalse();

    }

    public static Usuario criarUsuario() {
        return Usuario
                .builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("senha")
                .build();
    }

}
