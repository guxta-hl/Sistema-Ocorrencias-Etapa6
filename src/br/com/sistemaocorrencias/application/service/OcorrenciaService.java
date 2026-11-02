package br.com.sistemaocorrencias.application.service;

import br.com.sistemaocorrencias.domain.exception.RegraNegocioException;
import br.com.sistemaocorrencias.domain.model.Apreensao;
import br.com.sistemaocorrencias.domain.model.Ocorrencia;
import br.com.sistemaocorrencias.domain.repository.OcorrenciaRepository;
import java.util.List;

/** Centraliza os casos de uso e as regras de negocio de ocorrencias. */
public class OcorrenciaService {
    private final OcorrenciaRepository repository;

    public OcorrenciaService(OcorrenciaRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("O repositorio deve ser informado.");
        }
        this.repository = repository;
    }

    public long registrar(int numero, int ano, String tipoApreensao, String descricaoApreensao) {
        validarDados(numero, ano, tipoApreensao, descricaoApreensao);

        Apreensao apreensao = new Apreensao(
                tipoApreensao.trim(), descricaoApreensao.trim());
        Ocorrencia ocorrencia = new Ocorrencia(numero, ano, apreensao);
        return repository.salvar(ocorrencia);
    }

    public List<Ocorrencia> listar() {
        return repository.listarTodas();
    }

    private void validarDados(int numero, int ano, String tipo, String descricao) {
        if (numero <= 0) {
            throw new RegraNegocioException("O numero da ocorrencia deve ser maior que zero.");
        }
        if (ano <= 0) {
            throw new RegraNegocioException("O ano deve ser maior que zero.");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new RegraNegocioException("Informe o tipo da apreensao.");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new RegraNegocioException("Informe a descricao da apreensao.");
        }
    }
}
