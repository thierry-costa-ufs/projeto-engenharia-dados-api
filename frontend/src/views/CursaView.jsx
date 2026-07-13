import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { CursaForm } from '../components/forms';

export default function CursaView() {
    const formatarNota = (nota) => {
        if (nota === undefined || nota === null || nota === '') return '—';
        return Number(nota).toFixed(1);
    };

    return (
        <ModuleViewLayout
            title="Módulo de Avaliações e Notas (Cursa)"
            subtitle="Lançamento de notas, apuração de médias parciais e controle de frequência do corpo discente."
            formTitle="Registrar Matrícula/Nota"
            endpoint="cursa"
            FormComponent={CursaForm}
            tableHeaders={[
                'Estudante (Matrícula)',
                'ID Turma',
                'Nota'
            ]}
            renderRow={(registro, idx, sharedStyles, onEdit, onDelete) => {
                // Usa as chaves corretas que vêm do banco/DTO
                const idChaveUnica = registro.idCursa || `${registro.matEstudante || registro.mat_estudante}/${registro.idTurma || registro.id_turma}`;

                return (
                    <tr key={idChaveUnica || idx} className={sharedStyles.tableRow}>
                        <td className={sharedStyles.textMono}>{registro.matEstudante || registro.mat_estudante}</td>
                        <td>{registro.idTurma || registro.id_turma}</td>
                        <td className={sharedStyles.textMonoBold}>{formatarNota(registro.nota)}</td>
                        <td>
                            <div className={sharedStyles.actionsCell}>
                                <button
                                    type="button"
                                    title="Editar Notas"
                                    onClick={() => onEdit(registro)}
                                    className={sharedStyles.btnTableEdit}
                                >
                                    <Pencil size={14} />
                                </button>
                                <button
                                    type="button"
                                    title="Remover Registro"
                                    onClick={() => onDelete(idChaveUnica)}
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