import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { EstudanteForm } from '../components/forms';

export default function EstudantesView() {
  return (
    <ModuleViewLayout
      title="Modulo de Estudantes"
      subtitle="Controle de registros academicos ativos, media de conclusao e ano de ingresso."
      formTitle="Cadastrar Novo Estudante"
      endpoint="estudantes"
      FormComponent={EstudanteForm}
      tableHeaders={['Matricula', 'CPF do Usuario', 'Media', 'Ano de Ingresso']}
      renderRow={(estudante, idx, sharedStyles, onEdit, onDelete) => {
        const matricula = estudante.matEstudante || estudante.matricula;

        return (
          <tr key={matricula || idx} className={sharedStyles.tableRow}>
            <td className={sharedStyles.textMono}>{matricula || '-'}</td>
            <td>{estudante.cpf}</td>
            <td>{estudante.mc ?? '-'}</td>
            <td>{estudante.anoIngresso ?? '-'}</td>
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
                  onClick={() => onDelete(matricula)}
                  className={sharedStyles.btnTableDelete}
                >
                  <Trash2 size={14} />
                </button>
              </div>
            </td>
          </tr>
        );
      }}
    />
  );
}
