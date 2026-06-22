import { useState, useEffect } from 'react';
import { RefreshCw, Database, Plus, X } from 'lucide-react';
import { api } from '../../utils/api';
import styles from '../../styles/ModuleLayout.module.css';
import Modal from '../shared/Modal';

export default function ModuleViewLayout({
  title, subtitle, formTitle, endpoint, FormComponent, tableHeaders, renderRow
}) {
  const [dataPostgres, setDataPostgres] = useState([]);
  const [dataMongo, setDataMongo] = useState([]);
  const [bancoAtivo, setBancoAtivo] = useState('postgres');
  const [loading, setLoading] = useState(false);
  const [itemEmEdicao, setItemEmEdicao] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const carregarPostgres = async () => {
    try {
      const dados = await api.get(`${endpoint}/relacional`);
      setDataPostgres(dados);
    } catch (err) {
      console.error(`Erro ao buscar ${endpoint} no Postgres:`, err);
    }
  };

  const carregarMongo = async () => {
    try {
      const dados = await api.get(`${endpoint}/nosql`);
      setDataMongo(dados);
    } catch (err) {
      console.error(`Erro ao buscar ${endpoint} no Mongo:`, err);
    }
  };

  const sincronizarDados = async () => {
    setLoading(true);
    await Promise.all([carregarPostgres(), carregarMongo()]);
    setLoading(false);
  };

  useEffect(() => { sincronizarDados(); }, []);

  const listaExibida = bancoAtivo === 'postgres'
    ? (Array.isArray(dataPostgres) ? dataPostgres : dataPostgres?.content || [])
    : (Array.isArray(dataMongo) ? dataMongo : dataMongo?.content || []);

  const handleAbrirCriacao = () => {
    setItemEmEdicao(null);
    setIsModalOpen(true);
  };

  const handleIniciarEdicao = (item) => {
    setItemEmEdicao(item);
    setIsModalOpen(true);
  };

  const handleFecharModal = () => {
    setIsModalOpen(false);
    setItemEmEdicao(null);
  };

  const handleDeletar = async (id) => {
    if (!window.confirm('Tem certeza que deseja deletar este registro?')) return;
    try {
      await api.delete(`${endpoint}/${id}`);
      sincronizarDados();
      if (itemEmEdicao && (itemEmEdicao.id === id || itemEmEdicao.cpf === id || itemEmEdicao.matEstudante === id)) {
        handleFecharModal();
      }
    } catch (err) {
      console.error('Erro ao deletar:', err);
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

          {/* Classes limpas aplicadas aqui */}
          <button type="button" onClick={handleAbrirCriacao} className={styles.btnCreate}>
            <Plus size={18} /> Cadastrar
          </button>

          <div className={styles.switchContainer}>
            <button
              type="button"
              className={`${styles.switchBtn} ${bancoAtivo === 'postgres' ? styles.activePostgres : ''}`}
              onClick={() => setBancoAtivo('postgres')}
            >
              PostgreSQL
            </button>
            <button
              type="button"
              className={`${styles.switchBtn} ${bancoAtivo === 'mongo' ? styles.activeMongo : ''}`}
              onClick={() => setBancoAtivo('mongo')}
            >
              MongoDB
            </button>
          </div>
          <button type="button" onClick={sincronizarDados} className={styles.btnSecondary}>
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
                {tableHeaders.map((header, i) => (
                  <th key={i}>{header}</th>
                ))}
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {listaExibida.map((item, idx) =>
                renderRow(item, idx, styles, handleIniciarEdicao, handleDeletar)
              )}
            </tbody>
          </table>
        </div>
      </div>

      {/* Modal sem nenhuma sujeira de style inline */}
      {isModalOpen && (
            <Modal
              isOpen={isModalOpen}
              onClose={handleFecharModal}
              title={itemEmEdicao ? 'Editar Registro' : formTitle}
              variant="default"
            >
              <FormComponent
                onSubmit={async (formData) => {
                  if (itemEmEdicao) {
                    try {
                      const id = itemEmEdicao.idVinculo || itemEmEdicao.idRelacional || itemEmEdicao.matricula || itemEmEdicao.cpf;
                      await api.put(`${endpoint}/${id}`, formData);
                      sincronizarDados();
                      handleFecharModal();
                    } catch (err) {
                      alert('Erro ao atualizar o registro.');
                    }
                  } else if (onSaveCustom) {
                    // Executa a escrita dupla via Saga Hook injetado externamente
                    const res = await onSaveCustom(formData);
                    if (res?.status === 'SUCESSO') {
                      sincronizarDados();
                      handleFecharModal();
                    }
                  } else {
                    try {
                      await api.post(endpoint, formData);
                      sincronizarDados();
                      handleFecharModal();
                    } catch (err) {
                      alert('Erro ao cadastrar o registro.');
                    }
                  }
                }}
                initialData={itemEmEdicao}
                isEditing={!!itemEmEdicao}
                sharedStyles={styles}
                onCancel={handleFecharModal}
              />
            </Modal>
          )}
    </div>
  );
}