import { useState } from 'react';
import styles from './UsuarioForm.module.css';

export default function UsuarioForm({ onUsuarioCadastrado }) {
  const [cpf, setCpf] = useState('');
  const [nome, setNome] = useState('');
  const [dataNascimento, setDataNascimento] = useState('');
  const [login, setLogin] = useState('');
  const [senha, setSenha] = useState('');

  // Estados dinâmicos para arrays do Postgres
  const [emails, setEmails] = useState(['']);
  const [telefones, setTelefones] = useState(['']);

  // Controle do Modal de Falha Parcial
  const [modalAberto, setModalAberto] = useState(false);
  const [cpfPendente, setCpfPendente] = useState(null);

  const handleAddField = (setter) => setter((prev) => [...prev, '']);

  const handleFieldChange = (index, value, setter) => {
    setter((prev) => {
      const novos = [...prev];
      novos[index] = value;
      return novos;
    });
  };

  const handleRemoveField = (index, setter) => {
    setter((prev) => prev.filter((_, i) => i !== index));
  };

  const limparFormulario = () => {
    setCpf(''); setNome(''); setDataNascimento(''); setLogin(''); setSenha('');
    setEmails(['']); setTelefones(['']);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const novoUsuario = {
        cpf: Number(cpf), // <-- JS nativo, limpo e seguro para converter a string do input
        nome,
        dataNascimento,
        email: emails.filter(e => e.trim() !== ''),
        telefone: telefones.filter(t => t.trim() !== ''),
        login,
        senha
    };

    try {
      const response = await fetch('http://localhost:8080/api/v1/usuarios', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(novoUsuario)
      });

      if (response.status === 201) {
        const resultado = await response.json();

        if (resultado.status === "FALHA_PARCIAL_MONGO") {
          setCpfPendente(resultado.cpf);
          setModalAberto(true);
        } else {
          alert('Usuário persistido com sucesso em ambos os bancos!');
          limparFormulario();
          onUsuarioCadastrado();
        }
      } else {
        alert('Erro na validação do Relacional (PostgreSQL). Operação abortada.');
      }
    } catch (error) {
      console.error('Erro na escrita dupla:', error);
    }
  };

  const desfazerAlteracoes = async () => {
    try {
      await fetch(`http://localhost:8080/api/v1/usuarios/${cpfPendente}`, { method: 'DELETE' });
      alert('Cadastro desfeito do PostgreSQL com sucesso!');
    } catch (err) {
      console.error(err);
    } finally {
      setModalAberto(false);
      limparFormulario();
      onUsuarioCadastrado();
    }
  };

  return (
    <div className={styles.formContainer}>
      <h2>Tabelas Usuário</h2>
      <br/>
      <form onSubmit={handleSubmit} className={styles.form}>
        <div className={styles.row}>
          <input type="number" placeholder="CPF (Apenas números)" value={cpf} onChange={e => setCpf(e.target.value)} required />
          <input type="text" placeholder="Nome Completo" value={nome} onChange={e => setNome(e.target.value)} required />
        </div>

        <div className={styles.row}>
          <input type="date" value={dataNascimento} onChange={e => setDataNascimento(e.target.value)} required />
          <input type="text" placeholder="Login" value={login} onChange={e => setLogin(e.target.value)} required />
          <input type="password" placeholder="Senha" value={senha} onChange={e => setSenha(e.target.value)} required />
        </div>

        {/* Bloco de Emails e Telefones Dinâmicos */}
        <div className={styles.dynamicSection}>
            <div className={styles.row}>
                <div className={styles.column}>
                  <label>E-mails</label>
                  {emails.map((email, idx) => (
                    <div key={idx} className={styles.dynamicRow}>
                      <input type="email" placeholder={`E-mail ${idx + 1}`} value={email} onChange={e => handleFieldChange(idx, e.target.value, setEmails)} required />
                      {emails.length > 1 && <button type="button" onClick={() => handleRemoveField(idx, setEmails)} className={styles.btnRemove}>✕</button>}
                    </div>
                  ))}
                  <button type="button" onClick={() => handleAddField(setEmails)} className={styles.btnAdd}>+ Adicionar Email</button>
                </div>

                <div className={styles.column}>
                  <label>Telefones</label>
                    {telefones.map((tel, idx) => (
                      <div key={idx} className={styles.dynamicRow}>
                        <input type="text" placeholder={`Telefone ${idx + 1}`} value={tel} onChange={e => handleFieldChange(idx, e.target.value, setTelefones)} required />
                        {telefones.length > 1 && <button type="button" onClick={() => handleRemoveField(idx, setTelefones)} className={styles.btnRemove}>✕</button>}
                      </div>
                    ))}
                    <button type="button" onClick={() => handleAddField(setTelefones)} className={styles.btnAdd}>+ Adicionar Telefone</button>
                </div>
            </div>
        </div>
        <br/>
        <button type="submit" className={styles.btnSubmit}>Dupla Inserção</button>
      </form>

      {/* MODAL DE RESILIÊNCIA DA SAGA */}
      {modalAberto && (
        <div className={styles.modalOverlay}>
          <div className={styles.modal}>
            <h4>⚠️ Inconsistência de Escrita Híbrida</h4>
            <p>O usuário foi salvo no <strong>PostgreSQL</strong>, mas o servidor do <strong>MongoDB</strong> falhou ou está offline.</p>
            <p>Como deseja prosseguir com a consistência dos dados?</p>
            <div className={styles.modalActions}>
              <button onClick={() => { setModalAberto(false); limparFormulario(); onUsuarioCadastrado(); }} className={styles.btnKeep}>
                Manter apenas no Postgres
              </button>
              <button onClick={desfazerAlteracoes} className={styles.btnRollback}>
                Desfazer tudo (Rollback no Postgres)
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}