import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { TurmaForm } from '../components/forms';

export default function TurmasView() {
  return (
    <ModuleViewLayout
      title="Módulo de Turmas"
      subtitle="Alocação de horários, salas, atribuição docente e oferta de disciplinas."
      formTitle="Cadastrar Nova Turma"
      endpoint="turmas"
      FormComponent={TurmaForm}
      tableHeaders={['ID Turma', 'Código', 'Disciplina', 'Professor (Matrícula)', 'Horário', 'Sala']}
      renderRow={(turma, idx, sharedStyles, onEdit, onDelete) => (
        <tr key={turma.idTurma || idx} className={sharedStyles.tableRow}>
          <td>{turma.idTurma}</td>
          <td className={sharedStyles.textMono}>{turma.codigoTurma}</td>
          <td>{turma.codigoDisciplina || turma.codDisciplina}</td>
          <td className={sharedStyles.textMono}>{turma.matriculaProfessor || '—'}</td>
          <td>{turma.horario}</td>
          <td>{turma.sala || '—'}</td>
          <td>
            <div className={sharedStyles.actionsCell}>
              <button
                type="button"
                title="Editar Turma"
                onClick={() => onEdit(turma)}
                className={sharedStyles.btnTableEdit}
              >
                <Pencil size={14} />
              </button>
              <button
                type="button"
                title="Deletar Turma"
                onClick={() => onDelete(turma.idTurma)}
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