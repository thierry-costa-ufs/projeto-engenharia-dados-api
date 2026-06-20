import { useState } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

const ESTADO_INICIAL = { matEstudante: '', codCurso: '', semestre: '', situacao: 'ATIVO' };

export default function VinculoForm({ onCadastrado }) {
  const [formData, setFormData] = useState(ESTADO_INICIAL);

  // Aponta dinamicamente para o endpoint de vinculos que criamos no backend
  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('vinculos', onCadastrado);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Converte e higieniza os dados conforme o DTO do Backend espera
    const payload = {
      matEstudante: formData.matEstudante,
      codCurso: Number(formData.codCurso),
      semestre: formData.semestre,
      situacao: formData.situacao
    };

    const resultado = await executarEscritaDupla(payload);
    if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
      setFormData(ESTADO_INICIAL);
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleSubmit} className={theme.form}>

        <div className={theme.row}>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Matrícula do Estudante</label>
            <input type="text" name="matEstudante" placeholder="Ex: E101" value={formData.matEstudante} onChange={handleChange} maxLength={7} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Código do Curso</label>
            <input type="number" name="codCurso" placeholder="Código numérico" value={formData.codCurso} onChange={handleChange} required />
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Semestre Vigente</label>
            <input type="text" name="semestre" placeholder="Ex: 2026.1" value={formData.semestre} onChange={handleChange} maxLength={6} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Situação Acadêmica</label>
            <select name="situacao" value={formData.situacao} onChange={handleChange} className={theme.selectField} required>
              <option value="ATIVO">ATIVO</option>
              <option value="TRANCADO">TRANCADO</option>
              <option value="FORMADO">FORMADO</option>
              <option value="EVADIDO">EVADIDO</option>
            </select>
          </div>
        </div>

        <button type="submit" className={theme.btnSubmit}>Dupla Inserção de Vínculo</button>
      </form>

      {/* Seu modal de resiliência interceptando falhas do Mongo para o vínculo */}
      <ResilienceModal aberto={modalAberto} entidade="Vínculo" onKeep={manterApenasNoPostgres} onRollback={executarRollback} />
    </div>
  );
}