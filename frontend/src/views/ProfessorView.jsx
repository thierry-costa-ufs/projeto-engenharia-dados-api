import React from 'react';
import { Calendar, Pencil, Trash2, Shield, GraduationCap, DollarSign } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import ProfessorForm from '../components/professores/ProfessorForm';
import { useSagaPersistence } from '../hooks/useSagaPersistence';

export default function ProfessorView() {
  const { executarEscritaDupla } = useSagaPersistence('professores');

  return (
    <ModuleViewLayout
      title="Módulo: Corpo Docente"
      subtitle="Regimes de jornada de trabalho (20h, 40h, DE), salários e titulações acadêmicas."
      formTitle="Cadastrar Novo Professor"
      endpoint="professores"
      FormComponent={ProfessorForm}
      onSaveCustom={executarEscritaDupla}
      tableHeaders={[
        'Matrícula',
        'CPF',
        'Departamento',
        'Formação',
        'Data de Admissão',
        'Jornada',
        'Salário'
      ]}
      renderRow={(professor, idx, sharedStyles, onEdit, onDelete) => (
        <tr key={professor.matricula || idx} className={sharedStyles.tableRow}>
          {/* mat_professor */}
          <td className="px-4 py-3 text-sm font-mono text-white font-semibold">
            {professor.matricula || professor.mat_professor}
          </td>

          {/* cpf */}
          <td className={sharedStyles.cpfCell}>
            {professor.cpf}
          </td>

          {/* departamento */}
          <td className="px-4 py-3 text-sm font-mono text-slate-300">
            {professor.departamento}
          </td>

          {/* formacao */}
          <td className="px-4 py-3 text-sm">
            <span className="inline-flex items-center gap-1 text-xs bg-slate-800 text-blue-400 px-2 py-0.5 rounded border border-slate-700">
              <GraduationCap size={12} />
              {professor.formacao}
            </span>
          </td>

          {/* data_admissao */}
          <td className="px-4 py-3 text-sm text-slate-300">
            <div className="flex items-center gap-1">
              <Calendar size={13} className="text-slate-400" />
              <span>
                {professor.dataAdmissao || professor.data_admissao
                  ? new Date(professor.dataAdmissao || professor.data_admissao).toLocaleDateString('pt-PT')
                  : '—'}
              </span>
            </div>
          </td>

          {/* tipo_jornada_trabalho */}
          <td className="px-4 py-3 text-sm">
            <span className="inline-flex items-center gap-1 text-xs bg-slate-800 text-emerald-400 px-2 py-0.5 rounded border border-slate-700">
              <Shield size={12} />
              {professor.jornada || professor.tipo_jornada_trabalho}
            </span>
          </td>

          {/* salario */}
          <td className="px-4 py-3 text-sm text-slate-200 font-medium">
            <div className="flex items-center gap-0.5 text-emerald-500">
              <DollarSign size={13} />
              <span>{Number(professor.salario).toLocaleString('pt-PT', { minimumFractionDigits: 2 })}</span>
            </div>
          </td>

          {/* Ações */}
          <td className="px-4 py-3 text-sm">
            <div style={{ display: 'flex', gap: '12px', alignItems: 'center' }}>
              <button
                type="button"
                title="Editar Professor"
                onClick={() => onEdit(professor)}
                className={sharedStyles.btnTableEdit}
                style={{ background: 'none', border: 'none' }}
              >
                <Pencil size={14} />
              </button>
              <button
                type="button"
                title="Deletar Professor"
                onClick={() => onDelete(professor.matricula || professor.mat_professor)}
                className={sharedStyles.btnTableDelete}
                style={{ background: 'none', border: 'none' }}
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