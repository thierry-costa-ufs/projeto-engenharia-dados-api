import { useState, useEffect } from 'react';
import { Plus, Trash2 } from 'lucide-react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import { usuarioSchema } from '../../utils/validators';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

export default function UsuarioForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({
    cpf: '', nome: '', login: '', senha: '', emails: [''], telefones: ['']
  });
  const [errors, setErrors] = useState({});

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('usuarios');

  useEffect(() => {
    if (initialData) {
      setFormData({
        cpf: initialData.cpf || initialData.id || '',
        nome: initialData.nome || '',
        login: initialData.login || '',
        senha: '',
        emails: initialData.email && initialData.email.length > 0 ? initialData.email : [''],
        telefones: initialData.telefone && initialData.telefone.length > 0 ? initialData.telefone : ['']
      });
    } else {
      setFormData({ cpf: '', nome: '', login: '', senha: '', emails: [''], telefones: [''] });
    }
    setErrors({});
  }, [initialData]);

  const handleBaseChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) setErrors(prev => ({ ...prev, [name]: null }));
  };

  const handleArrayChange = (index, campo, value) => {
    setFormData(prev => {
      const novaLista = [...prev[campo]];
      novaLista[index] = value;
      return { ...prev, [campo]: novaLista };
    });
  };

  const adicionarCampo = (campo) => {
    setFormData(prev => ({ ...prev, [campo]: [...prev[campo], ''] }));
  };

  const removerCampo = (index, campo) => {
    if (formData[campo].length === 1) return;
    setFormData(prev => ({
      ...prev,
      [campo]: prev[campo].filter((_, i) => i !== index)
    }));
  };

  const handleLocalSubmit = async (e) => {
    e.preventDefault();

    // Valida propriedades base via Zod
    const validacao = usuarioSchema.safeParse(formData);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    const payload = {
      nome: validacao.data.nome,
      login: validacao.data.login,
      cpf: Number(validacao.data.cpf),
      email: formData.emails.filter(emailStr => emailStr.trim() !== ''),
      telefone: formData.telefones.filter(telStr => telStr.trim() !== '')
    };

    if (validacao.data.senha || !isEditing) {
      payload.senha = validacao.data.senha;
    }

    if (isEditing) {
      await onSubmit(payload);
    } else {
      const resultado = await executarEscritaDupla(payload);
      if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
        setFormData({ cpf: '', nome: '', login: '', senha: '', emails: [''], telefones: [''] });
        if (resultado.status === 'SUCESSO') onSubmit({ status: 'SUCESSO' });
      }
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleLocalSubmit} className={theme.form}>
        <input
          type="number"
          name="cpf"
          placeholder="CPF (Apenas números)"
          value={formData.cpf}
          onChange={handleBaseChange}
          required
          disabled={isEditing}
        />
        {errors.cpf && <span className={theme.errorText}>{errors.cpf}</span>}

        <input
          type="text"
          name="nome"
          placeholder="Nome Completo"
          value={formData.nome}
          onChange={handleBaseChange}
          required
        />
        {errors.nome && <span className={theme.errorText}>{errors.nome}</span>}

        <div className={theme.row}>
          <div style={{ flex: 1 }}>
            <input
              type="text"
              name="login"
              placeholder="Username"
              value={formData.login}
              onChange={handleBaseChange}
              required
            />
            {errors.login && <span className={theme.errorText}>{errors.login}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="password"
              name="senha"
              placeholder="Senha"
              value={formData.senha}
              onChange={handleBaseChange}
              required={!isEditing}
            />
            {errors.senha && <span className={theme.errorText}>{errors.senha}</span>}
          </div>
        </div>

        {/* Blocos de arrays dinâmicos mantidos intocados */}
        <div className={theme.dynamicSection}>
          <label className={theme.fieldLabel}>E-mails Vinculados</label>
          {formData.emails.map((email, idx) => (
            <div key={idx} className={theme.dynamicRow}>
              <input
                type="email"
                placeholder={`E-mail #${idx + 1}`}
                value={email}
                onChange={(e) => handleArrayChange(idx, 'emails', e.target.value)}
                required
              />
              {formData.emails.length > 1 && (
                <button type="button" className={theme.btnRemove} onClick={() => removerCampo(idx, 'emails')}>
                  <Trash2 size={16}/>
                </button>
              )}
            </div>
          ))}
          <button type="button" className={theme.btnAdd} onClick={() => adicionarCampo('emails')}>
            <Plus size={14}/> Adicionar E-mail
          </button>
        </div>

        <div className={theme.dynamicSection}>
          <label className={theme.fieldLabel}>Telefones</label>
          {formData.telefones.map((tel, idx) => (
            <div key={idx} className={theme.dynamicRow}>
              <input
                type="text"
                placeholder={`Telefone #${idx + 1}`}
                value={tel}
                onChange={(e) => handleArrayChange(idx, 'telefones', e.target.value)}
                required
              />
              {formData.telefones.length > 1 && (
                <button type="button" className={theme.btnRemove} onClick={() => removerCampo(idx, 'telefones')}>
                  <Trash2 size={16}/>
                </button>
              )}
            </div>
          ))}
          <button type="button" className={theme.btnAdd} onClick={() => adicionarCampo('telefones')}>
            <Plus size={14}/> Adicionar Telefone
          </button>
        </div>

        <div className={theme.formActionsGroup}>
          {isEditing && (
            <button type="button" className={theme.btnCancel} onClick={onCancel}>
              Cancelar
            </button>
          )}
          <button type="submit" className={theme.btnSubmitEdicao}>
            {isEditing ? 'Salvar Alterações' : 'Dupla Inserção'}
          </button>
        </div>
      </form>

      <ResilienceModal
        aberto={modalAberto}
        entidade="Usuário"
        onKeep={() => { manterApenasNoPostgres(); onSubmit({ status: 'SUCESSO' }); }}
        onRollback={() => { executarRollback(); onCancel(); }}
      />
    </div>
  );
}