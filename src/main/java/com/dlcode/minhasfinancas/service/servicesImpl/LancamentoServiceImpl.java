package com.dlcode.minhasfinancas.service.servicesImpl;

import com.dlcode.minhasfinancas.model.entities.Lancamento;
import com.dlcode.minhasfinancas.model.enums.StatusLancamento;
import com.dlcode.minhasfinancas.model.repositories.LancamentoRepository;
import com.dlcode.minhasfinancas.service.LancamentoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    } //Injeção de Dependência

    @Override
    public Lancamento salvar(Lancamento lancamento) {
        return null;
    }

    @Override
    public Lancamento atualizar(Lancamento lancamento) {
        return null;
    }

    @Override
    public void deletar(Lancamento lancamento) {

    }

    @Override
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        return null;
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {

    }
}
