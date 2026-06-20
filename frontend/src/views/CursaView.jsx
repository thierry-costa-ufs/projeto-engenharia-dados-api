import React from 'react';

export default function CursaView() {
  const mockHistorico = [
    { mat_estudante: 'E112', id_turma: 7, nota: 4.1 },
    { mat_estudante: 'E102', id_turma: 7, nota: 10.0 },
    { mat_estudante: 'E109', id_turma: 5, nota: null } // Nota nula (em andamento)
  ];

  return (
    <div style={{ padding: '24px', maxWidth: '1200px', margin: '0 auto' }}>
      <div style={{ display: 'flex', justifyContent: 'between', alignItems: 'center', borderBottom: '1px solid #e5e7eb', paddingBottom: '16px', marginBottom: '24px' }}>
        <div>
          <h1 style={{ fontSize: '24px', fontWeight: 'bold', color: '#1f2937' }}>Módulo: Matrículas em Disciplinas (Cursa)</h1>
          <p style={{ fontSize: '14px', color: '#6b7280' }}>Diário de classe, consolidação de notas e vinculação de alunos a turmas.</p>
        </div>
        <span style={{ padding: '4px 12px', backgroundColor: '#fef3c7', color: '#92400e', fontSize: '12px', fontWeight: '600', borderRadius: '9999px', textTransform: 'uppercase' }}>
          Placeholder / Sem Backend
        </span>
      </div>

      <div style={{ backgroundColor: '#ffffff', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0,0,0,0.1)', overflow: 'hidden', opacity: 0.6, pointerEvents: 'none' }}>
        <table style={{ minWidth: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
          <thead style={{ backgroundColor: '#f9fafb', fontSize: '12px', textTransform: 'uppercase', color: '#6b7280' }}>
            <tr>
              <th style={{ padding: '12px 24px' }}>Matrícula Estudante</th>
              <th style={{ padding: '12px 24px' }}>ID da Turma</th>
              <th style={{ padding: '12px 24px' }}>Nota Consolidada</th>
              <th style={{ padding: '12px 24px' }}>Status Provisório</th>
            </tr>
          </thead>
          <tbody style={{ fontSize: '14px', color: '#4b5563' }}>
            {mockHistorico.map((h, index) => (
              <tr key={index} style={{ borderTop: '1px solid #e5e7eb' }}>
                <td style={{ padding: '16px 24px', fontFamily: 'monospace' }}>{h.mat_estudante}</td>
                <td style={{ padding: '16px 24px' }}>{h.id_turma}</td>
                <td style={{ padding: '16px 24px', fontWeight: 'bold' }}>
                  {h.nota !== null ? h.nota.toFixed(1) : '—'}
                </td>
                <td style={{ padding: '16px 24px' }}>
                  {h.nota === null ? (
                    <span style={{ color: '#2563eb' }}>Em Andamento</span>
                  ) : h.nota >= 5.0 ? (
                    <span style={{ color: '#16a34a' }}>Aprovado</span>
                  ) : (
                    <span style={{ color: '#dc2626' }}>Reprovado</span>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}