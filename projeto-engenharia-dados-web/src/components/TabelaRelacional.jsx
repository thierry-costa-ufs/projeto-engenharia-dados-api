export default function TabelaRelacional({ usuarios }) {
    return (
        <div style={{ flex: 1, padding: '15px', border: '1px solid #3b82f6', borderRadius: '8px' }}>
            <h4 style={{ color: '#3b82f6' }}>🐘 PostgreSQL (Relacional)</h4>
            <table border="1" width="100%" style={{ borderCollapse: 'collapse', marginTop: '10px' }}>
                <thead>
                <tr>
                    <th>ID (Long)</th>
                    <th>Nome</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                {usuarios.map((u, i) => (
                    <tr key={i}>
                        <td>{u.idRelacional}</td>
                        <td>{u.nome}</td>
                        <td>{u.email}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}