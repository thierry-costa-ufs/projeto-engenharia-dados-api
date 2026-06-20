import { useState } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

const ESTADO_INICIAL = { matEstudante: '', curso: '', dataEntrada: '', status: 'Ativo', dataSaida: '' };

export default function VinculoForm({ onCadastrado }) {
  const [formData, setFormData] = useState(ESTADO_INICIAL);
  const { modalAberto, executarEscritaDupla, executarRollback, manterApenasNoPostgres } =
    useSagaPersistence('vinculos', onCadastrado);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const payload = {
      ...formData,
      curso: Number(formData.curso),
      dataSaida: formData.dataSaida || null
    };

    const resultado = await executarEscritaDupla(payload);
    if (resultado.status === 'SUCESSO' || resultado.status === 'FALHA_PARCIAL') {
      setFormData(ESTADO_INICIAL);
    }
  };

  return (
    <div className={theme.formContainer}>
      <form onSubmit={handleSubmit} className={theme.form}>

        {/* Usando o padrão de colunas estruturadas para manter labels alinhadas */}
        <div className={theme.row}>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Estudante</label>
            <input type="text" name="matEstudante" placeholder="Matrícula (7 dígitos)" value={formData.matEstudante} onChange={handleChange} maxLength={7} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Curso</label>
            <input type="number" name="curso" placeholder="ID do Curso" value={formData.curso} onChange={handleChange} required />
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Data de Entrada</label>
            <input type="date" name="dataEntrada" value={formData.dataEntrada} onChange={handleChange} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Data de Saída (Opcional)</label>
            <input type="date" name="dataSaida" value={formData.dataSaida} onChange={handleChange} />
          </div>
        </div>

        <div className={theme.column}>
          <label className={theme.fieldLabel}>Status do Vínculo</label>
          <select name="status" value={formData.status} onChange={handleChange}>
            <option value="Ativo">Ativo</option>
            <option value="Cancelada">Cancelada</option>
            <option value="Formando">Formando</option>
            <option value="Graduado">Graduado</option>
          </select>
        </div>

        <button type="submit" className={theme.btnSubmit}>Dupla Inserção</button>
      </form>
      <ResilienceModal aberto={modalAberto} entidade="Vínculo Acadêmico" onKeep={manterApenasNoPostgres} onRollback={executarRollback} />
    </div>
  );
}