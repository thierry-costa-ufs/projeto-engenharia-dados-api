import { useState } from 'react';

export default function UsuarioForm({ onUsuarioCadastrado }) {
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        const novoUsuario = { nome, email, senha };

        try {
            const response = await fetch('http://localhost:8080/api/v1/usuarios', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(novoUsuario)
            });

            if (response.ok) {
                alert('Usuário cadastrado com sucesso nos dois bancos!');
                setNome(''); setEmail(''); setSenha('');
                onUsuarioCadastrado(); // Atualiza as tabelas automaticamente
            } else {
                alert('Erro ao cadastrar usuário.');
            }
        } catch (error) {
            console.error('Erro na requisição:', error);
        }
    };

    return (
        <div style={{ padding: '20px', border: '1px solid #ccc', borderRadius: '8px' }}>
            <h3>Cadastrar Novo Usuário</h3>
            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                <input type="text" placeholder="Nome" value={nome} onChange={e => setNome(e.target.value)} required />
                <input type="email" placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} required />
                <input type="password" placeholder="Senha" value={senha} onChange={e => setSenha(e.target.value)} required />
                <button type="submit" style={{ cursor: 'pointer', padding: '8px' }}>Salvar (Escrita Dupla)</button>
            </form>
        </div>
    );
}