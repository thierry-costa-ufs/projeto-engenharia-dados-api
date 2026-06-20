import { useState } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

const ESTADO_INICIAL = { matEstudante: '', cpf: '', mc: '', anoIngresso: '' };

export default function EstudanteForm({ onCadastrado }) {
  const [formData, setFormData] = useState(ESTADO_INICIAL);
  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('estudantes', onCadastrado);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      matEstudante: formData.matEstudante,
      cpf: Number(formData.cpf),
      mc: Number(formData.mc),
      anoIngresso: Number(formData.anoIngresso)
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
            <label className={theme.fieldLabel}>Matrícula</label>
            <input type="text" name="matEstudante" placeholder="Ex: E102" value={formData.matEstudante} onChange={handleChange} maxLength={7} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>CPF Vinculado</label>
            <input type="number" name="cpf" placeholder="Apenas números" value={formData.cpf} onChange={handleChange} required />
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Média Conclusão (MC)</label>
            <input type="number" name="mc" step="0.1" placeholder="0.0 a 10.0" value={formData.mc} onChange={handleChange} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Ano de Ingresso</label>
            <input type="number" name="anoIngresso" placeholder="Ano (AAAA)" value={formData.anoIngresso} onChange={handleChange} required />
          </div>
        </div>

        <button type="submit" className={theme.btnSubmit}>Dupla Inserção</button>
      </form>
      <ResilienceModal aberto={modalAberto} entidade="Estudante" onKeep={manterApenasNoPostgres} onRollback={executarRollback} />
    </div>
  );
}