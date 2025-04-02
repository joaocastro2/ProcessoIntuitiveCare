<template>
  <div id="app">
    <h1>Busca de Operadoras</h1>

    <!-- Botões para selecionar a coluna -->
    <div>
      <button @click="selecionarColuna('registro_ans')" :class="{ ativo: coluna === 'registro_ans' }">Registro ANS</button>
      <button @click="selecionarColuna('cnpj')" :class="{ ativo: coluna === 'cnpj' }">CNPJ</button>
      <button @click="selecionarColuna('nome_fantasia')" :class="{ ativo: coluna === 'nome_fantasia' }">Nome Fantasia</button>
    </div>

    <!-- Campo para o termo de busca -->
    <input v-model="termo" placeholder="Digite o termo de busca" />
    <button @click="buscar">Buscar</button>

    <!-- Resultados -->
    <table border="1">
      <thead>
        <tr>
          <th v-for="(value, key) in operadoras[0]" :key="key">{{ key }}</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="operadora in operadoras" :key="operadora.id">
          <td v-for="(value, key) in operadora" :key="key">{{ value }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      coluna: '',        // Coluna selecionada
      termo: '',         // Termo de busca
      operadoras: []     // Resultados da busca
    };
  },
  methods: {
    selecionarColuna(coluna) {
      console.log(`Coluna selecionada: ${coluna}`);
      this.coluna = coluna; // Define a coluna selecionada
    },
    async buscar() {
      if (!this.coluna) {
        alert('Por favor, selecione uma coluna para a busca.');
        return;
      }

      try {
        const response = await axios.get('http://localhost:5000/buscar', {
          params: { coluna: this.coluna, termo: this.termo }
        });
        this.operadoras = response.data;
      } catch (error) {
        console.error('Erro ao buscar:', error);
      }
    }
  }
};
</script>

<style>
/* Estilos para os botões ativos */
button {
  margin: 5px;
  padding: 10px;
  cursor: pointer;
}
button.ativo {
  background-color: #007BFF;
  color: white;
}
table {
  margin-top: 20px;
  border-collapse: collapse;
  width: 100%;
}
th, td {
  padding: 10px;
  text-align: left;
}
</style>
