export default function TabelaNoSql({ usuarios }) {
    return (
        <div style={{ flex: 1, padding: '15px', border: '1px solid #10b981', borderRadius: '8px' }}>
            <h4 style={{ color: '#10b981' }}>🍃 MongoDB (NoSQL)</h4>
            <table border="1" width="100%" style={{ borderCollapse: 'collapse', marginTop: '10px' }}>
                <thead>
                <tr>
                    <th>ID (String/Hex)</th>
                    <th>Nome</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                {usuarios.map((u, i) => (
                    <tr key={i}>
                        <td style={{ fontSize: '12px' }}>{u.idNoSql}</td>
                        <td>{u.nome}</td>
                        <td>{u.email}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}