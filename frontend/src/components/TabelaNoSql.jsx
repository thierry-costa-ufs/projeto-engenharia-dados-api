import styles from './Tabelas.module.css';

export default function TabelaNoSql({ usuarios, onDeletar }) {
  const deletarUsuario = async (cpf) => {
    if (confirm(`Remover do MongoDB (e consequentemente sincronizar)?`)) {
      try {
        const res = await fetch(`http://localhost:8080/api/v1/usuarios/${cpf}`, { method: 'DELETE' });
        if (res.ok) {
          onDeletar();
        }
      } catch (err) {
        console.error(err);
      }
    }
  };

  return (
    <div className={`${styles.card} ${styles.mongo}`}>
      <h4>MongoDB (NoSQL)</h4>
      <table className={styles.table}>
        <thead>
          <tr>
            <th>ID Mongo (Hex)</th>
            <th>CPF Origem</th>
            <th>Nome</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((u) => (
            <tr key={u.mongoId || u.cpf}>
              <td className={styles.hexId}>{u.mongoId || 'N/A'}</td>
              <td>{u.cpf}</td>
              <td>{u.nome}</td>
              <td>
                <button onClick={() => deletarUsuario(u.cpf)} className={styles.btnDelete}>Deletar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}