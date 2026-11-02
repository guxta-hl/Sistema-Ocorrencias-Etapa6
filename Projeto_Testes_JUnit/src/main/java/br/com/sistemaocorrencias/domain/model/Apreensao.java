package br.com.sistemaocorrencias.domain.model;

/** Representa uma apreensão vinculada a uma ocorrência. */
public class Apreensao {
    private final Integer id;
    private final String tipo;
    private final String descricao;

    public Apreensao(String tipo, String descricao) {
        this(null, tipo, descricao);
    }

    public Apreensao(Integer id, String tipo, String descricao) {
        this.id = id;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }
}
