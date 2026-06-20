import React from 'react';

export default function DepartamentoView() {
  // Mock apenas para preenchimento visual do placeholder
  const mockDepartamentos = [
    { cod_depto: 'DCOMP', nome: 'Departamento de Computação', chefe: 'P100', orcamento: 10000.00, comissal: 1000.00 },
    { cod_depto: 'DMA', nome: 'Departamento de Matemática', chefe: 'P600', orcamento: 20000.00, comissal: 1000.00 }
  ];

  return (
    <div style={{ padding: '24px', maxWidth: '1200px', margin: '0 auto' }}>
      <div style={{ display: 'flex', justifyContent: 'between', alignItems: 'center', borderBottom: '1px solid #e5e7eb', paddingBottom: '16px', marginBottom: '24px' }}>
        <div>
          <h1 style={{ fontSize: '24px', fontWeight: 'bold', color: '#1f2937' }}>Módulo: Departamentos</h1>
          <p style={{ fontSize: '14px', color: '#6b7280' }}>Gerenciamento de dotação orçamentária e chefias de institutos.</p>
        </div>
        <span style={{ padding: '4px 12px', backgroundColor: '#fef3c7', color: '#92400e', fontSize: '12px', fontWeight: '600', borderRadius: '9999px', textTransform: 'uppercase' }}>
          Placeholder / Sem Backend
        </span>
      </div>

      <div style={{ backgroundColor: '#fffbeb', borderLeft: '4px solid #f59e0b', padding: '16px', borderRadius: '4px', marginBottom: '24px' }}>
        <p style={{ fontSize: '14px', color: '#b45309', fontWeight: '500' }}>
          ⚠️ Pendente: Criar pasta <code style={{ backgroundColor: '#fef3c7', padding: '2px 4px', borderRadius: '4px' }}>src/components/departamentos</code> e integrar com os endpoints do Spring Boot.
        </p>
      </div>

      <div style={{ backgroundColor: '#ffffff', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0,0,0,0.1)', overflow: 'hidden', opacity: 0.6, pointerEvents: 'none' }}>
        <table style={{ minWidth: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
          <thead style={{ backgroundColor: '#f9fafb', fontSize: '12px', textTransform: 'uppercase', color: '#6b7280' }}>
            <tr>
              <th style={{ padding: '12px 24px' }}>Código Depto</th>
              <th style={{ padding: '12px 24px' }}>Nome</th>
              <th style={{ padding: '12px 24px' }}>Mat. Chefe</th>
              <th style={{ padding: '12px 24px' }}>Orçamento</th>
              <th style={{ padding: '12px 24px' }}>Comissal</th>
            </tr>
          </thead>
          <tbody style={{ fontSize: '14px', color: '#4b5563' }}>
            {mockDepartamentos.map((d) => (
              <tr key={d.cod_depto} style={{ borderTop: '1px solid #e5e7eb' }}>
                <td style={{ padding: '16px 24px', fontFamily: 'monospace' }}>{d.cod_depto}</td>
                <td style={{ padding: '16px 24px' }}>{d.nome}</td>
                <td style={{ padding: '16px 24px' }}>{d.chefe}</td>
                <td style={{ padding: '16px 24px' }}>R$ {d.orcamento.toFixed(2)}</td>
                <td style={{ padding: '16px 24px' }}>{d.comissal}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}