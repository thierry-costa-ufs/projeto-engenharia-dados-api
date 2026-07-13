import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { CursoForm } from '../components/forms';

export default function CursoView() {
  const formatarEnum = (valor) => {
    if (!valor) return '—';
    return valor.replaceAll('_', ' ').toLowerCase().replace(/(^\w|\s\w)/g, m => m.toUpperCase());
  };

  return (
    <ModuleViewLayout
      title="Módulo: Cursos"
      subtitle="Gerenciamento de matrizes curriculares, níveis acadêmicos, turnos e alocações de campus."
      formTitle="Cadastrar Novo Curso"
      endpoint="cursos"
      FormComponent={CursoForm}
      tableHeaders={[
        'ID',
        'Nome do Curso',
        'Grau',
        'Turno',
        'Campus',
        'Nível'
      ]}
      renderRow={(curso, idx, sharedStyles, onEdit, onDelete) => (
        <tr key={curso.idCurso || idx} className={sharedStyles.tableRow}>
          <td>{curso.idCurso}</td>
          <td>{curso.nome}</td>
          <td>{formatarEnum(curso.grau)}</td>
          <td>{formatarEnum(curso.turno)}</td>
          <td>{curso.campus || 'Não Informado'}</td>
          <td>{formatarEnum(curso.nivel)}</td>
          <td>
            <div className={sharedStyles.actionsCell}>
              <button
                type="button"
                title="Editar Curso"
                onClick={() => onEdit(curso)}
                className={sharedStyles.btnTableEdit}
              >
                <Pencil size={14} />
              </button>
              <button
                type="button"
                title="Deletar Curso"
                onClick={() => onDelete(curso.idCurso)}
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