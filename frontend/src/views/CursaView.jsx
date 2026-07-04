import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { CursaForm } from '../components/forms';

export default function CursaView() {
  const formatarNota = (nota) => {
    if (nota === undefined || nota === null || nota === '') return '—';
    return Number(nota).toFixed(1);
  };

  return (
    <ModuleViewLayout
      title="Módulo de Avaliações e Notas (Cursa)"
      subtitle="Lançamento de notas, apuração de médias parciais e controle de frequência do corpo discente."
      formTitle="Registrar Matrícula/Nota"
      endpoint="cursa"
      FormComponent={CursaForm}
      tableHeaders={[
        'Estudante (Matrícula)',
        'ID Turma',
        'Nota 1',
        'Nota 2',
        'Nota 3',
        'Média',
        'Falta(s)',
        'Situação'
      ]}
      renderRow={(registro, idx, sharedStyles, onEdit, onDelete) => {
        const idChaveUnica = registro.idCursa || `${registro.matEstudante}/${registro.idTurma}`;

        return (
          <tr key={idChaveUnica || idx} className={sharedStyles.tableRow}>
            <td className={sharedStyles.textMono}>{registro.matEstudante}</td>
            <td>{registro.idTurma}</td>
            <td className={sharedStyles.textMono}>{formatarNota(registro.nota1)}</td>
            <td className={sharedStyles.textMono}>{formatarNota(registro.nota2)}</td>
            <td className={sharedStyles.textMono}>{formatarNota(registro.nota3)}</td>
            <td className={sharedStyles.textMonoBold}>
              {formatarNota(registro.media)}
            </td>
            <td>{registro.frequencia ?? registro.faltas ?? 0}</td>
            <td>
              <span className={
                registro.situacao === 'Aprovado'
                  ? sharedStyles.statusAprovado
                  : registro.situacao === 'Reprovado'
                    ? sharedStyles.statusReprovado
                    : sharedStyles.statusEmCurso
              }>
                {registro.situacao || 'Em Curso'}
              </span>
            </td>
            <td>
              <div className={sharedStyles.actionsCell}>
                <button
                  type="button"
                  title="Editar Notas/Frequência"
                  onClick={() => onEdit(registro)}
                  className={sharedStyles.btnTableEdit}
                >
                  <Pencil size={14} />
                </button>
                <button
                  type="button"
                  title="Remover Registro"
                  onClick={() => onDelete(idChaveUnica)}
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