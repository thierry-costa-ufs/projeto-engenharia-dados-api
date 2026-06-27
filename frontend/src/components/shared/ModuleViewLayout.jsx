import { useState, useEffect, useCallback } from 'react';
import { RefreshCw, Database, Plus } from 'lucide-react';
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

  // Busca genérica otimizada com useCallback
  const buscarDadosPorBanco = useCallback(async (tipoPersistencia) => {
    try {
      return await api.get(`${endpoint}/${tipoPersistencia}`);
    } catch (err) {
      console.error(`Erro ao buscar dados do modulo [${endpoint}] via ${tipoPersistencia}:`, err);
      return [];
    }
  }, [endpoint]);

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

  useEffect(() => {
    sincronizarDados();
  }, [sincronizarDados]);

  const extrairChavePrimaria = (item) => {
    if (!item) return null;

    // para a entidade Cursa: se houver matrícula e turma juntas, cria a URL completa
    if (item.matEstudante && item.idTurma) {
      return `${item.matEstudante}/${item.idTurma}`;
    }

    // mantém as lógicas antigas para as outras entidades do sistema
    return item.idVinculo || item.idCurso || item.codDepto || item.matEstudante || item.matricula || item.cpf || item.id || item.idRelacional;
  };

  const listaExibida = bancoAtivo === 'postgres'
    ? (Array.isArray(dataPostgres) ? dataPostgres : dataPostgres?.content || [])
    : (Array.isArray(dataMongo) ? dataMongo : dataMongo?.content || []);

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
              onClick={() => setBancoAtivo('postgres')}
            >
              PostgreSQL
            </button>
            <button
              type="button"
              className={`${styles.switchBtn} ${bancoAtivo === 'mongo' ? styles.activeBtn : ''}`}
              onClick={() => setBancoAtivo('mongo')}
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
