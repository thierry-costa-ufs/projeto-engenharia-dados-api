import { useState, useEffect } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import { turmaSchema } from '../../utils/validators';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

export default function TurmaForm({ onSubmit, initialData, isEditing, onCancel, onSuccess }) {
  const [formData, setFormData] = useState({ id_turma: '', cod_disc: '', numero: '', ano: '', semestre: '' });
  const [errors, setErrors] = useState({});

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('turmas');

  useEffect(() => {
    if (initialData) {
      setFormData({
        id_turma: initialData.id_turma || initialData.idTurma || '',
        cod_disc: initialData.cod_disc || initialData.codigoDisciplina || '',
        numero: initialData.numero || initialData.codigoTurma || '',
        ano: initialData.ano || '',
        semestre: initialData.semestre || ''
      });
    } else {
      setFormData({ id_turma: '', cod_disc: '', numero: '', ano: '', semestre: '' });
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

    const validacao = turmaSchema.safeParse(formData);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    const payload = {
      cod_disc: validacao.data.cod_disc,
      numero: validacao.data.numero,
      ano: validacao.data.ano,
      semestre: validacao.data.semestre
    };

    if (isEditing) {
      await onSubmit(payload);
    } else {
      const resultado = await executarEscritaDupla(payload);

      if (!resultado || resultado?.status !== 'ERRO') {
        // limpa os campos
        setFormData({ id_turma: '', cod_disc: '', numero: '', ano: '', semestre: '' });

        // fecha a janela
        if (onCancel) {
          onCancel();
        }

        // atualiza a tabela em segundo plano
        if (onSuccess) {
          onSuccess();
        }
      }
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleLocalSubmit} className={theme.form}>
        {isEditing && (
          <input
            type="text"
            name="id_turma"
            value={`ID Interno da Turma: ${formData.id_turma}`}
            disabled
          />
        )}

        <div className={theme.row}>
          <div style={{ flex: 1 }}>
            <input
              type="text"
              name="cod_disc"
              placeholder="Código da Disciplina"
              maxLength={8}
              value={formData.cod_disc}
              onChange={handleChange}
              required
            />
            {errors.cod_disc && <span className={theme.errorText}>{errors.cod_disc}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="number"
              name="numero"
              placeholder="Número da Turma (Ex: 1)"
              value={formData.numero}
              onChange={handleChange}
              required
            />
            {errors.numero && <span className={theme.errorText}>{errors.numero}</span>}
          </div>
        </div>

        <div className={theme.row}>
          <div style={{ flex: 1 }}>
            <input
              type="number"
              name="ano"
              placeholder="Ano (Ex: 2026)"
              value={formData.ano}
              onChange={handleChange}
              required
            />
            {errors.ano && <span className={theme.errorText}>{errors.ano}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="number"
              name="semestre"
              placeholder="Semestre (1 ou 2)"
              value={formData.semestre}
              onChange={handleChange}
              required
            />
            {errors.semestre && <span className={theme.errorText}>{errors.semestre}</span>}
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
        entidade="Turma"
        onKeep={() => { manterApenasNoPostgres(); onSubmit({ status: 'SUCESSO' }); }}
        onRollback={() => { executarRollback(); onCancel(); }}
      />
    </div>
  );
}