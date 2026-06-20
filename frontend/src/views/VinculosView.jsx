import { Paperclip } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import VinculoForm from '../components/vinculos/VinculoForm';

export default function VinculosView() {
  return (
    <ModuleViewLayout
      title="Módulo de Vínculos"
      subtitle="Associação e histórico de permanência de estudantes em cursos cadastrados."
      formTitle="Registrar Novo Vínculo"
      endpoint="vinculos"
      FormComponent={VinculoForm}
      tableHeaders={['ID Vínculo', 'Matrícula', 'Cód. Curso', 'Status']}
      renderRow={(vinculo, idx, sharedStyles) => (
        <tr key={vinculo.idVinculo || idx} className={sharedStyles.tableRow}>
          <td className={sharedStyles.cpfCell}>{vinculo.idVinculo}</td>
          <td className={sharedStyles.nameCell}>
            <Paperclip size={14} />
            <span>{vinculo.matEstudante}</span>
          </td>
          <td>Curso #{vinculo.curso}</td>
          <td>
            <span className={`${sharedStyles.emailBadge} ${vinculo.status === 'Ativo' ? sharedStyles.activeMongo : ''}`}>
              {vinculo.status}
            </span>
          </td>
        </tr>
      )}
    />
  );
}