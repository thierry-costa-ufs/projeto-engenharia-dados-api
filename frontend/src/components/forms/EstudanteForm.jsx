import { useState, useEffect } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import { estudanteSchema } from '../../utils/validators';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

export default function EstudanteForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({ mat_estudante: '', cpf: '', MC: '', ano_ingresso: '' });
  const [errors, setErrors] = useState({});

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('estudantes');

  useEffect(() => {
    if (initialData) {
      setFormData({
        mat_estudante: initialData.matricula || initialData.mat_estudante || '',
        cpf: initialData.cpf || '',
        MC: initialData.MC !== undefined ? initialData.MC : '',
        ano_ingresso: initialData.ano_ingresso || initialData.dataIngresso?.split('-')[0] || ''
      });
    } else {
      setFormData({ mat_estudante: '', cpf: '', MC: '', ano_ingresso: '' });
    }
    setErrors({});
  }, [initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (errors[name]) setErrors(prev => ({ ...prev, [name]: null }));
  };

  const handleLocalSubmit = async (e) => {
    e.preventDefault();

    const validacao = estudanteSchema.safeParse(formData);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    const payload = {
      mat_estudante: validacao.data.mat_estudante,
      cpf: Number(validacao.data.cpf),
      MC: validacao.data.MC,
      ano_ingresso: validacao.data.ano_ingresso
    };

    if (isEditing) {
      await onSubmit(payload);
    } else {
      const resultado = await executarEscritaDupla(payload);
      if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
        setFormData({ mat_estudante: '', cpf: '', MC: '', ano_ingresso: '' });
        if (resultado.status === 'SUCESSO') onSubmit({ status: 'SUCESSO' });
      }
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleLocalSubmit} className={theme.form}>
        <div className={theme.row}>
          <div style={{ flex: 1 }}>
            <input
              type="text"
              name="mat_estudante"
              placeholder="Matrícula (7 caracteres)"
              maxLength={7}
              value={formData.mat_estudante}
              onChange={handleChange}
              required
              disabled={isEditing}
            />
            {errors.mat_estudante && <span className={theme.errorText}>{errors.mat_estudante}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="number"
              name="cpf"
              placeholder="CPF do Usuário Base"
              value={formData.cpf}
              onChange={handleChange}
              required
              disabled={isEditing}
            />
            {errors.cpf && <span className={theme.errorText}>{errors.cpf}</span>}
          </div>
        </div>

        <div className={theme.row}>
          <div style={{ flex: 1 }}>
            <input
              type="number"
              step="0.1"
              name="MC"
              placeholder="Média Conclusão (MC)"
              value={formData.MC}
              onChange={handleChange}
            />
            {errors.MC && <span className={theme.errorText}>{errors.MC}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="number"
              name="ano_ingresso"
              placeholder="Ano de Ingresso"
              value={formData.ano_ingresso}
              onChange={handleChange}
              required
            />
            {errors.ano_ingresso && <span className={theme.errorText}>{errors.ano_ingresso}</span>}
          </div>
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
        entidade="Estudante"
        onKeep={() => { manterApenasNoPostgres(); onSubmit({ status: 'SUCESSO' }); }}
        onRollback={() => { executarRollback(); onCancel(); }}
      />
    </div>
  );
}