import React from 'react';
import {
  Database,
  RefreshCw,
  CheckCircle2,
  AlertTriangle,
  Layers,
  TrendingUp
} from 'lucide-react';

export default function GeneralView() {
  // Cores adaptadas ao tema escuro da aplicação (#1e293b e #334155)
  const metricasBancos = [
    { id: 1, nome: 'PostgreSQL (Relacional)', tipo: 'Core OLTP', tabelas: 8, status: 'Operacional', cor: '#3b82f6', bgStatus: 'rgba(59, 130, 246, 0.15)' },
    { id: 2, nome: 'MongoDB (NoSQL)', tipo: 'Espelhamento/Documentos', tabelas: 2, status: 'Sincronizando', cor: '#10b981', bgStatus: 'rgba(16, 185, 129, 0.15)' }
  ];

  const statusModulos = [
    { modulo: 'Usuários', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Concluído', sync: '100%' },
    { modulo: 'Estudantes', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '0%' },
    { modulo: 'Cursos', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '0%' },
    { modulo: 'Vínculos', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '94.2%' },
    { modulo: 'Departamentos', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '0%' },
    { modulo: 'Professores', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '0%' },
    { modulo: 'Disciplinas', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '0%' },
    { modulo: 'Turmas', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '0%' },
    { modulo: 'Cursa (Notas)', modelo: 'Poliglota', postgres: 'Concluído', mongo: 'Pendente', sync: '0%' },
  ];

  return (
    <div style={{ maxWidth: '1200px', margin: '0 auto', fontFamily: 'system-ui, -apple-system, sans-serif' }}>

      {/* Cabeçalho principal */}
      <div style={{ borderBottom: '1px solid #334155', paddingBottom: '16px', marginBottom: '24px' }}>
        <h1 style={{ fontSize: '24px', fontWeight: 'bold', color: '#ffffff', margin: 0 }}>Visão Geral Operacional</h1>
        <p style={{ fontSize: '14px', color: '#94a3b8', marginTop: '4px' }}>
          Métricas de consistência, volumetria e status de sincronização do ecossistema poliglota da UFS.
        </p>
      </div>

      {/* Grid 1: Cards de Métricas de Infraestrutura */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(240px, 1fr))', gap: '16px', marginBottom: '32px' }}>

        {/* Card 1: Consistência */}
        <div style={{ backgroundColor: '#1e293b', padding: '20px', borderRadius: '8px', border: '1px solid #334155' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ fontSize: '14px', fontWeight: '500', color: '#94a3b8' }}>Consistência da Replicação</span>
            <RefreshCw size={20} color="#3b82f6" />
          </div>
          <h2 style={{ fontSize: '28px', fontWeight: 'bold', color: '#ffffff', margin: '0 0 4px 0' }}>94.2%</h2>
          <span style={{ fontSize: '12px', color: '#f59e0b', fontWeight: '500', display: 'flex', alignItems: 'center', gap: '4px' }}>
            <AlertTriangle size={14} /> Eventos de FALHA_PARCIAL detectados
          </span>
        </div>

        {/* Card 2: Postgres Registros */}
        <div style={{ backgroundColor: '#1e293b', padding: '20px', borderRadius: '8px', border: '1px solid #334155' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ fontSize: '14px', fontWeight: '500', color: '#94a3b8' }}>Total de Registros (PG)</span>
            <Database size={20} color="#cbd5e1" />
          </div>
          <h2 style={{ fontSize: '28px', fontWeight: 'bold', color: '#ffffff', margin: '0 0 4px 0' }}>1,420</h2>
          <span style={{ fontSize: '12px', color: '#10b981', fontWeight: '500', display: 'flex', alignItems: 'center', gap: '4px' }}>
            <CheckCircle2 size={14} /> Tabelas instanciadas via DUMP
          </span>
        </div>

        {/* Card 3: Mongo Documentos */}
        <div style={{ backgroundColor: '#1e293b', padding: '20px', borderRadius: '8px', border: '1px solid #334155' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
            <span style={{ fontSize: '14px', fontWeight: '500', color: '#94a3b8' }}>Documentos NoSQL (Mongo)</span>
            <Layers size={20} color="#10b981" />
          </div>
          <h2 style={{ fontSize: '28px', fontWeight: 'bold', color: '#ffffff', margin: '0 0 4px 0' }}>84</h2>
          <span style={{ fontSize: '12px', color: '#3b82f6', fontWeight: '500', display: 'flex', alignItems: 'center', gap: '4px' }}>
            <TrendingUp size={14} /> Coleções dinâmicas ativas
          </span>
        </div>

      </div>

      {/* Grid 2: Clusters & Data Mapping */}
      <h3 style={{ fontSize: '16px', fontWeight: '600', color: '#f8fafc', marginBottom: '12px' }}>Clusters & Data Mapping</h3>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(400px, 1fr))', gap: '20px', marginBottom: '32px' }}>
        {metricasBancos.map((banco) => (
          <div key={banco.id} style={{ backgroundColor: '#1e293b', padding: '20px', borderRadius: '8px', border: `1px solid #334155`, borderLeft: `6px solid ${banco.cor}` }}>
            <h4 style={{ margin: '0 0 4px 0', fontSize: '16px', fontWeight: 'bold', color: '#ffffff' }}>{banco.nome}</h4>
            <p style={{ margin: '0 0 12px 0', fontSize: '13px', color: '#94a3b8' }}>Estratégia: {banco.tipo}</p>
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', fontSize: '14px' }}>
              <span style={{ color: '#cbd5e1' }}>Módulos Mapeados: <strong>{banco.tabelas}</strong></span>
              <span style={{ fontSize: '12px', padding: '4px 10px', borderRadius: '4px', backgroundColor: banco.bgStatus, color: banco.cor, fontWeight: 'bold' }}>
                {banco.status}
              </span>
            </div>
          </div>
        ))}
      </div>

      {/* Tabela de Cobertura do Domínio */}
      <h3 style={{ fontSize: '16px', fontWeight: '600', color: '#f8fafc', marginBottom: '12px' }}>
        Malha de Implementação do Domínio Acadêmico
      </h3>
      <div style={{ backgroundColor: '#1e293b', borderRadius: '8px', border: '1px solid #334155', overflow: 'hidden' }}>
        <table style={{ minWidth: '100%', borderCollapse: 'collapse', textAlign: 'left' }}>
          <thead style={{ backgroundColor: '#0f172a', fontSize: '12px', textTransform: 'uppercase', color: '#94a3b8', borderBottom: '1px solid #334155' }}>
            <tr>
              <th style={{ padding: '14px 20px' }}>Entidade / Módulo</th>
              <th style={{ padding: '14px 20px' }}>Arquitetura</th>
              <th style={{ padding: '14px 20px' }}>PostgreSQL (JPA)</th>
              <th style={{ padding: '14px 20px' }}>MongoDB (NoSQL)</th>
              <th style={{ padding: '14px 20px' }}>Sincronia Estimada</th>
            </tr>
          </thead>
          <tbody style={{ fontSize: '14px', color: '#cbd5e1' }}>
            {statusModulos.map((item, index) => {
              const isPendente = item.postgres === 'Pendente';
              return (
                <tr key={index} style={{ borderBottom: index !== statusModulos.length - 1 ? '1px solid #334155' : 'none', backgroundColor: isPendente ? 'rgba(15, 23, 42, 0.2)' : 'transparent' }}>
                  <td style={{ padding: '14px 20px', fontWeight: '500', color: isPendente ? '#64748b' : '#ffffff' }}>{item.modulo}</td>
                  <td style={{ padding: '14px 20px', fontSize: '13px', color: isPendente ? '#64748b' : '#cbd5e1' }}>{item.modelo}</td>
                  <td style={{ padding: '14px 20px' }}>
                    <span style={{
                      color: item.postgres === 'Concluído' ? '#10b981' : '#64748b',
                      fontWeight: item.postgres === 'Concluído' ? '600' : 'normal'
                    }}>
                      {item.postgres}
                    </span>
                  </td>
                  <td style={{ padding: '14px 20px', color: item.mongo === 'Concluído' ? '#10b981' : '#64748b' }}>{item.mongo}</td>
                  <td style={{ padding: '14px 20px', fontFamily: 'monospace', color: item.sync === '0%' ? '#64748b' : '#f8fafc' }}>{item.sync}</td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

    </div>
  );
}