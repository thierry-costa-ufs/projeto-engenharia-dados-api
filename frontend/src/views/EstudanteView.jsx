import { GraduationCap } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import EstudanteForm from '../components/estudantes/EstudanteForm';

export default function EstudantesView() {
  return (
    <ModuleViewLayout
      title="Módulo de Estudantes"
      subtitle="Gerenciamento de registros estudantis acadêmicos integrados com escrita dupla."
      formTitle="Cadastrar Novo Estudante"
      endpoint="estudantes"
      FormComponent={EstudanteForm}
      tableHeaders={['Matrícula', 'CPF Associado', 'MC', 'Ano Ingresso']}
      renderRow={(estudante, idx, sharedStyles) => (
        <tr key={estudante.matEstudante || idx} className={sharedStyles.tableRow}>
          <td className={sharedStyles.nameCell}>
            <GraduationCap size={14} />
            <span>{estudante.matEstudante}</span>
          </td>
          <td className={sharedStyles.cpfCell}>{estudante.cpf}</td>
          <td>{estudante.mc ? estudante.mc.toFixed(1) : '0.0'}</td>
          <td>{estudante.anoIngresso}</td>
        </tr>
      )}
    />
  );
}