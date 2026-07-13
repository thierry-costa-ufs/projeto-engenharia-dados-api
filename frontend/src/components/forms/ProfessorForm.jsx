import { useState, useEffect } from 'react';
import { professorSchema } from '../../utils/validators';
import { TITULACAO_PROFESSOR, JORNADA_PROFESSOR } from '../../utils/constants/enums';
import theme from '../../styles/FormTheme.module.css';

const obterDataAtual = () => new Date().toISOString().split('T')[0];

export default function ProfessorForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState({
    matricula: '', cpf: '', departamento: '', formacao: 'Doutorado',
    jornada: 'DE', salario: '', dataAdmissao: obterDataAtual()
  });
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialData) {
      setFormData({
        matricula: initialData.matricula || '',
        cpf: initialData.cpf || '',
        departamento: initialData.departamento || '',
        formacao: initialData.formacao || 'Doutorado',
        jornada: initialData.jornada || initialData.tipoJornadaTrabalho || 'DE',
        salario: initialData.salario || '',
        dataAdmissao: initialData.dataAdmissao || obterDataAtual()
      });
    } else {
      setFormData({
        matricula: '', cpf: '', departamento: '', formacao: 'Doutorado',
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

  const handleLocalSubmit = (e) => {
    e.preventDefault();

    const validacao = professorSchema.safeParse(formData);

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
              value={formData.matricula}
              onChange={handleChange}
              maxLength={7}
              required
              disabled={isEditing}
              placeholder="Ex: P100"
            />
            {errors.matricula && <span className={theme.errorText}>{errors.matricula}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Depto</label>
            <input
              type="text"
              name="departamento"
              value={formData.departamento}
              onChange={handleChange}
              maxLength={5}
              required
              placeholder="DCOMP"
              className={theme.inputUppercase}
            />
            {errors.departamento && <span className={theme.errorText}>{errors.departamento}</span>}
          </div>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>CPF do Usuário Base</label>
          <input
            type="text"
            name="cpf"
            value={formData.cpf}
            onChange={handleChange}
            required
            disabled={isEditing}
            placeholder="Apenas números"
          />
          {errors.cpf && <span className={theme.errorText}>{errors.cpf}</span>}
        </div>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Titulação Acadêmica</label>
            <select name="formacao" value={formData.formacao} onChange={handleChange} required>
              {TITULACAO_PROFESSOR.map(item => (
                <option key={item.value} value={item.value}>{item.label}</option>
              ))}
            </select>
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Regime de Jornada</label>
            <select name="jornada" value={formData.jornada} onChange={handleChange} required>
              {JORNADA_PROFESSOR.map(item => (
                <option key={item.value} value={item.value}>{item.label}</option>
              ))}
            </select>
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Vencimento (R$)</label>
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

        {isEditing ? (
          <div className={theme.formActionsGroup}>
            <button type="button" className={theme.btnCancel} onClick={onCancel}>
              Cancelar
            </button>
            <button type="submit" className={theme.btnSubmitEdicao}>
              Salvar Alterações
            </button>
          </div>
        ) : (
          <button type="submit" className={theme.btnSubmit}>
            Cadastrar Professor
          </button>
        )}
      </form>
    </div>
  );
}