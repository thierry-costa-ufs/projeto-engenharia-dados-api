import { useState, useEffect } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import { cursaSchema } from '../../utils/validators'; // IMPORT VIA BARREL
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

export default function CursaForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({ mat_estudante: '', id_turma: '', nota: '' });
  const [errors, setErrors] = useState({}); // Estado de erros

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('cursa');

  useEffect(() => {
    if (initialData) {
      setFormData({
        mat_estudante: initialData.matEstudante || initialData.mat_estudante || '',
        id_turma: initialData.idTurma || initialData.id_turma || '',
        nota: initialData.nota !== null && initialData.nota !== undefined ? initialData.nota : ''
      });
    } else {
      setFormData({ mat_estudante: '', id_turma: '', nota: '' });
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

    // Validação local via Zod antes da montagem do payload
    const validacao = cursaSchema.safeParse(formData);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    // Payload limpo e tipado gerado pelo Zod
    const payload = {
      mat_estudante: validacao.data.mat_estudante,
      id_turma: validacao.data.id_turma,
      nota: validacao.data.nota
    };

    if (isEditing) {
      await onSubmit(payload);
    } else {
      const resultado = await executarEscritaDupla(payload);
      if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
        setFormData({ mat_estudante: '', id_turma: '', nota: '' });
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
              placeholder="Matrícula do Estudante"
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
              name="id_turma"
              placeholder="ID Numérico da Turma"
              value={formData.id_turma}
              onChange={handleChange}
              required
              disabled={isEditing}
            />
            {errors.id_turma && <span className={theme.errorText}>{errors.id_turma}</span>}
          </div>
        </div>

        <input
          type="number"
          step="0.1"
          name="nota"
          placeholder="Nota da Disciplina (0.0 a 10.0)"
          value={formData.nota}
          onChange={handleChange}
        />
        {errors.nota && <span className={theme.errorText}>{errors.nota}</span>}

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
        entidade="Inscrição (Cursa)"
        onKeep={() => { manterApenasNoPostgres(); onSubmit({ status: 'SUCESSO' }); }}
        onRollback={() => { executarRollback(); onCancel(); }}
      />
    </div>
  );
}