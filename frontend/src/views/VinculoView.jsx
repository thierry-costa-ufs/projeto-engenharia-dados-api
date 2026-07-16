import { Trash2, Pencil } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { VinculoForm } from '../components/forms';

export default function VinculosView() {
  const formatarData = (dataString) => {
    if (!dataString) return '—';
    return dataString.split('-').reverse().join('/');
  };

  return (
    <ModuleViewLayout
      title="Módulo de Vínculos"
      subtitle="Associação e histórico de permanência de estudantes em cursos cadastrados."
      formTitle="Registrar Novo Vínculo"
      endpoint="vinculos"
      FormComponent={VinculoForm}
      tableHeaders={[
        'Matrícula',
        'Código do Curso',
        'Data de Entrada',
        'Status',
        'Data de Saída'
      ]}
      renderRow={(vinculo, idx, sharedStyles, onEdit, onDelete) => {
        const idVinculo = vinculo.idVinculo || vinculo.id || vinculo.idvinculo;
        return (
          <tr key={idVinculo || idx} className={sharedStyles.tableRow}>
            <td>{vinculo.matEstudante}</td>
            <td>{vinculo.codCurso}</td>
            <td>{formatarData(vinculo.dataEntrada)}</td>
            <td>{vinculo.status || vinculo.situacao}</td>
            <td>{formatarData(vinculo.dataSaida)}</td>
            <td>
              <div className={sharedStyles.actionsCell}>
                <button
                  type="button"
                  title="Editar Vínculo"
                  onClick={() => onEdit(vinculo)}
                  className={sharedStyles.btnTableEdit}
                >
                  <Pencil size={14} />
                </button>
                <button
                  type="button"
                  title="Deletar Vínculo"
                  onClick={() => onDelete(idVinculo)}
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