import { useState, useEffect } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import { estudanteSchema } from '../../utils/validators';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

export default function EstudanteForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({ matEstudante: '', cpf: '', mc: '', anoIngresso: '' });
  const [errors, setErrors] = useState({});

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('estudantes');

  useEffect(() => {
    if (initialData) {
      setFormData({
        matEstudante: initialData.matEstudante || initialData.matricula || initialData.mat_estudante || '',
        cpf: initialData.cpf ? String(initialData.cpf) : '',
        mc: initialData.mc ?? initialData.MC ?? '',
        anoIngresso: initialData.anoIngresso || initialData.ano_ingresso || initialData.dataIngresso?.split('-')[0] || ''
      });
    } else {
      setFormData({ matEstudante: '', cpf: '', mc: '', anoIngresso: '' });
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

    const dadosParaValidacao = {
      ...formData,
      cpf: String(formData.cpf),
      mc: formData.mc !== '' ? Number(formData.mc) : 0,
      anoIngresso: Number(formData.anoIngresso)
    };

    const validacao = estudanteSchema.safeParse(dadosParaValidacao);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    onSubmit(validacao.data);
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleLocalSubmit} className={theme.form}>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <input
              type="text"
              name="matEstudante"
              placeholder="Matrícula"
              value={formData.matEstudante}
              onChange={handleChange}
              required
              disabled={isEditing}
              maxLength="7"
            />
            {errors.matEstudante && <span className={theme.errorText}>{errors.matEstudante}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <input
              type="text"
              name="cpf"
              placeholder="CPF (Apenas números)"
              value={formData.cpf}
              onChange={handleChange}
              required
              maxLength="11"
            />
            {errors.cpf && <span className={theme.errorText}>{errors.cpf}</span>}
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <input
              type="number"
              step="0.1"
              name="mc"
              placeholder="Média Conclusão (MC)"
              value={formData.mc}
              onChange={handleChange}
            />
            {errors.mc && <span className={theme.errorText}>{errors.mc}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <input
              type="number"
              name="anoIngresso"
              placeholder="Ano de Ingresso"
              value={formData.anoIngresso}
              onChange={handleChange}
              required
            />
            {errors.anoIngresso && <span className={theme.errorText}>{errors.anoIngresso}</span>}
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