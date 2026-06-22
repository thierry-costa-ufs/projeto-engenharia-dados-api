import { useState } from 'react';
import { useSagaPersistence } from '../../hooks/useSagaPersistence';
import ResilienceModal from '../shared/ResilienceModal';
import theme from '../../styles/FormTheme.module.css';

const ESTADO_INICIAL = {
  matEstudante: '',
  codCurso: '',
  dataEntrada: '',
  status: 'Ativo',
  dataSaida: ''
};

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
      matEstudante: formData.matEstudante,
      codCurso: Number(formData.codCurso),
      dataEntrada: formData.dataEntrada || null,
      status: formData.status,
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
            <label className={theme.fieldLabel}>Data de Entrada</label>
            <input type="date" name="dataEntrada" value={formData.dataEntrada} onChange={handleChange} required />
          </div>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Data de Saída (Opcional)</label>
            <input type="date" name="dataSaida" value={formData.dataSaida} onChange={handleChange} />
          </div>
        </div>

        <div className={theme.row}>
          <div className={theme.column}>
            <label className={theme.fieldLabel}>Status Acadêmico</label>
            <select name="status" value={formData.status} onChange={handleChange} className={theme.selectField} required>
              <option value="Ativo">Ativo</option>
              <option value="Cancelada">Cancelada</option>
              <option value="Formando">Formando</option>
              <option value="Graduado">Graduado</option>
            </select>
          </div>
        </div>

        <button type="submit" className={theme.btnSubmit}>Dupla Inserção de Vínculo</button>
      </form>

      <ResilienceModal aberto={modalAberto} entidade="Vínculo" onKeep={manterApenasNoPostgres} onRollback={executarRollback} />
    </div>
  );
}