# Sistema de Ocorrências e Apreensões — Etapa 6

Projeto Java organizado em camadas para separar regras de negócio, contratos de persistência e infraestrutura JDBC. O núcleo de negócio não depende de Java Swing e pode ser reutilizado por uma futura camada web.

## Tecnologias
- Java 17 (o código usa construções compatíveis com Java 8)
- Apache Ant / NetBeans
- JDBC / MySQL

## Estrutura
- `domain/model`: entidades `Ocorrencia` e `Apreensao`.
- `domain/repository`: contrato `OcorrenciaRepository`.
- `application/service`: casos de uso e validações em `OcorrenciaService`.
- `infrastructure/persistence`: implementações JDBC e em memória.
- `infrastructure/config`: criação da conexão JDBC.
- `app/Main.java`: testes básicos de cadastro, consulta e validação.
- `sql/schema.sql`: estrutura do banco.

## Abrir no NetBeans
1. Extraia o pacote.
2. No NetBeans, selecione **File > Open Project**.
3. Escolha a pasta `Projeto_NetBeans_Etapa6` (ou a pasta do projeto se estiver isolada).
4. Execute `br.com.sistemaocorrencias.app.Main`.

## Testes
Ao executar `Main`, os testes padrão usam `InMemoryOcorrenciaRepository`, portanto não exigem MySQL. São verificados cadastro, listagem, preservação de campos e rejeição de número/ano inválidos e campos em branco.

Para testar a leitura no MySQL, execute `sql/schema.sql`, configure as variáveis abaixo e defina `TESTAR_BANCO=true` no ambiente antes de executar.

- `DB_URL` — padrão: `jdbc:mysql://localhost:3306/sistema_ocorrenciaspi?serverTimezone=UTC`
- `DB_USER` — padrão: `root`
- `DB_PASSWORD` — senha do seu ambiente (não é armazenada no fonte)
- `TESTAR_BANCO` — `true` para ativar o teste de leitura MySQL

## GitHub
Crie um repositório vazio em sua conta GitHub e execute os comandos abaixo no terminal, dentro da pasta do projeto:

```bash
git init
git add .
git commit -m "Etapa 6 - refatoracao do sistema de ocorrencias"
git branch -M main
git remote add origin https://github.com/SEU-USUARIO/SEU-REPOSITORIO.git
git push -u origin main
```

Substitua a URL pelo endereço real do repositório. Depois, inclua no relatório a URL e capturas de tela reais da página inicial, da árvore de arquivos e dos commits.

**Segurança:** nunca versione a senha do MySQL. Configure `DB_PASSWORD` no sistema operacional/IDE. A cópia do projeto desktop incluída na entrega também teve a senha fixa removida e substituída por configuração externa.
