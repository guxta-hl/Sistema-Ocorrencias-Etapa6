package br.com.sistemaocorrencias.infrastructure.persistence;

import br.com.sistemaocorrencias.domain.exception.RepositorioException;
import br.com.sistemaocorrencias.domain.model.Apreensao;
import br.com.sistemaocorrencias.domain.model.Ocorrencia;
import br.com.sistemaocorrencias.domain.repository.OcorrenciaRepository;
import br.com.sistemaocorrencias.infrastructure.config.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** Implementacao JDBC do contrato de repositorio. */
public class JdbcOcorrenciaRepository implements OcorrenciaRepository {

    @Override
    public long salvar(Ocorrencia ocorrencia) {
        final String sqlOcorrencia =
                "INSERT INTO ocorrencia (numeroOc, ano) VALUES (?, ?)";
        final String sqlApreensao =
                "INSERT INTO apreensao (descricao, tipo, ocorrencia_id) VALUES (?, ?, ?)";

        try (Connection conexao = ConnectionFactory.obterConexao()) {
            conexao.setAutoCommit(false);
            try {
                long idOcorrencia;
                try (PreparedStatement comando = conexao.prepareStatement(
                        sqlOcorrencia, Statement.RETURN_GENERATED_KEYS)) {
                    comando.setInt(1, ocorrencia.getNumero());
                    comando.setInt(2, ocorrencia.getAno());
                    comando.executeUpdate();
                    try (ResultSet chaves = comando.getGeneratedKeys()) {
                        if (!chaves.next()) {
                            throw new SQLException("O banco nao retornou o ID da ocorrencia.");
                        }
                        idOcorrencia = chaves.getLong(1);
                    }
                }

                Apreensao apreensao = ocorrencia.getApreensao();
                try (PreparedStatement comando = conexao.prepareStatement(sqlApreensao)) {
                    comando.setString(1, apreensao.getDescricao());
                    comando.setString(2, apreensao.getTipo());
                    comando.setLong(3, idOcorrencia);
                    comando.executeUpdate();
                }

                conexao.commit();
                return idOcorrencia;
            } catch (SQLException erro) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    erro.addSuppressed(erroRollback);
                }
                throw erro;
            }
        } catch (SQLException erro) {
            throw new RepositorioException(
                    "Nao foi possivel salvar a ocorrencia e sua apreensao.", erro);
        }
    }

    @Override
    public List<Ocorrencia> listarTodas() {
        String sql = "SELECT o.id, o.numeroOc, o.ano, "
                + "a.id AS apreensao_id, a.tipo AS tipo_apreensao, "
                + "a.descricao AS descricao_apreensao "
                + "FROM ocorrencia o "
                + "LEFT JOIN apreensao a ON a.ocorrencia_id = o.id "
                + "ORDER BY o.id";
        List<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();

        try (Connection conexao = ConnectionFactory.obterConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                int apreensaoId = resultado.getInt("apreensao_id");
                Apreensao apreensao = resultado.wasNull() ? null
                        : new Apreensao(apreensaoId,
                                resultado.getString("tipo_apreensao"),
                                resultado.getString("descricao_apreensao"));

                ocorrencias.add(new Ocorrencia(
                        resultado.getInt("id"),
                        resultado.getInt("numeroOc"),
                        resultado.getInt("ano"),
                        apreensao));
            }
            return ocorrencias;
        } catch (SQLException erro) {
            throw new RepositorioException("Nao foi possivel listar as ocorrencias.", erro);
        }
    }
}
