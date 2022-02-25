package com.dlcode.minhasfinancas.model.repositories;

import com.dlcode.minhasfinancas.model.entities.Usuario;
import com.dlcode.minhasfinancas.service.UsuarioService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Ela serve para saobreescrever qualquer conrfiguração que eu tenha feito no ambiente de teste e ele nao desconfigura o banco de dados. Ele cria uma instancia propria no banco de testes da memoria
@DataJpaTest //Ela cria uma instancia no banco de dados da memoria e ao finalizar a bateria de testes ela deleta da memoria.
@ExtendWith(SpringExtension.class)
@ActiveProfiles("application-test") //Com essa notação ele vai procurar o application-test e carregar as nossas confgurações para fazer os testes
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    UsuarioService usuarioService;

    //Todos o métodos de teste sao do tipo void
    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
    //Cenário
    Usuario usuario = Usuario.builder().nome("Usuário").email("usuario@email.com").build();
    repository.save(usuario);
    //Ação/Execução
    boolean result = repository.existsByEmail("usuario@email.com");
    //Verificação
    Assertions.assertThat(result).isTrue(); //verificando se o retorno desses dados é verdadeiro ou falso.
    }

    //Verificar se nao existe nenhum usuario com o email passado.
    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        //cenario
        repository.deleteAll(); //para garantir que a base de dados esta limpa

        //ação
        boolean result = repository.existsByEmail("usuario@email.com");

        //verificação
        Assertions.assertThat(result).isFalse();

    }
}
