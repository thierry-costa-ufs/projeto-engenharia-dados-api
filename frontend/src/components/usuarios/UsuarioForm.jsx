import { useState, useEffect } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import ResilienceModal from '../shared/ResilienceModal';
import { Plus, Trash2 } from 'lucide-react';
import { api } from '../../utils/api';
import theme from '../../styles/FormTheme.module.css';

export default function UsuarioForm({ onCadastrado, itemEmEdicao, onCancelarEdicao }) {
  const [formData, setFormData] = useState({
    cpf: '', nome: '', login: '', senha: '',
    emails: [''], telefones: ['']
  });

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('usuarios', onCadastrado);

  useEffect(() => {
    if (itemEmEdicao) {
      setFormData({
        // Garante o mapeamento se o back-end estiver mandando como string ou se o objeto diferir
        cpf: itemEmEdicao.cpf || itemEmEdicao.id || '',
        nome: itemEmEdicao.nome || '',
        login: itemEmEdicao.login || '',
        senha: '',
        emails: itemEmEdicao.email && itemEmEdicao.email.length > 0 ? itemEmEdicao.email : [''],
        telefones: itemEmEdicao.telefone && itemEmEdicao.telefone.length > 0 ? itemEmEdicao.telefone : ['']
      });
    } else {
      setFormData({ cpf: '', nome: '', login: '', senha: '', emails: [''], telefones: [''] });
    }
  }, [itemEmEdicao]);

  const handleBaseChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleArrayChange = (index, campo, value) => {
    const novaLista = [...formData[campo]];
    novaLista[index] = value;
    setFormData(prev => ({ ...prev, [campo]: novaLista }));
  };

  const adicionarCampo = (campo) => {
    setFormData(prev => ({ ...prev, [campo]: [...prev[campo], ''] }));
  };

  const removerCampo = (index, campo) => {
    if (formData[campo].length === 1) return;
    const novaLista = formData[campo].filter((_, i) => i !== index);
    setFormData(prev => ({ ...prev, [campo]: novaLista }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      ...formData,
      cpf: Number(formData.cpf),
      email: formData.emails.filter(e => e.trim() !== ''),
      telefone: formData.telefones.filter(t => t.trim() !== '')
    };
    delete payload.emails;
    delete payload.telefones;

    if (itemEmEdicao) {
      try {
        await api.put(`usuarios/${formData.cpf}`, payload);
        alert('Usuário atualizado com sucesso!');
        onCadastrado();
      } catch (err) {
        console.error('Erro ao atualizar usuário:', err);
        alert('Falha na atualização poliglota.');
      }
    } else {
      const resultado = await executarEscritaDupla(payload);
      if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
        setFormData({ cpf: '', nome: '', login: '', senha: '', emails: [''], telefones: [''] });
      }
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleSubmit} className={theme.form}>
        <input
          type="number"
          name="cpf"
          placeholder="CPF (Apenas números)"
          value={formData.cpf}
          onChange={handleBaseChange}
          required
          disabled={!!itemEmEdicao}
        />

        <input type="text" name="nome" placeholder="Nome Completo" value={formData.nome} onChange={handleBaseChange} required />

        <div className={theme.row}>
          <input type="text" name="login" placeholder="Username" value={formData.login} onChange={handleBaseChange} required />
          <input type="password" name="senha" placeholder="Senha" value={formData.senha} onChange={handleBaseChange} required={!itemEmEdicao} />
        </div>

        <div className={theme.dynamicSection}>
          <label className={theme.fieldLabel}>E-mails Vinculados</label>
          {formData.emails.map((email, idx) => (
            <div key={idx} className={theme.dynamicRow}>
              <input type="email" placeholder={`E-mail #${idx + 1}`} value={email} onChange={(e) => handleArrayChange(idx, 'emails', e.target.value)} required />
              {formData.emails.length > 1 && (
                <button type="button" className={theme.btnRemove} onClick={() => removerCampo(idx, 'emails')}><Trash2 size={16}/></button>
              )}
            </div>
          ))}
          <button type="button" className={theme.btnAdd} onClick={() => adicionarCampo('emails')}><Plus size={14}/> Adicionar E-mail</button>
        </div>

        <div className={theme.dynamicSection}>
          <label className={theme.fieldLabel}>Telefones</label>
          {formData.telefones.map((tel, idx) => (
            <div key={idx} className={theme.dynamicRow}>
              <input type="text" placeholder={`Telefone #${idx + 1}`} value={tel} onChange={(e) => handleArrayChange(idx, 'telefones', e.target.value)} required />
              {formData.telefones.length > 1 && (
                <button type="button" className={theme.btnRemove} onClick={() => removerCampo(idx, 'telefones')}><Trash2 size={16}/></button>
              )}
            </div>
          ))}
          <button type="button" className={theme.btnAdd} onClick={() => adicionarCampo('telefones')}><Plus size={14}/> Adicionar Telefone</button>
        </div>

        {/* JSX completamente limpo de inline styles e seguro contra ReferenceError */}
        <div className={theme.formActionsGroup}>
          {itemEmEdicao && (
            <button
              type="button"
              className={theme.btnCancel}
              onClick={onCancelarEdicao}
            >
              Cancelar
            </button>
          )}
          <button type="submit" className={theme.btnSubmitEdicao}>
            {itemEmEdicao ? 'Salvar Alterações' : 'Dupla Inserção'}
          </button>
        </div>
      </form>
      <ResilienceModal aberto={modalAberto} entidade="Usuário" onKeep={manterApenasNoPostgres} onRollback={executarRollback} />
    </div>
  );
}