import { Trash2, Edit } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import VinculoForm from '../components/vinculos/VinculoForm';

export default function VinculosView() {
  // Removido o 'Ações' manual, pois o ModuleViewLayout injeta o cabeçalho de ações automaticamente
  const headers = ['ID Vínculo', 'Matrícula', 'Código do Curso', 'Data de Entrada', 'Status', 'Data de Saída'];

  return (
    <ModuleViewLayout
      title="Módulo de Vínculos"
      subtitle="Associação e histórico de permanência de estudantes em cursos cadastrados."
      formTitle="Registrar Novo Vínculo"
      endpoint="vinculos"
      FormComponent={VinculoForm}
      tableHeaders={headers}
      renderRow={(vinculo, idx, sharedStyles, handleIniciarEdicao, handleDeletar) => {
        return (
          <tr key={vinculo.idVinculo || vinculo.id || idx} className={sharedStyles.tableRow}>
            {/* 1. ID Vínculo - Fallback seguro para garantir a renderização do número puro */}
            <td>
              {vinculo.idVinculo || vinculo.id || vinculo.idvinculo}
            </td>

            {/* 2. Matrícula */}
            <td>
              {vinculo.matEstudante}
            </td>

            {/* 3. Código do Curso */}
            <td>
              {vinculo.codCurso}
            </td>

            {/* 4. Data de Entrada */}
            <td>
              {vinculo.dataEntrada ? new Date(vinculo.dataEntrada).toLocaleDateString('pt-BR') : '-'}
            </td>

            {/* 5. Status - Cor corrigida para o tema Dark (#e6edf3) evitando o sumiço do texto */}
            <td>
              <span style={{ fontWeight: '600', color: '#e6edf3', display: 'inline-block' }}>
                {vinculo.status}
              </span>
            </td>

            {/* 6. Data de Saída */}
            <td>
              {vinculo.dataSaida ? new Date(vinculo.dataSaida).toLocaleDateString('pt-BR') : '-'}
            </td>

            {/* 7. Coluna de Ações unificada */}
            <td>
              <div className={sharedStyles.tableActions}>
                <button
                  type="button"
                  onClick={() => handleIniciarEdicao(vinculo)}
                  className="btnTableEdit"
                  style={{ marginRight: '8px', padding: '4px 8px', cursor: 'pointer' }}
                  title="Editar"
                >
                  <Edit size={16} />
                </button>
                <button
                  type="button"
                  onClick={() => handleDeletar(vinculo.idVinculo || vinculo.id)}
                  className="btnTableDelete"
                  style={{ padding: '4px 8px', color: '#ff4d4f', cursor: 'pointer' }}
                  title="Deletar"
                >
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