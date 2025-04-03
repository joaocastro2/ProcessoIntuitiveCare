
# Tranformação de dados

[![My Skills](https://skillicons.dev/icons?i=java&theme=light)](https://skillicons.dev)

... Api para extrair dados de um arquivo pdf e gerar um arquivo .csv


## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  http://localhost:8080/extrair-e-baixar?caminhoPdf=<Caminho do arquivo PDF>
```

## O passo a passo do processo será:
Abrir e iterar um arquivo pdf posicionando cada tabela e coluna para gerar um csv.




## Dificuldades e soluções

No meio do caminho tive dificuldades como manter a fidelidade do arquivo pdf dentro do csv.
Ao fazer testes troquei a biblioteca que usei, troquei a linguagem e não consegui obter o resultado esperado e 100% fiel, me levando a acreditar que o problema estaria dentro do pdf, incapacitando a leitura e iteração pelas informações da tabela

Talvex, como solução, poderia testar um outro método ou linguagem que possua uma biblioteca com uma leitura mais robusta para manter toda a fidelidade das tabelas presentes no pdf.