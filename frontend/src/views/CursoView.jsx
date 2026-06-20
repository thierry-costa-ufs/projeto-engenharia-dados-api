import { BookOpen } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import CursoForm from '../components/cursos/CursoForm';

export default function CursosView() {
  return (
    <ModuleViewLayout
      title="Módulo de Cursos"
      subtitle="Gerenciamento de Matrizes de Cursos Operacionais e Poliglotas."
      formTitle="Cadastrar Novo Curso"
      endpoint="cursos"
      FormComponent={CursoForm}
      tableHeaders={['ID', 'Nome do Curso', 'Turno / Nível', 'Campus']}
      renderRow={(curso, idx, sharedStyles) => (
        <tr key={curso.idCurso || idx} className={sharedStyles.tableRow}>
          <td className={sharedStyles.cpfCell}>{curso.idCurso}</td>
          <td className={sharedStyles.nameCell}>
            <BookOpen size={14} />
            <span>{curso.nome}</span>
          </td>
          <td>
            <span className={sharedStyles.emailBadge}>{curso.turno}</span> — {curso.nivel}
          </td>
          <td>{curso.campus}</td>
        </tr>
      )}
    />
  );
}