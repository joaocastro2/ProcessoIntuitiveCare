from flask import Flask, request, jsonify
from flask_cors import CORS
import psycopg2

app = Flask(__name__)
CORS(app)

# Configuração da conexão com o banco de dados PostgreSQL
conn = psycopg2.connect(
    host="localhost",
    database="db_CrudPythonVue",
    user="postgres",
    password="123321"
)

@app.route('/buscar', methods=['GET'])
def buscar_operadoras():
    # Obter parâmetros enviados pelo frontend
    coluna = request.args.get('coluna', '').lower()  # Nome da coluna selecionada
    termo = request.args.get('termo', '').lower()  # Termo de busca

    # Validar a coluna escolhida para evitar SQL Injection
    colunas_permitidas = ['registro_ans', 'cnpj', 'nome_fantasia']
    if coluna not in colunas_permitidas:
        return jsonify({"error": "Coluna inválida. Escolha: registro_ans, cnpj, ou nome_fantasia"}), 400

    try:
        # Preparar o cursor para a consulta SQL
        cursor = conn.cursor()

        # Configurar a query com base no tipo da coluna
        if coluna == 'registro_ans':
            # Coluna numérica precisa ser convertida para texto para usar LIKE
            query = f"SELECT * FROM relatorio_cadop WHERE CAST({coluna} AS TEXT) LIKE %s"
        else:
            # Colunas textuais usam LOWER para busca insensível a maiúsculas/minúsculas
            query = f"SELECT * FROM relatorio_cadop WHERE LOWER({coluna}) LIKE %s"

        # Executar a query
        cursor.execute(query, (f"%{termo}%",))
        resultados = cursor.fetchall()

        # Obter os nomes das colunas da tabela
        colunas = [desc[0] for desc in cursor.description]

        # Formatar os resultados como uma lista de dicionários
        resultados_json = [dict(zip(colunas, row)) for row in resultados]

        # Fechar o cursor
        cursor.close()

        # Retornar os resultados como JSON
        return jsonify(resultados_json)

    except Exception as e:
        # Retornar mensagem de erro caso algo dê errado
        return jsonify({"error": str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True)
