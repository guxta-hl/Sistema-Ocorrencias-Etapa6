package br.com.sistemaocorrencias.app;

import br.com.sistemaocorrencias.application.service.OcorrenciaService;
import br.com.sistemaocorrencias.domain.exception.RegraNegocioException;
import br.com.sistemaocorrencias.domain.model.Ocorrencia;
import br.com.sistemaocorrencias.domain.repository.OcorrenciaRepository;
import br.com.sistemaocorrencias.infrastructure.persistence.InMemoryOcorrenciaRepository;
import br.com.sistemaocorrencias.infrastructure.persistence.JdbcOcorrenciaRepository;
import java.util.List;

/**
 * Ponto de entrada do projeto e verificacao automatizada solicitada na Etapa 6.
 * Os testes padrao usam memoria e nao dependem de um servidor MySQL.
 */
public class Main {

    public static void main(String[] args) {
        executarTestesBasicos();

        if ("true".equalsIgnoreCase(System.getenv("TESTAR_BANCO"))) {
            executarTesteLeituraBanco();
        } else {
            System.out.println("[INFO] Teste MySQL nao executado. Defina TESTAR_BANCO=true para ativa-lo.");
        }

        System.out.println("[OK] Verificacao finalizada.");
    }

    private static void executarTestesBasicos() {
        OcorrenciaRepository repositorio = new InMemoryOcorrenciaRepository();
        OcorrenciaService servico = new OcorrenciaService(repositorio);

        long id = servico.registrar(125, 2026, "Eletronicos", "Dois aparelhos celulares");
        verificar(id == 1L, "O primeiro ID gerado deve ser 1.");

        List<Ocorrencia> registros = servico.listar();
        verificar(registros.size() == 1, "A ocorrencia cadastrada deve aparecer na listagem.");
        verificar(registros.get(0).getNumero() == 125, "O numero da ocorrencia deve ser preservado.");
        verificar(registros.get(0).getAno() == 2026, "O ano da ocorrencia deve ser preservado.");
        verificar("Eletronicos".equals(registros.get(0).getApreensao().getTipo()),
                "O tipo da apreensao deve ser preservado.");
        verificar("Dois aparelhos celulares".equals(registros.get(0).getApreensao().getDescricao()),
                "A descricao da apreensao deve ser preservada.");

        verificarDadoInvalido(servico, 0, 2026, "Eletronicos", "Descricao valida",
                "Numero igual a zero deve ser rejeitado.");
        verificarDadoInvalido(servico, 126, 0, "Eletronicos", "Descricao valida",
                "Ano igual a zero deve ser rejeitado.");
        verificarDadoInvalido(servico, 126, 2026, " ", "Descricao valida",
                "Tipo vazio deve ser rejeitado.");
        verificarDadoInvalido(servico, 126, 2026, "Eletronicos", " ",
                "Descricao vazia deve ser rejeitada.");

        verificar(servico.listar().size() == 1,
                "Dados invalidos nao podem ser persistidos.");

        System.out.println("[OK] Cadastro, listagem, integridade dos dados e validacoes passaram.");
    }

    private static void verificarDadoInvalido(OcorrenciaService servico, int numero,
            int ano, String tipo, String descricao, String mensagemFalha) {
        boolean rejeitou = false;
        try {
            servico.registrar(numero, ano, tipo, descricao);
        } catch (RegraNegocioException esperado) {
            rejeitou = true;
        }
        verificar(rejeitou, mensagemFalha);
    }

    private static void executarTesteLeituraBanco() {
        OcorrenciaService servico = new OcorrenciaService(new JdbcOcorrenciaRepository());
        List<Ocorrencia> ocorrencias = servico.listar();
        System.out.println("[OK] Leitura MySQL realizada. Registros encontrados: " + ocorrencias.size());
    }

    private static void verificar(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new IllegalStateException("[FALHA] " + mensagem);
        }
    }
}
