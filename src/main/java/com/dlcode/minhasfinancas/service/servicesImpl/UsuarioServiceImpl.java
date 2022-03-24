package com.dlcode.minhasfinancas.service.servicesImpl;

import com.dlcode.minhasfinancas.exception.ErroAutenticacao;
import com.dlcode.minhasfinancas.exception.RegraNegocioException;
import com.dlcode.minhasfinancas.model.entities.Usuario;
import com.dlcode.minhasfinancas.model.repositories.UsuarioRepository;
import com.dlcode.minhasfinancas.service.UsuarioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository; //para podermos acessar os metodos do JPA repository. É uma dependencia.

    //@Autowired //posso fazer a injeção de dependencia com essa notation
    public UsuarioServiceImpl(UsuarioRepository repository) { //gerenciado a dependencia pelo construtor
        this.repository = repository;
    } //e posso fazer dessa maneira tambem.

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
        }

        if(!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida");
        }

        return usuario.get();
    }

    @Override
    @Transactional //Vai criar no banco uma transação, vai executar o metodo de salvar o usuario e depois commitar
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if(existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email");
        }
    }

}
