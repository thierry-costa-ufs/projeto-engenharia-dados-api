import styles from './Tabelas.module.css';

export default function TabelaRelacional({ usuarios, onDeletar }) {
  const deletarUsuario = async (cpf) => {
    if (confirm(`Deseja remover o usuário com CPF ${cpf}?`)) {
      try {
        const res = await fetch(`http://localhost:8080/api/v1/usuarios/${cpf}`, { method: 'DELETE' });
        if (res.ok) {
          alert('Usuário removido!');
          onDeletar();
        }
      } catch (err) {
        console.error(err);
      }
    }
  };

  return (
    <div className={`${styles.card} ${styles.postgres}`}>
      <h4>PostgreSQL (Relacional)</h4>
      <table className={styles.table}>
        <thead>
          <tr>
            <th>CPF</th>
            <th>Nome</th>
            <th>E-mails</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {usuarios.map((u) => (
            <tr key={u.cpf}>
              <td>{u.cpf}</td>
              <td>{u.nome}</td>
              <td>{u.email?.join(', ')}</td>
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