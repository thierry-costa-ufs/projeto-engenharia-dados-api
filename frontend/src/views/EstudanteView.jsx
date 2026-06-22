import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { EstudanteForm } from '../components/forms';

export default function EstudantesView() {
  const formatarData = (dataString) => {
    if (!dataString) return '—';
    return dataString.split('-').reverse().join('/');
  };

  return (
    <ModuleViewLayout
      title="Módulo de Estudantes"
      subtitle="Controle de registros acadêmicos ativos, histórico de ingresso e vinculação base."
      formTitle="Cadastrar Novo Estudante"
      endpoint="estudantes"
      FormComponent={EstudanteForm}
      tableHeaders={['Matrícula', 'CPF do Usuário', 'Data de Ingresso', 'Status']}
      renderRow={(estudante, idx, sharedStyles, onEdit, onDelete) => (
        <tr key={estudante.matricula || idx} className={sharedStyles.tableRow}>
          <td className={sharedStyles.textMono}>{estudante.matricula}</td>
          <td>{estudante.cpf}</td>
          <td>{formatarData(estudante.dataIngresso)}</td>
          <td>
            <span style={{
              color: estudante.status === 'Ativo' ? '#10b981' : '#64748b',
              fontWeight: '600'
            }}>
              {estudante.status || 'Ativo'}
            </span>
          </td>
          <td>
            <div className={sharedStyles.actionsCell}>
              <button
                type="button"
                title="Editar Estudante"
                onClick={() => onEdit(estudante)}
                className={sharedStyles.btnTableEdit}
              >
                <Pencil size={14} />
              </button>
              <button
                type="button"
                title="Deletar Estudante"
                onClick={() => onDelete(estudante.matricula)}
                className={sharedStyles.btnTableDelete}
              >
                <Trash2 size={14} />
              </button>
            </div>
          </td>
        </tr>
      )}
    />
  );
}