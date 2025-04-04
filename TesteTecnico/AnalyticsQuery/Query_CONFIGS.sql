/*
	A importação dos dados para as tabelas foi feita manualmente através deste sgbd, clicando com o botão direito e indo em import,
	Para isso ser possivel precisei mudar alguns dados das colunas e estruturar os arquivos para a importação dentro do c:\ para não enfrentar erros de
	permissão
*/

-- Cria o Banco de dados
CREATE DATABASE db_Analytics;

-- Cria a primeira tabela
CREATE TABLE cadop (
    Registro_ANS INTEGER,
    CNPJ CHAR(14),
    Razao_Social VARCHAR(255),
    Nome_Fantasia VARCHAR(255),
    Modalidade VARCHAR(100),
    Logradouro VARCHAR(255),
    Numero VARCHAR(20),
    Complemento VARCHAR(100),
    Bairro VARCHAR(100),
    Cidade VARCHAR(100),
    UF CHAR(2),
    CEP VARCHAR(9),
    DDD CHAR(2),
    Telefone VARCHAR(15),
    Fax VARCHAR(15),
    Endereco_eletronico VARCHAR(255),
    Representante VARCHAR(100),
    Cargo_Representante VARCHAR(100),
    Regiao_de_Comercializacao VARCHAR(100),
    Data_Registro_ANS DATE
);

-- Cria a segunda tabela
CREATE TABLE demonstracoes_contabeis (
    periodo DATE,
    REG_ANS VARCHAR(50),
    CD_CONTA_CONTABIL VARCHAR(20),
    DESCRICAO VARCHAR(255),
    VL_SALDO_INICIAL NUMERIC(15, 2),
    VL_SALDO_FINAL NUMERIC(15, 2)
);

