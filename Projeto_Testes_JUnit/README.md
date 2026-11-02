# Projeto de Testes JUnit — Etapa 7

Projeto Maven compatível com Apache NetBeans. Os testes usam JUnit 4.13.2 e a implementação
`InMemoryOcorrenciaRepository`, portanto não precisam de MySQL.

## Abrir no NetBeans
1. Extraia o pacote.
2. No NetBeans, escolha **File > Open Project**.
3. Selecione a pasta `Projeto_Testes_JUnit` (a que contém `pom.xml`).
4. Aguarde o Maven resolver a dependência JUnit.

## Executar
- No NetBeans: botão direito no projeto > **Test**.
- No terminal, dentro desta pasta: `mvn test`.

A primeira execução precisa de internet para baixar JUnit e os plugins Maven, caso ainda não estejam
em cache.

## O que os testes verificam
- Cadastro válido e retorno do identificador.
- Preservação dos dados e remoção de espaços externos.
- Rejeição de número e ano iguais a zero.
- Rejeição de tipo nulo ou em branco.
- Rejeição de descrição em branco.
- Listagem de todos os registros.
- Geração sequencial de identificadores.

Os arquivos de domínio, serviço e repositório em `src/main/java` são cópias fiéis das classes
correspondentes da Etapa 6, para que este projeto de testes possa ser aberto e executado
independentemente.
