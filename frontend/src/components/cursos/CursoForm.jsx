import { useState } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

const ESTADO_INICIAL = { nome: '', grau: 'Bacharelado', turno: 'Matutino', campus: '', nivel: 'Graduação' };

export default function CursoForm({ onCadastrado }) {
  const [formData, setFormData] = useState(ESTADO_INICIAL);
  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('cursos', onCadastrado);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const resultado = await executarEscritaDupla(formData);
    if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
      setFormData(ESTADO_INICIAL);
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleSubmit} className={theme.form}>

        <div className={theme.row}>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Nome do Curso</label>
            <input type="text" name="nome" placeholder="Ex: Ciência da Computação" value={formData.nome} onChange={handleChange} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Campus</label>
            <input type="text" name="campus" placeholder="Ex: São Cristóvão" value={formData.campus} onChange={handleChange} required />
          </div>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Grau Acadêmico</label>
          <select name="grau" value={formData.grau} onChange={handleChange}>
            <option value="Bacharelado">Bacharelado</option>
            <option value="Licenciatura Plena">Licenciatura Plena</option>
          </select>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Turno</label>
          <select name="turno" value={formData.turno} onChange={handleChange}>
            <option value="Matutino">Matutino</option>
            <option value="Vespertino">Vespertino</option>
            <option value="Noturno">Noturno</option>
            <option value="Turno Indefinido">Turno Indefinido</option>
          </select>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Nível</label>
          <select name="nivel" value={formData.nivel} onChange={handleChange}>
            <option value="Graduação">Graduação</option>
            <option value="Mestrado">Mestrado</option>
            <option value="Doutorado">Doutorado</option>
            <option value="Lato">Lato</option>
          </select>
        </div>

        <button type="submit" className={theme.btnSubmit}>Dupla Inserção</button>
      </form>
      <ResilienceModal aberto={modalAberto} entidade="Curso" onKeep={manterApenasNoPostgres} onRollback={executarRollback} />
    </div>
  );
}