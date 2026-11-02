package br.com.sistemaocorrencias.domain.model;

/** Entidade principal do sistema, associada a uma apreensão. */
public class Ocorrencia {
    private final Integer id;
    private final int numero;
    private final int ano;
    private final Apreensao apreensao;

    public Ocorrencia(int numero, int ano, Apreensao apreensao) {
        this(null, numero, ano, apreensao);
    }

    public Ocorrencia(Integer id, int numero, int ano, Apreensao apreensao) {
        this.id = id;
        this.numero = numero;
        this.ano = ano;
        this.apreensao = apreensao;
    }

    public Integer getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getAno() {
        return ano;
    }

    public Apreensao getApreensao() {
        return apreensao;
    }
}
