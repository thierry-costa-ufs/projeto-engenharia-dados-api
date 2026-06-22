import React, { useState, useEffect } from 'react';

export default function CursoForm({ onSubmit, initialData, isEditing, sharedStyles, onCancel }) {
  const [formData, setFormData] = useState({
    idCurso: null,
    nome: '',
    grau: 'BACHARELADO',
    turno: 'MATUTINO',
    campus: '',
    nivel: 'GRADUACAO'
  });

  useEffect(() => {
    if (initialData) {
      setFormData({
        idCurso: initialData.idCurso || null,
        nome: initialData.nome || '',
        grau: initialData.grau || 'BACHARELADO',
        turno: initialData.turno || 'MATUTINO',
        campus: initialData.campus || '',
        nivel: initialData.nivel || 'GRADUACAO'
      });
    }
  }, [initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  const styles = sharedStyles || {};

  return (
    <div className={styles.formContainer || 'formContainer'}>
      <form onSubmit={handleSubmit} className={styles.form || 'form'}>

        {/* Nome do Curso */}
        <div className={styles.column || 'column'}>
          <label className={styles.fieldLabel || 'fieldLabel'}>Nome do Curso</label>
          <input
            type="text"
            name="nome"
            value={formData.nome}
            onChange={handleChange}
            maxLength={100}
            required
            placeholder="Ex: Ciência da Computação"
          />
        </div>

        {/* Campus */}
        <div className={styles.column || 'column'}>
          <label className={styles.fieldLabel || 'fieldLabel'}>Campus / Localização</label>
          <input
            type="text"
            name="campus"
            value={formData.campus}
            onChange={handleChange}
            maxLength={100}
            required
            placeholder="Ex: São Cristóvão"
          />
        </div>

        {/* LINHA DUPLA: Grau Acadêmico e Turno */}
        <div className={styles.row || 'row'}>
          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Grau</label>
            <select
              name="grau"
              value={formData.grau}
              onChange={handleChange}
              required
            >
              <option value="BACHARELADO">Bacharelado</option>
              <option value="LICENCIATURA_PLENA">Licenciatura Plena</option>
            </select>
          </div>

          <div className={styles.column || 'column'}>
            <label className={styles.fieldLabel || 'fieldLabel'}>Turno</label>
            <select
              name="turno"
              value={formData.turno}
              onChange={handleChange}
              required
            >
              <option value="MATUTINO">Matutino</option>
              <option value="VESPERTINO">Vespertino</option>
              <option value="NOTURNO">Noturno</option>
              <option value="TURNO_INDEFINIDO">Turno Indefinido</option>
            </select>
          </div>
        </div>

        {/* Nível Acadêmico */}
        <div className={styles.column || 'column'}>
          <label className={styles.fieldLabel || 'fieldLabel'}>Nível Acadêmico</label>
          <select
            name="nivel"
            value={formData.nivel}
            onChange={handleChange}
            required
          >
            <option value="GRADUACAO">Graduação</option>
            <option value="MESTRADO">Mestrado</option>
            <option value="DOUTORADO">Doutorado</option>
            <option value="LATO">Lato Sensu (Plano)</option>
          </select>
        </div>

        {/* GRUPO DE AÇÕES DINÂMICAS */}
        {isEditing ? (
          <div className={styles.formActionsGroup || 'formActionsGroup'}>
            <button
              type="button"
              className={styles.btnCancel || 'btnCancel'}
              onClick={onCancel}
            >
              Cancelar
            </button>
            <button
              type="submit"
              className={`${styles.btnSubmit || 'btnSubmit'} ${styles.btnSubmitEdicao || 'btnSubmitEdicao'}`}
            >
              Salvar
            </button>
          </div>
        ) : (
          <button
            type="submit"
            className={styles.btnSubmit || 'btnSubmit'}
            style={{ marginTop: '8px' }}
          >
            Cadastrar Curso
          </button>
        )}
      </form>
    </div>
  );
}