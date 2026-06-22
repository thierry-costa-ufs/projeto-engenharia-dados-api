import { useState, useEffect } from 'react';
import { cursoSchema } from '../../utils/validators'; // IMPORT VIA BARREL
import theme from '../../styles/FormTheme.module.css';

const ESTADO_INICIAL = {
  idCurso: null, nome: '', grau: 'BACHARELADO', turno: 'MATUTINO', campus: '', nivel: 'GRADUACAO'
};

export default function CursoForm({ onSubmit, initialData, isEditing, onCancel }) {
  const [formData, setFormData] = useState(ESTADO_INICIAL);
  const [errors, setErrors] = useState({});

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

    const validacao = cursoSchema.safeParse(formData);

    if (!validacao.success) {
      const mapeamentoErros = {};
      validacao.error.issues.forEach(issue => {
        mapeamentoErros[issue.path[0]] = issue.message;
      });
      setErrors(mapeamentoErros);
      return;
    }

    onSubmit({ ...formData, ...validacao.data });
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleLocalSubmit} className={theme.form}>
        <div className={theme.column}>
          <label className={theme.fieldLabel}>Nome do Curso</label>
          <input
            type="text"
            name="nome"
            value={formData.nome}
            onChange={handleChange}
            maxLength={100}
            required
            placeholder="Ex: Ciência da Computação"
          />
          {errors.nome && <span className={theme.errorText}>{errors.nome}</span>}
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Campus / Localização</label>
          <input
            type="text"
            name="campus"
            value={formData.campus}
            onChange={handleChange}
            maxLength={100}
            required
            placeholder="Ex: São Cristóvão"
          />
          {errors.campus && <span className={theme.errorText}>{errors.campus}</span>}
        </div>

        <div className={theme.row}>
          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Graduação / Grau</label>
            <select name="grau" value={formData.grau} onChange={handleChange} required>
              <option value="BACHARELADO">Bacharelado</option>
              <option value="LICENCIATURA_PLENA">Licenciatura Plena</option>
            </select>
            {errors.grau && <span className={theme.errorText}>{errors.grau}</span>}
          </div>

          <div className={theme.column} style={{ flex: 1 }}>
            <label className={theme.fieldLabel}>Turno</label>
            <select name="turno" value={formData.turno} onChange={handleChange} required>
              <option value="MATUTINO">Matutino</option>
              <option value="VESPERTINO">Vespertino</option>
              <option value="NOTURNO">Noturno</option>
              <option value="TURNO_INDEFINIDO">Turno Indefinido</option>
            </select>
            {errors.turno && <span className={theme.errorText}>{errors.turno}</span>}
          </div>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Nível Acadêmico</label>
          <select name="nivel" value={formData.nivel} onChange={handleChange} required>
            <option value="GRADUACAO">Graduação</option>
            <option value="MESTRADO">Mestrado</option>
            <option value="DOUTORADO">Doutorado</option>
            <option value="LATO">Lato Sensu (Plano)</option>
          </select>
          {errors.nivel && <span className={theme.errorText}>{errors.nivel}</span>}
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
            Cadastrar Curso
          </button>
        )}
      </form>
    </div>
  );
}