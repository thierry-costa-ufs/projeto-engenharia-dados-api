import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { DisciplinaForm } from '../components/forms';

export default function DisciplinasView() {
    return (
        <ModuleViewLayout
            title="Módulo de Disciplinas"
            subtitle="Catálogo de componentes curriculares vinculados aos seus respectivos departamentos."
            formTitle="Cadastrar Nova Disciplina"
            endpoint="disciplinas"
            FormComponent={DisciplinaForm}
            tableHeaders={['Código', 'Nome', 'Pré-Requisito', 'Créditos', 'Departamento']}
            renderRow={(disc, idx, sharedStyles, onEdit, onDelete) => (
                <tr key={disc.codigoDisciplina || disc.cod_disc || idx} className={sharedStyles.tableRow}>
                    <td className={sharedStyles.textMono}>{disc.codigoDisciplina || disc.cod_disc}</td>
                    <td>{disc.nome}</td>
                    <td className={sharedStyles.textMono}>{disc.preReq || disc.pre_req || '—'}</td>
                    <td>{disc.creditos}</td>
                    <td>{disc.idDepartamento || disc.codDepartamento || disc.depto_responsavel || '—'}</td>
                    <td>
                        <div className={sharedStyles.actionsCell}>
                            <button
                                type="button"
                                title="Editar Disciplina"
                                onClick={() => onEdit(disc)}
                                className={sharedStyles.btnTableEdit}
                            >
                                <Pencil size={14} />
                            </button>
                            <button
                                type="button"
                                title="Deletar Disciplina"
                                onClick={() => onDelete(disc.codigoDisciplina || disc.cod_disc)}
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