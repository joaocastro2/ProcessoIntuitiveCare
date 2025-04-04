/*
	Retorna as 10 maiores empresas com maiores gastos em"EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR"
	do último trimestre PRESENTE NA TABELA
*/

WITH ultimos_meses_disponiveis AS (
    SELECT DISTINCT DATE_TRUNC('month', periodo) AS ano
    FROM demonstracoes_contabeis
    ORDER BY ano DESC
    LIMIT 3
)
SELECT 
    c.nome_fantasia AS Operadora,
    c.razao_social AS Razao_Social,
    d.descricao AS Descricao,
    d.periodo AS Data,
    SUM(d.vl_saldo_final) AS Total_Despesas
FROM 
    cadop c
JOIN 
    demonstracoes_contabeis d
ON 
    c.registro_ans = d.reg_ans
WHERE 
    (d.DESCRICAO ILIKE '%EVENTOS%' OR
     d.DESCRICAO ILIKE '%Sinistros conhecidos%' OR
     d.DESCRICAO ILIKE '%Assistência médico hospitalar%')
    AND DATE_TRUNC('month', d.periodo) IN (SELECT ano FROM ultimos_meses_disponiveis)
GROUP BY 
    c.nome_fantasia, c.razao_social, d.descricao, d.periodo
ORDER BY 
    d.periodo ASC, -- Ordena pela data em ordem crescente
    Total_Despesas DESC -- Ordena pelo total de despesas dentro da mesma data
LIMIT 10;


/*
	Retorna as 10 maiores empresas com maiores gastos em"EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR"
	do último ano PRESENTE NA TABELA
*/

WITH ultimos_meses_disponiveis AS (
    SELECT DISTINCT DATE_TRUNC('month', periodo) AS ano
    FROM demonstracoes_contabeis
    ORDER BY ano DESC
    LIMIT 12
)
SELECT 
    c.nome_fantasia AS Operadora,
    c.razao_social AS Razao_Social,
    d.descricao AS Descricao,
    d.periodo AS Data,
    SUM(d.vl_saldo_final) AS Total_Despesas
FROM 
    cadop c
JOIN 
    demonstracoes_contabeis d
ON 
    c.registro_ans = d.reg_ans
WHERE 
    (d.DESCRICAO ILIKE '%EVENTOS%' OR
     d.DESCRICAO ILIKE '%Sinistros conhecidos%' OR
     d.DESCRICAO ILIKE '%Assistência médico hospitalar%')
    AND DATE_TRUNC('month', d.periodo) IN (SELECT ano FROM ultimos_meses_disponiveis)
GROUP BY 
    c.nome_fantasia, c.razao_social, d.descricao, d.periodo
ORDER BY 
    d.periodo ASC, -- Ordena pela data em ordem crescente
    Total_Despesas DESC -- Ordena pelo total de despesas dentro da mesma data
LIMIT 10;

