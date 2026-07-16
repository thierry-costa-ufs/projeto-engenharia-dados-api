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
            tableHeaders={['Código', 'Disciplina', 'Ano', 'Semestre']}
            renderRow={(turma, idx, sharedStyles, onEdit, onDelete) => (
                <tr key={turma.idTurma || idx} className={sharedStyles.tableRow}>
                    <td className={sharedStyles.textMono}>{turma.codigoTurma || turma.numero}</td>
                    <td>{turma.codigoDisciplina || turma.codDisciplina || turma.cod_disc}</td>
                    <td>{turma.ano}</td>
                    <td>{turma.semestre}</td>
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