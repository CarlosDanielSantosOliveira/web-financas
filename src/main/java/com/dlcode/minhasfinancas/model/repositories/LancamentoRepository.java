package com.dlcode.minhasfinancas.model.repositories;

import com.dlcode.minhasfinancas.model.entities.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
