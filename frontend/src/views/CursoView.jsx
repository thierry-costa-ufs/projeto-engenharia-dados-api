import React from 'react';
import { BookOpen, Pencil, Trash2, Clock, MapPin, Award, Layers } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import CursoForm from '../components/cursos/CursoForm';

export default function CursoView() {
  // Helper para formatar a exibição amigável dos Enums vindos da API
  const formatarEnum = (valor) => {
    if (!valor) return '—';
    return valor.replace('_', ' ').toLowerCase().replace(/(^\w|\s\w)/g, m => m.toUpperCase());
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
          {/* 1. ID (SERIAL gerado pelo Postgres) */}
          <td className="px-4 py-3 text-sm font-mono text-slate-400">
            #{curso.idCurso}
          </td>

          {/* 2. Nome do Curso */}
          <td className={sharedStyles.nameCell}>
            <BookOpen size={14} className="text-blue-400 mr-1" />
            <span className="font-semibold text-white">{curso.nome}</span>
          </td>

          {/* 3. Grau Acadêmico */}
          <td className="px-4 py-3 text-sm">
            <span className="inline-flex items-center gap-1 text-xs bg-slate-800 text-slate-300 px-2 py-0.5 rounded border border-slate-700">
              <Layers size={12} className="text-slate-400" />
              {formatarEnum(curso.grau)}
            </span>
          </td>

          {/* 4. Turno */}
          <td className="px-4 py-3 text-sm">
            <span className="inline-flex items-center gap-1 text-xs bg-slate-800 text-amber-400 px-2 py-0.5 rounded border border-slate-700">
              <Clock size={12} />
              {formatarEnum(curso.turno)}
            </span>
          </td>

          {/* 5. Campus */}
          <td className="px-4 py-3 text-sm text-slate-300">
            <div className="flex items-center gap-1">
              <MapPin size={13} className="text-slate-500" />
              <span>{curso.campus || 'Não Informado'}</span>
            </div>
          </td>

          {/* 6. Nível */}
          <td className="px-4 py-3 text-sm">
            <span className="inline-flex items-center gap-1 text-xs bg-slate-800 text-purple-400 px-2 py-0.5 rounded border border-slate-700">
              <Award size={12} />
              {formatarEnum(curso.nivel)}
            </span>
          </td>

          {/* 7. Célula Operacional Automatizada */}
          <td className="px-4 py-3 text-sm">
            <div style={{ display: 'flex', gap: '12px', alignItems: 'center' }}>
              <button
                type="button"
                title="Editar Curso"
                onClick={() => onEdit(curso)}
                className={sharedStyles.btnTableEdit}
                style={{ background: 'none' }}
              >
                <Pencil size={14} />
              </button>
              <button
                type="button"
                title="Deletar Curso"
                onClick={() => onDelete(curso.idCurso)}
                className={sharedStyles.btnTableDelete}
                style={{ background: 'none' }}
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