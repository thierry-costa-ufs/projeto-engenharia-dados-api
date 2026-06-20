import { Paperclip, Trash2, Edit } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import VinculoForm from '../components/vinculos/VinculoForm';

export default function VinculosView() {
  // Alinhado com as colunas que realmente vamos renderizar
  const headers = ['ID Identificador', 'Matrícula', 'Cód. Curso', 'Situação', 'Sincronização'];

  return (
    <ModuleViewLayout
      title="Módulo de Vínculos"
      subtitle="Associação e histórico de permanência de estudantes em cursos cadastrados."
      formTitle="Registrar Novo Vínculo"
      endpoint="vinculos"
      FormComponent={VinculoForm}
      tableHeaders={headers}
      renderRow={(vinculo, idx, sharedStyles, handleIniciarEdicao, handleDeletar) => {
        // No Postgres usamos o id (Long), no MongoDB usamos o mongoId (String alfanumérica)
        const idExibido = vinculo.id || vinculo.mongoId || `Index: ${idx}`;

        return (
          <tr key={idExibido} className={sharedStyles.tableRow}>
            {/* 1. ID Correto */}
            <td className={sharedStyles.cpfCell}>
              <strong>{idExibido}</strong>
            </td>

            {/* 2. Matrícula com o ícone */}
            <td className={sharedStyles.nameCell}>
              <Paperclip size={14} style={{ marginRight: '6px', display: 'inline-block', verticalAlign: 'middle' }} />
              <span>{vinculo.matEstudante}</span>
            </td>

            {/* 3. Código do Curso (veio como codCurso do DTO) */}
            <td>Curso #{vinculo.codCurso}</td>

            {/* 4. Situação Acadêmica */}
            <td>
              <span className={`${sharedStyles.emailBadge} ${vinculo.situacao === 'ATIVO' ? sharedStyles.activeMongo : ''}`}>
                {vinculo.situacao}
              </span>
            </td>

            {/* 5. Status da Replicação Dupla Síncrona */}
            <td>
              <span className={`${sharedStyles.statusBadge} ${vinculo.statusExecucao === 'SUCESSO_TOTAL' ? sharedStyles.badgeSucesso : sharedStyles.badgeAlerta}`}>
                {vinculo.statusExecucao}
              </span>
            </td>

            {/* 6. Coluna de Ações Obrigatória para o ModuleViewLayout funcionar */}
            <td>
              <div className={sharedStyles.tableActions}>
                <button type="button" onClick={() => handleIniciarEdicao(vinculo)} className={sharedStyles.btnIconEdit} title="Editar">
                  <Edit size={16} />
                </button>
                <button type="button" onClick={() => handleDeletar(vinculo.id)} className={sharedStyles.btnIconDelete} title="Deletar">
                  <Trash2 size={16} />
                </button>
              </div>
            </td>
          </tr>
        );
      }}
    />
  );
}