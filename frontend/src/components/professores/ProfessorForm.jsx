import React, { useState, useEffect } from 'react';

export default function ProfessorForm({ onSubmit, initialData, isEditing, sharedStyles, onCancel }) {
  const styles = sharedStyles || {};

  const [formData, setFormData] = useState({
    matricula: '',
    cpf: '',
    departamento: '',
    formacao: 'Doutorado',
    jornada: 'DE',
    salario: '',
    dataAdmissao: new Date().toISOString().split('T')[0] // Evita quebra de constraint NOT NULL no SQL
  });

  useEffect(() => {
    if (initialData) {
      setFormData({
        matricula: initialData.matricula || '',
        cpf: initialData.cpf || '',
        departamento: initialData.departamento || '',
        formacao: initialData.formacao || 'Doutorado',
        jornada: initialData.jornada || initialData.tipoJornadaTrabalho || 'DE',
        salario: initialData.salario || '',
        dataAdmissao: initialData.dataAdmissao || new Date().toISOString().split('T')[0]
      });
    }
  }, [initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'salario' ? (value === '' ? '' : Number(value)) : value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <div className={styles.formContainer || 'formContainer'}>
      <form onSubmit={handleSubmit} className={styles.form || 'form'}>

        <div className={styles.row || 'row'}>
          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Matrícula</label>
            <input
              type="text"
              name="matricula"
              value={formData.matricula}
              onChange={handleChange}
              maxLength={7}
              required
              disabled={isEditing}
              placeholder="Ex: P100"
              className={isEditing ? `${styles.inputDisabled}` : ''}
            />
          </div>

          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Depto</label>
            <input
              type="text"
              name="departamento"
              value={formData.departamento}
              onChange={handleChange}
              maxLength={5}
              required
              placeholder="DCOMP"
              className={styles.inputUppercase}
            />
          </div>
        </div>

        <div className={styles.column || 'column'}>
          <label className={styles.fieldLabel || 'fieldLabel'}>CPF do Usuário Base</label>
          <input
            type="text"
            name="cpf"
            value={formData.cpf}
            onChange={handleChange}
            required
            disabled={isEditing}
            placeholder="Apenas números"
            className={isEditing ? `${styles.inputDisabled}` : ''}
          />
        </div>

        <div className={styles.row || 'row'}>
          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Titulação Acadêmica</label>
            <select name="formacao" value={formData.formacao} onChange={handleChange} required>
              <option value="Graduação">Graduação</option>
              <option value="Especialização">Especialização</option>
              <option value="Mestrado">Mestrado</option>
              <option value="Doutorado">Doutorado</option>
            </select>
          </div>

          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Regime de Jornada</label>
            <select name="jornada" value={formData.jornada} onChange={handleChange} required>
              <option value="20h">20 Horas (20h)</option>
              <option value="40h">40 Horas (40h)</option>
              <option value="DE">Dedicação Exclusiva (DE)</option>
            </select>
          </div>
        </div>

        <div className={styles.row || 'row'}>
          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Vencimento (R$)</label>
            <input
              type="number"
              name="salario"
              value={formData.salario}
              onChange={handleChange}
              step="0.01"
              min="0"
              required
              placeholder="0.00"
            />
          </div>

          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Data de Admissão</label>
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
          <div className={styles.formActionsGroup || 'formActionsGroup'}>
            <button type="button" className={styles.btnCancel || 'btnCancel'} onClick={onCancel}>
              Cancelar
            </button>
            <button type="submit" className={`${styles.btnSubmit || 'btnSubmit'} ${styles.btnSubmitEdicao || 'btnSubmitEdicao'}`}>
              Salvar
            </button>
          </div>
        ) : (
          <button type="submit" className={`${styles.btnSubmit || 'btnSubmit'} ${styles.mtTwo}`}>
            Cadastrar Professor
          </button>
        )}
      </form>
    </div>
  );
}