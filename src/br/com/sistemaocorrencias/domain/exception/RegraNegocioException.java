package br.com.sistemaocorrencias.domain.exception;

/** Excecao lancada quando os dados violam uma regra de negocio. */
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
