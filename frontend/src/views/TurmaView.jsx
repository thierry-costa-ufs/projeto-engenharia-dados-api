import React from 'react';

export default function TurmaView() {
  const mockTurmas = [
    { id_turma: 1, cod_disc: 'COMP0212', numero: 1, ano: 2017, semestre: 2 }
  ];

  return (
    <div style={{ padding: '24px', maxWidth: '1200px', margin: '0 auto' }}>
      <div style={{ display: 'flex', justifyContent: 'between', alignItems: 'center', borderBottom: '1px solid #e5e7eb', paddingBottom: '16px', marginBottom: '24px' }}>
        <div>
          <h1 style={{ fontSize: '24px', fontWeight: 'bold', color: '#1f2937' }}>Módulo: Turmas</h1>
          <p style={{ fontSize: '14px', color: '#6b7280' }}>Seções e ofertas de disciplinas ativas organizadas por ano letivo e período.</p>
        </div>
        <span style={{ padding: '4px 12px', backgroundColor: '#fef3c7', color: '#92400e', fontSize: '12px', fontWeight: '600', borderRadius: '9999px', textTransform: 'uppercase' }}>
          Placeholder / Sem Backend
        </span>
      </div>

      <div style={{ backgroundColor: '#ffffff', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0,0,0,0.1)', overflow: 'hidden', opacity: 0.6, pointerEvents: 'none' }}>
        <table style={{ minWidth: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
          <thead style={{ backgroundColor: '#f9fafb', fontSize: '12px', textTransform: 'uppercase', color: '#6b7280' }}>
            <tr>
              <th style={{ padding: '12px 24px' }}>ID Turma</th>
              <th style={{ padding: '12px 24px' }}>Cód. Disciplina</th>
              <th style={{ padding: '12px 24px' }}>Número (Turma)</th>
              <th style={{ padding: '12px 24px' }}>Ano Letivo</th>
              <th style={{ padding: '12px 24px' }}>Semestre</th>
            </tr>
          </thead>
          <tbody style={{ fontSize: '14px', color: '#4b5563' }}>
            {mockTurmas.map((t) => (
              <tr key={t.id_turma} style={{ borderTop: '1px solid #e5e7eb' }}>
                <td style={{ padding: '16px 24px' }}>{t.id_turma}</td>
                <td style={{ padding: '16px 24px', fontFamily: 'monospace' }}>{t.cod_disc}</td>
                <td style={{ padding: '16px 24px' }}>{t.numero}</td>
                <td style={{ padding: '16px 24px' }}>{t.ano}</td>
                <td style={{ padding: '16px 24px' }}>{t.semestre}°</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}