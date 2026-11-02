package br.com.sistemaocorrencias.domain.exception;

/** Encapsula falhas ocorridas durante a persistencia dos dados. */
public class RepositorioException extends RuntimeException {
    public RepositorioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
