import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { ProfessorForm } from '../components/forms';
import { useSagaPersistence } from '../hooks/useSagaPersistence';

export default function ProfessorView() {
  const { executarEscritaDupla } = useSagaPersistence('professores');

  const formatarData = (dataString) => {
    if (!dataString) return '—';
    return dataString.split('T')[0].split('-').reverse().join('/');
  };

  return (
    <ModuleViewLayout
      title="Módulo: Corpo Docente"
      subtitle="Regimes de jornada de trabalho (20h, 40h, DE), salários e titulações acadêmicas."
      formTitle="Cadastrar Novo Professor"
      endpoint="professores"
      FormComponent={ProfessorForm}
      onSaveCustom={executarEscritaDupla}
      tableHeaders={[
        'Matrícula',
        'CPF',
        'Departamento',
        'Formação',
        'Data de Admissão',
        'Jornada',
        'Salário'
      ]}
      renderRow={(professor, idx, sharedStyles, onEdit, onDelete) => {
        const idProfessor = professor.matricula || professor.mat_professor;
        return (
          <tr key={idProfessor || idx} className={sharedStyles.tableRow}>
            <td>{idProfessor}</td>
            <td>{professor.cpf}</td>
            <td>{professor.departamento}</td>
            <td>{professor.formacao}</td>
            <td>{formatarData(professor.dataAdmissao || professor.data_admissao)}</td>
            <td>{professor.jornada || professor.tipo_jornada_trabalho}</td>
            <td>
              {professor.salario
                ? `R$ ${Number(professor.salario).toLocaleString('pt-BR', { minimumFractionDigits: 2 })}`
                : '—'}
            </td>
            <td>
              <div className={sharedStyles.actionsCell}>
                <button
                  type="button"
                  title="Editar Professor"
                  onClick={() => onEdit(professor)}
                  className={sharedStyles.btnTableEdit}
                >
                  <Pencil size={14} />
                </button>
                <button
                  type="button"
                  title="Deletar Professor"
                  onClick={() => onDelete(idProfessor)}
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