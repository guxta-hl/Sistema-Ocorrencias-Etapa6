package br.com.sistemaocorrencias.domain.repository;

import br.com.sistemaocorrencias.domain.model.Ocorrencia;
import java.util.List;

/** Contrato de persistencia; nao depende de MySQL ou de interface grafica. */
public interface OcorrenciaRepository {
    long salvar(Ocorrencia ocorrencia);
    List<Ocorrencia> listarTodas();
}
