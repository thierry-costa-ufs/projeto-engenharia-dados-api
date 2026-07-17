import { useState, useEffect, useCallback } from 'react';
import { RefreshCw, Database, Plus, ChevronLeft, ChevronRight } from 'lucide-react';
import { api } from '../../utils/api';
import styles from '../../styles/ModuleLayout.module.css';
import Modal from '../shared/Modal';

export default function ModuleViewLayout({
                                           title, subtitle, formTitle, endpoint, FormComponent, tableHeaders, renderRow, onSaveCustom
                                         }) {
  const [dataPostgres, setDataPostgres] = useState([]);
  const [dataMongo, setDataMongo] = useState([]);
  const [bancoAtivo, setBancoAtivo] = useState('postgres');
  const [loading, setLoading] = useState(false);
  const [itemEmEdicao, setItemEmEdicao] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  // Novos estados para a paginação
  const [paginaAtual, setPaginaAtual] = useState(0);
  const tamanhoPagina = 10; // Você pode alterar quantos registros aparecem por tela

  // Busca genérica otimizada com os parâmetros de paginação na URL
  const buscarDadosPorBanco = useCallback(async (tipoPersistencia) => {
    try {
      return await api.get(`${endpoint}/${tipoPersistencia}?page=${paginaAtual}&size=${tamanhoPagina}`);
    } catch (err) {
      console.error(`Erro ao buscar dados do modulo [${endpoint}] via ${tipoPersistencia}:`, err);
      return [];
    }
  }, [endpoint, paginaAtual, tamanhoPagina]);

  const sincronizarDados = useCallback(async () => {
    setLoading(true);
    const [pgData, mongoData] = await Promise.all([
      buscarDadosPorBanco('relacional'),
      buscarDadosPorBanco('nosql')
    ]);
    setDataPostgres(pgData);
    setDataMongo(mongoData);
    setLoading(false);
  }, [buscarDadosPorBanco]);

  // Sempre que a página atual mudar, ele busca os dados novamente
  // Sempre que a página atual mudar, ele busca os dados novamente
  useEffect(() => {
    const buscar = async () => {
      await sincronizarDados();
    };
    buscar();
  }, [sincronizarDados]);

  const extrairChavePrimaria = (item) => {
    if (!item) return null;
    if (item.matEstudante && item.idTurma) {
      return `${item.matEstudante}/${item.idTurma}`;
    }
    return item.idVinculo || item.idCurso || item.codDepto || item.matEstudante || item.matricula || item.cpf || item.id || item.idRelacional;
  };

  // Lógica de extração de dados e metadados da paginação
  const fonteAtual = bancoAtivo === 'postgres' ? dataPostgres : dataMongo;
  const listaExibida = Array.isArray(fonteAtual) ? fonteAtual : (fonteAtual?.content || []);
  const totalPaginas = fonteAtual?.totalPages || 1;

  const handleAbrirCriacao = () => { setItemEmEdicao(null); setIsModalOpen(true); };
  const handleIniciarEdicao = (item) => { setItemEmEdicao(item); setIsModalOpen(true); };
  const handleFecharModal = () => { setIsModalOpen(false); setItemEmEdicao(null); };

  const handleDeletar = async (id) => {
    if (!window.confirm('Tem certeza que deseja deletar este registro?')) return;
    try {
      await api.delete(`${endpoint}/${id}`);
      sincronizarDados();
      if (itemEmEdicao && extrairChavePrimaria(itemEmEdicao) === id) {
        handleFecharModal();
      }
    } catch (err) {
      console.error('Falha na deleção de dados:', err);
    }
  };

  const handleFormSubmit = async (formData) => {
    try {
      if (itemEmEdicao) {
        const id = extrairChavePrimaria(itemEmEdicao);
        await api.put(`${endpoint}/${id}`, formData);
      } else if (onSaveCustom) {
        const res = await onSaveCustom(formData);
        if (res?.status !== 'SUCESSO') return;
      } else {
        await api.post(endpoint, formData);
      }
      sincronizarDados();
      handleFecharModal();
    } catch (err) {
      alert(`Erro ao processar requisição: ${err.message || 'Verifique os dados inseridos.'}`);
    }
  };

  return (
      <div className={styles.container}>
        <div className={styles.actionHeader}>
          <div>
            <h2 className={styles.title}>{title}</h2>
            <p className={styles.subtitle}>{subtitle}</p>
          </div>
          <div className={styles.buttonGroup}>
            <button type="button" onClick={handleAbrirCriacao} className={styles.btnCreate}>
              <Plus size={18} /> Cadastrar
            </button>
            <div className={styles.switchContainer}>
              <button
                  type="button"
                  className={`${styles.switchBtn} ${bancoAtivo === 'postgres' ? styles.activeBtn : ''}`}
                  onClick={() => { setBancoAtivo('postgres'); setPaginaAtual(0); }}
              >
                PostgreSQL
              </button>
              <button
                  type="button"
                  className={`${styles.switchBtn} ${bancoAtivo === 'mongo' ? styles.activeBtn : ''}`}
                  onClick={() => { setBancoAtivo('mongo'); setPaginaAtual(0); }}
              >
                MongoDB
              </button>
            </div>
            <button type="button" onClick={sincronizarDados} className={styles.btnSecondary} aria-label="Sincronizar dados">
              <RefreshCw size={18} className={loading ? styles.spin : ''} />
            </button>
          </div>
        </div>

        <div className={styles.tableCardFull}>
          <div className={styles.tableHeaderInline}>
            <Database size={16} />
            <span>Visualizando registros gravados via: <strong>{bancoAtivo.toUpperCase()}</strong></span>
          </div>
          <div className={styles.tableWrapper}>
            <table className={styles.table}>
              <thead>
              <tr>
                {tableHeaders.map((header, i) => <th key={i}>{header}</th>)}
                <th>Ações</th>
              </tr>
              </thead>
              <tbody>
              {listaExibida.length > 0 ? (
                  listaExibida.map((item, idx) => renderRow(item, idx, styles, handleIniciarEdicao, handleDeletar))
              ) : (
                  <tr>
                    <td colSpan={tableHeaders.length + 1} className={styles.emptyState}>
                      Nenhum registro armazenado nesta base.
                    </td>
                  </tr>
              )}
              </tbody>
            </table>
          </div>

          {/* === CONTROLES DE PAGINAÇÃO === */}
          {totalPaginas > 1 && (
              <div className={styles.paginationContainer} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', padding: '1rem', gap: '1rem', borderTop: '1px solid #333' }}>
                <button
                    type="button"
                    onClick={() => setPaginaAtual(prev => Math.max(0, prev - 1))}
                    disabled={paginaAtual === 0 || loading}
                    style={{ padding: '0.5rem', borderRadius: '4px', background: '#252a36', color: '#fff', border: 'none', cursor: paginaAtual === 0 ? 'not-allowed' : 'pointer', display: 'flex', alignItems: 'center' }}
                >
                  <ChevronLeft size={16} /> Anterior
                </button>

                <span style={{ color: '#a0aec0', fontSize: '0.9rem' }}>
              Página <strong>{paginaAtual + 1}</strong> de <strong>{totalPaginas}</strong>
            </span>

                <button
                    type="button"
                    onClick={() => setPaginaAtual(prev => Math.min(totalPaginas - 1, prev + 1))}
                    disabled={paginaAtual >= totalPaginas - 1 || loading}
                    style={{ padding: '0.5rem', borderRadius: '4px', background: '#252a36', color: '#fff', border: 'none', cursor: paginaAtual >= totalPaginas - 1 ? 'not-allowed' : 'pointer', display: 'flex', alignItems: 'center' }}
                >
                  Próxima <ChevronRight size={16} />
                </button>
              </div>
          )}
        </div>

        {isModalOpen && (
            <Modal
                isOpen={isModalOpen}
                onClose={handleFecharModal}
                title={itemEmEdicao ? 'Editar Registro' : formTitle}
                variant="default"
            >
              <FormComponent
                  onSubmit={handleFormSubmit}
                  initialData={itemEmEdicao}
                  isEditing={!!itemEmEdicao}
                  sharedStyles={styles}
                  onCancel={handleFecharModal}
                  onSuccess={sincronizarDados}
              />
            </Modal>
        )}
      </div>
  );
}