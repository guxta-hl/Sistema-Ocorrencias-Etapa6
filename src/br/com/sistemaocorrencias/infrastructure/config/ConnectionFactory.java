package br.com.sistemaocorrencias.infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Cria conexoes JDBC usando configuracoes externas ao codigo-fonte. */
public final class ConnectionFactory {
    private static final String URL_PADRAO =
            "jdbc:mysql://localhost:3306/sistema_ocorrenciaspi?serverTimezone=UTC";

    private ConnectionFactory() {
    }

    public static Connection obterConexao() throws SQLException {
        String url = obterConfiguracao("DB_URL", URL_PADRAO);
        String usuario = obterConfiguracao("DB_USER", "root");
        String senha = obterConfiguracao("DB_PASSWORD", "");
        return DriverManager.getConnection(url, usuario, senha);
    }

    private static String obterConfiguracao(String chave, String valorPadrao) {
        String valor = System.getProperty(chave);
        if (valor == null || valor.trim().isEmpty()) {
            valor = System.getenv(chave);
        }
        return (valor == null || valor.trim().isEmpty()) ? valorPadrao : valor;
    }
}
