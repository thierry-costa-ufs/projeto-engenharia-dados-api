import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { DepartamentoForm } from '../components/forms';

export default function DepartamentosView() {
  return (
    <ModuleViewLayout
      title="Módulo de Departamentos"
      subtitle="Gerenciamento dos centros acadêmicos e lotação de disciplinas da instituição."
      formTitle="Cadastrar Novo Departamento"
      endpoint="departamentos"
      FormComponent={DepartamentoForm}
      tableHeaders={['ID', 'Nome', 'Sigla']}
      renderRow={(depto, idx, sharedStyles, onEdit, onDelete) => (
        <tr key={depto.idDepartamento || idx} className={sharedStyles.tableRow}>
          <td>{depto.idDepartamento}</td>
          <td>{depto.nome}</td>
          <td className={sharedStyles.textMono}>{depto.sigla}</td>
          <td>
            <div className={sharedStyles.actionsCell}>
              <button
                type="button"
                title="Editar Departamento"
                onClick={() => onEdit(depto)}
                className={sharedStyles.btnTableEdit}
              >
                <Pencil size={14} />
              </button>
              <button
                type="button"
                title="Deletar Departamento"
                onClick={() => onDelete(depto.idDepartamento)}
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