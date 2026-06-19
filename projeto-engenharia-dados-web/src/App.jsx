import { useState, useEffect } from 'react';
import UsuarioForm from './components/UsuarioForm.jsx';
import TabelaRelacional from './components/TabelaRelacional.jsx';
import TabelaNoSql from './components/TabelaNoSql.jsx';

export default function App() {
  const [usuariosPostgres, setUsuariosPostgres] = useState([]);
  const [usuariosMongo, setUsuariosMongo] = useState([]);

  // Função para buscar dados do PostgreSQL
  const carregarPostgres = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/v1/usuarios/relacional');
      const data = await res.json();
      setUsuariosPostgres(data);
    } catch (err) {
      console.error("Erro ao buscar Postgres:", err);
    }
  };

  // Função para buscar dados do MongoDB
  const carregarMongo = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/v1/usuarios/nosql');
      const data = await res.json();
      setUsuariosMongo(data);
    } catch (err) {
      console.error("Erro ao buscar Mongo:", err);
    }
  };

  // Carrega os dados assim que a página abre
  useEffect(() => {
    carregarPostgres();
    carregarMongo();
  }, []);

  // Força a atualização de ambas as tabelas após um novo cadastro
  const atualizarTelas = () => {
    carregarPostgres();
    carregarMongo();
  };

  return (
      <div style={{ fontFamily: 'sans-serif', maxWidth: '1200px', margin: '0 auto', padding: '20px' }}>
        <header style={{ textAlign: 'center', marginBottom: '30px' }}>
          <h2>Painel de Persistência Poliglota</h2>
          <p>Demonstração Prática de Engenharia de Dados - UFS</p>
        </header>

        <UsuarioForm onUsuarioCadastrado={atualizarTelas} />

        <main style={{ display: 'flex', gap: '20px', marginTop: '30px' }}>
          <TabelaRelacional usuarios={usuariosPostgres} />
          <TabelaNoSql usuarios={usuariosMongo} />
        </main>
      </div>
  );
}