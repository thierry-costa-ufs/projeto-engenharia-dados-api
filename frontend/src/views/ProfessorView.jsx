import React from 'react';

export default function ProfessorView() {
  const mockProfessores = [
    { mat_professor: 'P100', cpf: '11111111100', departamento: 'DCOMP', formacao: 'Doutorado', tipo_jornada_trabalho: '20h', salario: 2000.00 }
  ];

  return (
    <div style={{ padding: '24px', maxWidth: '1200px', margin: '0 auto' }}>
      <div style={{ display: 'flex', justifyContent: 'between', alignItems: 'center', borderBottom: '1px solid #e5e7eb', paddingBottom: '16px', marginBottom: '24px' }}>
        <div>
          <h1 style={{ fontSize: '24px', fontWeight: 'bold', color: '#1f2937' }}>Módulo: Corpo Docente</h1>
          <p style={{ fontSize: '14px', color: '#6b7280' }}>Regimes de jornada de trabalho (20h, 40h, DE), salários e titulações.</p>
        </div>
        <span style={{ padding: '4px 12px', backgroundColor: '#fef3c7', color: '#92400e', fontSize: '12px', fontWeight: '600', borderRadius: '9999px', textTransform: 'uppercase' }}>
          Placeholder / Sem Backend
        </span>
      </div>

      <div style={{ backgroundColor: '#ffffff', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0,0,0,0.1)', overflow: 'hidden', opacity: 0.6, pointerEvents: 'none' }}>
        <table style={{ minWidth: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
          <thead style={{ backgroundColor: '#f9fafb', fontSize: '12px', textTransform: 'uppercase', color: '#6b7280' }}>
            <tr>
              <th style={{ padding: '12px 24px' }}>Matrícula</th>
              <th style={{ padding: '12px 24px' }}>CPF Usuário</th>
              <th style={{ padding: '12px 24px' }}>Departamento</th>
              <th style={{ padding: '12px 24px' }}>Formação</th>
              <th style={{ padding: '12px 24px' }}>Jornada</th>
              <th style={{ padding: '12px 24px' }}>Salário</th>
            </tr>
          </thead>
          <tbody style={{ fontSize: '14px', color: '#4b5563' }}>
            {mockProfessores.map((p) => (
              <tr key={p.mat_professor} style={{ borderTop: '1px solid #e5e7eb' }}>
                <td style={{ padding: '16px 24px', fontFamily: 'monospace' }}>{p.mat_professor}</td>
                <td style={{ padding: '16px 24px' }}>{p.cpf}</td>
                <td style={{ padding: '16px 24px' }}>{p.departamento}</td>
                <td style={{ padding: '16px 24px' }}>{p.formacao}</td>
                <td style={{ padding: '16px 24px' }}>{p.tipo_jornada_trabalho}</td>
                <td style={{ padding: '16px 24px' }}>R$ {p.salario.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}