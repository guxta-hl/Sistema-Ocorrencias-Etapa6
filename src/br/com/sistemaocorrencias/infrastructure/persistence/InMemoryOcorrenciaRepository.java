package br.com.sistemaocorrencias.infrastructure.persistence;

import br.com.sistemaocorrencias.domain.model.Apreensao;
import br.com.sistemaocorrencias.domain.model.Ocorrencia;
import br.com.sistemaocorrencias.domain.repository.OcorrenciaRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Repositorio em memoria, utilizado nos testes sem depender do MySQL. */
public class InMemoryOcorrenciaRepository implements OcorrenciaRepository {
    private final List<Ocorrencia> dados = new ArrayList<Ocorrencia>();
    private long proximoId = 1;

    @Override
    public synchronized long salvar(Ocorrencia ocorrencia) {
        long id = proximoId++;
        Apreensao original = ocorrencia.getApreensao();
        Apreensao copiaApreensao = original == null ? null
                : new Apreensao(original.getTipo(), original.getDescricao());
        dados.add(new Ocorrencia((int) id, ocorrencia.getNumero(),
                ocorrencia.getAno(), copiaApreensao));
        return id;
    }

    @Override
    public synchronized List<Ocorrencia> listarTodas() {
        return Collections.unmodifiableList(new ArrayList<Ocorrencia>(dados));
    }
}
