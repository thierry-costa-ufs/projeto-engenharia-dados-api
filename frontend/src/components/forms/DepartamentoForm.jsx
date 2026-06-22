import { useState, useEffect } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import { departamentoSchema } from '../../utils/validators'; // IMPORT VIA BARREL
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

export default function DepartamentoForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({
    cod_depto: '', nome: '', chefe: '', orcamento: '', comissal: ''
  });
  const [errors, setErrors] = useState({});

  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('departamentos');

  useEffect(() => {
    if (initialData) {
      setFormData({
        cod_depto: initialData.idDepartamento || initialData.cod_depto || '',
        nome: initialData.nome || '',
        chefe: initialData.chefe || '',
        orcamento: initialData.orcamento || '',
        comissal: initialData.comissal || ''
      });
    } else {
      setFormData({ cod_depto: '', nome: '', chefe: '', orcamento: '', comissal: '' });
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

    const validacao = departamentoSchema.safeParse(formData);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    const payload = {
      cod_depto: validacao.data.cod_depto,
      nome: validacao.data.nome,
      chefe: validacao.data.chefe,
      orcamento: validacao.data.orcamento,
      comissal: validacao.data.comissal
    };

    if (isEditing) {
      await onSubmit(payload);
    } else {
      const resultado = await executarEscritaDupla(payload);
      if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
        setFormData({ cod_depto: '', nome: '', chefe: '', orcamento: '', comissal: '' });
        if (resultado.status === 'SUCESSO') onSubmit({ status: 'SUCESSO' });
      }
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleLocalSubmit} className={theme.form}>
        <input
          type="text"
          name="cod_depto"
          placeholder="Código do Departamento (Ex: DCOMP)"
          maxLength={5}
          value={formData.cod_depto}
          onChange={handleChange}
          required
          disabled={isEditing}
        />
        {errors.cod_depto && <span className={theme.errorText}>{errors.cod_depto}</span>}

        <input
          type="text"
          name="nome"
          placeholder="Nome do Departamento"
          maxLength={50}
          value={formData.nome}
          onChange={handleChange}
          required
        />
        {errors.nome && <span className={theme.errorText}>{errors.nome}</span>}

        <input
          type="text"
          name="chefe"
          placeholder="Matrícula do Chefe (Opcional)"
          maxLength={7}
          value={formData.chefe}
          onChange={handleChange}
        />
        {errors.chefe && <span className={theme.errorText}>{errors.chefe}</span>}

        <div className={theme.row}>
          <div style={{ flex: 1 }}>
            <input
              type="number"
              step="any"
              name="orcamento"
              placeholder="Orçamento (Deve ser > 0)"
              value={formData.orcamento}
              onChange={handleChange}
            />
            {errors.orcamento && <span className={theme.errorText}>{errors.orcamento}</span>}
          </div>

          <div style={{ flex: 1 }}>
            <input
              type="number"
              step="any"
              name="comissal"
              placeholder="Comissão de Função"
              value={formData.comissal}
              onChange={handleChange}
            />
            {errors.comissal && <span className={theme.errorText}>{errors.comissal}</span>}
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
        entidade="Departamento"
        onKeep={() => { manterApenasNoPostgres(); onSubmit({ status: 'SUCESSO' }); }}
        onRollback={() => { executarRollback(); onCancel(); }}
      />
    </div>
  );
}