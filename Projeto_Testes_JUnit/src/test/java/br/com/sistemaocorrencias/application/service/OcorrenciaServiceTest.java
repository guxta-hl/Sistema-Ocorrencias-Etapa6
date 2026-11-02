package br.com.sistemaocorrencias.application.service;

import br.com.sistemaocorrencias.domain.exception.RegraNegocioException;
import br.com.sistemaocorrencias.domain.model.Ocorrencia;
import br.com.sistemaocorrencias.infrastructure.persistence.InMemoryOcorrenciaRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OcorrenciaServiceTest {

    private InMemoryOcorrenciaRepository repository;
    private OcorrenciaService service;

    @Before
    public void setUp() {
        repository = new InMemoryOcorrenciaRepository();
        service = new OcorrenciaService(repository);
    }

    @Test
    public void deveRegistrarOcorrenciaValida() {
        long id = service.registrar(125, 2026, "Eletronicos", "Dois aparelhos celulares");

        assertEquals(1L, id);
        assertEquals(1, service.listar().size());
    }

    @Test
    public void devePreservarDadosEAplicarTrimNoCadastro() {
        service.registrar(125, 2026, "  Eletronicos  ", "  Dois celulares  ");

        Ocorrencia ocorrencia = service.listar().get(0);
        assertEquals(125, ocorrencia.getNumero());
        assertEquals(2026, ocorrencia.getAno());
        assertEquals("Eletronicos", ocorrencia.getApreensao().getTipo());
        assertEquals("Dois celulares", ocorrencia.getApreensao().getDescricao());
    }

    @Test
    public void deveRejeitarNumeroZero() {
        assertThrows(RegraNegocioException.class,
                () -> service.registrar(0, 2026, "Eletronicos", "Descricao valida"));
        assertTrue(service.listar().isEmpty());
    }

    @Test
    public void deveRejeitarAnoZero() {
        assertThrows(RegraNegocioException.class,
                () -> service.registrar(125, 0, "Eletronicos", "Descricao valida"));
        assertTrue(service.listar().isEmpty());
    }

    @Test
    public void deveRejeitarTipoEmBranco() {
        assertThrows(RegraNegocioException.class,
                () -> service.registrar(125, 2026, "   ", "Descricao valida"));
        assertTrue(service.listar().isEmpty());
    }

    @Test
    public void deveRejeitarTipoNulo() {
        assertThrows(RegraNegocioException.class,
                () -> service.registrar(125, 2026, null, "Descricao valida"));
        assertTrue(service.listar().isEmpty());
    }

    @Test
    public void deveRejeitarDescricaoEmBranco() {
        assertThrows(RegraNegocioException.class,
                () -> service.registrar(125, 2026, "Eletronicos", "   "));
        assertTrue(service.listar().isEmpty());
    }

    @Test
    public void deveListarTodosOsRegistrosCadastrados() {
        service.registrar(125, 2026, "Eletronicos", "Dois celulares");
        service.registrar(126, 2026, "Documentos", "Documentos diversos");

        assertEquals(2, service.listar().size());
        assertEquals(125, service.listar().get(0).getNumero());
        assertEquals(126, service.listar().get(1).getNumero());
    }

    @Test
    public void deveGerarIdentificadoresSequenciais() {
        long primeiro = service.registrar(125, 2026, "Eletronicos", "Celular");
        long segundo = service.registrar(126, 2026, "Documentos", "Pasta");

        assertEquals(1L, primeiro);
        assertEquals(2L, segundo);
    }
}
