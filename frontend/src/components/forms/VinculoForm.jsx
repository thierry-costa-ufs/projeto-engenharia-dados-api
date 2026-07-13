import { useState, useEffect } from 'react';
import { vinculoSchema } from '../../utils/validators';
import { STATUS_VINCULO } from '../../utils/constants/enums';
import theme from '../../styles/FormTheme.module.css';

const ESTADO_INICIAL = { matEstudante: '', codCurso: '', dataEntrada: '', status: 'Ativo', dataSaida: '' };

export default function VinculoForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState(ESTADO_INICIAL);
  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (initialData) {
      setFormData({
        matEstudante: initialData.matEstudante || '',
        codCurso: initialData.codCurso || '',
        dataEntrada: initialData.dataEntrada || '',
        status: initialData.status || initialData.situacao || 'Ativo',
        dataSaida: initialData.dataSaida || ''
      });
    } else {
      setFormData(ESTADO_INICIAL);
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

    const validacao = vinculoSchema.safeParse(formData);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    onSubmit({
      idVinculo: initialData?.idVinculo || null,
      matEstudante: validacao.data.matEstudante,
      codCurso: validacao.data.codCurso,
      dataEntrada: validacao.data.dataEntrada,
      dataSaida: validacao.data.dataSaida,
      status: validacao.data.status
    });
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleLocalSubmit} className={theme.form}>
        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Matrícula do Estudante</label>
            <input
              type="text"
              name="matEstudante"
              placeholder="Ex: E101"
              value={formData.matEstudante}
              onChange={handleChange}
              maxLength={4}
              required
            />
            {errors.matEstudante && <span className={theme.errorText}>{errors.matEstudante}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Código do Curso</label>
            <input
              type="number"
              name="codCurso"
              placeholder="Código numérico"
              value={formData.codCurso}
              onChange={handleChange}
              required
            />
            {errors.codCurso && <span className={theme.errorText}>{errors.codCurso}</span>}
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Data de Entrada</label>
            <input
              type="date"
              name="dataEntrada"
              value={formData.dataEntrada}
              onChange={handleChange}
              required
            />
            {errors.dataEntrada && <span className={theme.errorText}>{errors.dataEntrada}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Data de Saída (Opcional)</label>
            <input
              type="date"
              name="dataSaida"
              value={formData.dataSaida}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Status Acadêmico</label>
          <select name="status" value={formData.status} onChange={handleChange} required>
            {STATUS_VINCULO.map(item => (
              <option key={item.value} value={item.value}>{item.label}</option>
            ))}
          </select>
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
            Dupla Inserção de Vínculo
          </button>
        )}
      </form>
    </div>
  );
}