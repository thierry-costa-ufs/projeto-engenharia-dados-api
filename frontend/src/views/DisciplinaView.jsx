import React from 'react';

export default function DisciplinaView() {
  const mockDisciplinas = [
    { cod_disc: 'COMP0198', nome: 'Programação Orientada à Objetos', pre_req: 'COMP0197', creditos: 4, depto_responsavel: 'DCOMP' }
  ];

  return (
    <div style={{ padding: '24px', maxWidth: '1200px', margin: '0 auto' }}>
      <div style={{ display: 'flex', justifyContent: 'between', alignItems: 'center', borderBottom: '1px solid #e5e7eb', paddingBottom: '16px', marginBottom: '24px' }}>
        <div>
          <h1 style={{ fontSize: '24px', fontWeight: 'bold', color: '#1f2937' }}>Módulo: Disciplinas</h1>
          <p style={{ fontSize: '14px', color: '#6b7280' }}>Catálogo de matérias ofertadas, créditos e dependências de pré-requisitos.</p>
        </div>
        <span style={{ padding: '4px 12px', backgroundColor: '#fef3c7', color: '#92400e', fontSize: '12px', fontWeight: '600', borderRadius: '9999px', textTransform: 'uppercase' }}>
          Placeholder / Sem Backend
        </span>
      </div>

      <div style={{ backgroundColor: '#ffffff', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0,0,0,0.1)', overflow: 'hidden', opacity: 0.6, pointerEvents: 'none' }}>
        <table style={{ minWidth: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
          <thead style={{ backgroundColor: '#f9fafb', fontSize: '12px', textTransform: 'uppercase', color: '#6b7280' }}>
            <tr>
              <th style={{ padding: '12px 24px' }}>Código</th>
              <th style={{ padding: '12px 24px' }}>Nome da Disciplina</th>
              <th style={{ padding: '12px 24px' }}>Pré-requisito</th>
              <th style={{ padding: '12px 24px' }}>Créditos</th>
              <th style={{ padding: '12px 24px' }}>Depto Responsável</th>
            </tr>
          </thead>
          <tbody style={{ fontSize: '14px', color: '#4b5563' }}>
            {mockDisciplinas.map((d) => (
              <tr key={d.cod_disc} style={{ borderTop: '1px solid #e5e7eb' }}>
                <td style={{ padding: '16px 24px', fontFamily: 'monospace' }}>{d.cod_disc}</td>
                <td style={{ padding: '16px 24px', fontWeight: '500' }}>{d.nome}</td>
                <td style={{ padding: '16px 24px', fontFamily: 'monospace' }}>{d.pre_req || 'Nenhum'}</td>
                <td style={{ padding: '16px 24px' }}>{d.creditos}</td>
                <td style={{ padding: '16px 24px' }}>{d.depto_responsavel}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}