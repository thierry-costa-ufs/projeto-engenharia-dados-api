import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { DepartamentoForm } from '../components/forms';

export default function DepartamentosView() {
  const formatarMoeda = (valor) => {
    if (valor === null || valor === undefined || valor === '') return '-';

    return Number(valor).toLocaleString('pt-BR', {
      style: 'currency',
      currency: 'BRL'
    });
  };

  return (
    <ModuleViewLayout
      title="Módulo de Departamentos"
      subtitle="Gerenciamento dos departamentos acadêmicos, chefias, orçamentos e comissões."
      formTitle="Cadastrar Novo Departamento"
      endpoint="departamentos"
      FormComponent={DepartamentoForm}
      tableHeaders={['Código', 'Nome', 'Chefe', 'Orçamento', 'Comissão']}
      renderRow={(departamento, idx, sharedStyles, onEdit, onDelete) => {
        const codigo = departamento.codDepto || departamento.idDepartamento;

        return (
          <tr key={codigo || idx} className={sharedStyles.tableRow}>
            <td className={sharedStyles.textMono}>{codigo || '-'}</td>
            <td>{departamento.nome}</td>
            <td className={sharedStyles.textMono}>{departamento.chefe || '-'}</td>
            <td>{formatarMoeda(departamento.orcamento)}</td>
            <td>{formatarMoeda(departamento.comissao)}</td>
            <td>
              <div className={sharedStyles.actionsCell}>
                <button
                  type="button"
                  title="Editar Departamento"
                  onClick={() => onEdit(departamento)}
                  className={sharedStyles.btnTableEdit}
                >
                  <Pencil size={14} />
                </button>
                <button
                  type="button"
                  title="Deletar Departamento"
                  onClick={() => onDelete(codigo)}
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
