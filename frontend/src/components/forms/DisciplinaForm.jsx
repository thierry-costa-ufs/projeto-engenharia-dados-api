import { useState, useEffect } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import { disciplinaSchema } from '../../utils/validators';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

export default function DisciplinaForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({
    cod_disc: '', nome: '', pre_req: '', creditos: '', depto_responsavel: ''
  });
  const [errors, setErrors] = useState({});

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('disciplinas');

  useEffect(() => {
    if (initialData) {
      setFormData({
        cod_disc: initialData.codigoDisciplina || initialData.cod_disc || '',
        nome: initialData.nome || '',
        pre_req: initialData.pre_req || '',
        creditos: initialData.creditos || '',
        depto_responsavel: initialData.idDepartamento || initialData.depto_responsavel || ''
      });
    } else {
      setFormData({ cod_disc: '', nome: '', pre_req: '', creditos: '', depto_responsavel: '' });
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

    const validacao = disciplinaSchema.safeParse(formData);

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
      nome: validacao.data.nome,
      pre_req: validacao.data.pre_req,
      creditos: validacao.data.creditos,
      depto_responsavel: validacao.data.depto_responsavel
    };

    if (isEditing) {
      await onSubmit(payload);
    } else {
      const resultado = await executarEscritaDupla(payload);
      if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
        setFormData({ cod_disc: '', nome: '', pre_req: '', creditos: '', depto_responsavel: '' });
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
              name="cod_disc"
              placeholder="Código Disciplina (Ex: COMP0311)"
              maxLength={8}
              value={formData.cod_disc}
              onChange={handleChange}
              required
              disabled={isEditing}
            />
            {errors.cod_disc && <span className={theme.errorText}>{errors.cod_disc}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="number"
              name="creditos"
              placeholder="Créditos (1 a 11)"
              value={formData.creditos}
              onChange={handleChange}
              required
            />
            {errors.creditos && <span className={theme.errorText}>{errors.creditos}</span>}
          </div>
        </div>

        <input
          type="text"
          name="nome"
          placeholder="Nome da Disciplina"
          maxLength={40}
          value={formData.nome}
          onChange={handleChange}
          required
        />
        {errors.nome && <span className={theme.errorText}>{errors.nome}</span>}

        <div className={theme.row}>
          <div style={{ flex: 1 }}>
            <input
              type="text"
              name="pre_req"
              placeholder="Pré-requisito (Cód. Opcional)"
              maxLength={8}
              value={formData.pre_req}
              onChange={handleChange}
            />
            {errors.pre_req && <span className={theme.errorText}>{errors.pre_req}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="text"
              name="depto_responsavel"
              placeholder="Cód. Depto Responsável"
              maxLength={5}
              value={formData.depto_responsavel}
              onChange={handleChange}
            />
            {errors.depto_responsavel && <span className={theme.errorText}>{errors.depto_responsavel}</span>}
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
        entidade="Disciplina"
        onKeep={() => { manterApenasNoPostgres(); onSubmit({ status: 'SUCESSO' }); }}
        onRollback={() => { executarRollback(); onCancel(); }}
      />
    </div>
  );
}