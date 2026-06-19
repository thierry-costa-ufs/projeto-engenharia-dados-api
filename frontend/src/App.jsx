import { useState, useEffect } from 'react';
import UsuarioForm from './components/UsuarioForm.jsx';
import TabelaRelacional from './components/TabelaRelacional.jsx';
import TabelaNoSql from './components/TabelaNoSql.jsx';
import styles from './App.module.css';

export default function App() {
  const [usuariosPostgres, setUsuariosPostgres] = useState([]);
  const [usuariosMongo, setUsuariosMongo] = useState([]);

  const carregarPostgres = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/v1/usuarios/relacional');
      if (res.ok) setUsuariosPostgres(await res.json());
    } catch (err) {
      console.error("Erro ao buscar Postgres:", err);
    }
  };

  const carregarMongo = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/v1/usuarios/nosql');
      if (res.ok) setUsuariosMongo(await res.json());
    } catch (err) {
      console.error("Erro ao buscar Mongo:", err);
    }
  };

  useEffect(() => {
    carregarPostgres();
    carregarMongo();
  }, []);

  const atualizarTelas = () => {
    carregarPostgres();
    carregarMongo();
  };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <h1 className={styles.title}>Painel de Persistência Poliglota</h1>
        <p className={styles.subtitle}>Engenharia de Dados - UFS</p>
      </header>

      <UsuarioForm onUsuarioCadastrado={atualizarTelas} />

      <main className={styles.mainGrid}>
        <TabelaRelacional usuarios={usuariosPostgres} onDeletar={atualizarTelas} />
        <TabelaNoSql usuarios={usuariosMongo} onDeletar={atualizarTelas} />
      </main>
    </div>
  );
}