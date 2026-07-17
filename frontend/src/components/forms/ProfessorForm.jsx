import { useState, useEffect } from 'react';
import { professorSchema } from '../../utils/validators';
import { TITULACAO_PROFESSOR, JORNADA_PROFESSOR } from '../../utils/constants/enums';
import theme from '../../styles/FormTheme.module.css';

const obterDataAtual = () => new Date().toISOString().split('T')[0];

export default function ProfessorForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({
    matricula: '', cpf: '', departamento: '', formacao: 'DOUTORADO',
    jornada: 'DE', salario: '', dataAdmissao: obterDataAtual()
  });
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialData) {
      const formacaoFormatada = (initialData.formacao || 'DOUTORADO').toUpperCase().normalize('NFD').replace(/[\u0300-\u036f]/g, "");
      const jornadaFormatada = (initialData.jornada || initialData.tipoJornadaTrabalho || 'DE').toUpperCase();

      setFormData({
        matricula: initialData.matricula || '',
        cpf: initialData.cpf ? String(initialData.cpf) : '',
        departamento: initialData.departamento || '',
        formacao: formacaoFormatada,
        jornada: jornadaFormatada,
        salario: initialData.salario || '',
        dataAdmissao: initialData.dataAdmissao || obterDataAtual()
      });
    } else {
      setFormData({
        matricula: '', cpf: '', departamento: '', formacao: 'DOUTORADO',
        jornada: 'DE', salario: '', dataAdmissao: obterDataAtual()
      });
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
      salario: Number(formData.salario)
    };

    const validacao = professorSchema.safeParse(dadosParaValidacao);

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
            <label className={theme.fieldLabel}>Matrícula</label>
            <input
              type="text"
              name="matricula"
              placeholder="Ex: 1234567"
              value={formData.matricula}
              onChange={handleChange}
              required
              disabled={isEditing}
            />
            {errors.matricula && <span className={theme.errorText}>{errors.matricula}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>CPF do Usuário</label>
            <input
              type="text"
              name="cpf"
              placeholder="Apenas números"
              value={formData.cpf}
              onChange={handleChange}
              required
              maxLength="11"
            />
            {errors.cpf && <span className={theme.errorText}>{errors.cpf}</span>}
          </div>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Departamento (Código)</label>
          <input
            type="text"
            name="departamento"
            placeholder="Ex: DCOMP"
            value={formData.departamento}
            onChange={handleChange}
            required
          />
          {errors.departamento && <span className={theme.errorText}>{errors.departamento}</span>}
        </div>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Formação</label>
            <select name="formacao" value={formData.formacao} onChange={handleChange}>
              {TITULACAO_PROFESSOR.map(t => (
                <option
                  key={t.value}
                  value={t.value.toUpperCase().normalize('NFD').replace(/[\u0300-\u036f]/g, "")}
                >
                  {t.label}
                </option>
              ))}
            </select>
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Jornada</label>
            <select name="jornada" value={formData.jornada} onChange={handleChange}>
              {JORNADA_PROFESSOR.map(t => (
                <option key={t.value} value={t.value.toUpperCase()}>
                  {t.label}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Salário (R$)</label>
            <input
              type="number"
              name="salario"
              value={formData.salario}
              onChange={handleChange}
              step="0.01"
              required
              placeholder="0.00"
            />
            {errors.salario && <span className={theme.errorText}>{errors.salario}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Data de Admissão</label>
            <input
              type="date"
              name="dataAdmissao"
              value={formData.dataAdmissao}
              onChange={handleChange}
              required
            />
          </div>
        </div>

        <div className={theme.formActionsGroup}>
          {isEditing && (
            <button type="button" className={theme.btnCancel} onClick={onCancel}>
              Cancelar
            </button>
          )}
          <button type="submit" className={theme.btnSubmitEdicao}>
            {isEditing ? 'Salvar Alterações' : 'Cadastrar Professor'}
          </button>
        </div>
      </form>
    </div>
  );
}