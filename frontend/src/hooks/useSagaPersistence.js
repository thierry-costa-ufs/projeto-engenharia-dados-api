import { useState } from 'react';
import { api } from '../utils/api';

export function useSagaPersistence(endpoint, onSuccess) {
  const [modalAberto, setModalAberto] = useState(false);
  const [idPendente, setIdPendente] = useState(null);
  const [loading, setLoading] = useState(false);

  const executarEscritaDupla = async (payload) => {
    setLoading(true);
    try {
      const response = await api.post(endpoint, payload);

      if (response.status === 201) {
        const resultado = await response.json();

        if (resultado.status === "FALHA_PARCIAL_MONGO") {
          const id = resultado.cpf || resultado.matEstudante || resultado.codDepto || resultado.cod_depto || resultado.idCurso || resultado.idVinculo;
          setIdPendente(id);
          setModalAberto(true);
          return { status: 'FALHA_PARCIAL' };
        } else {
          alert('Registro persistido com sucesso em ambos os bancos!');
          if (onSuccess) onSuccess();
          return { status: 'SUCESSO' };
        }
      } else {
        alert('Erro na validação do Relacional (PostgreSQL). Operação abortada.');
        return { status: 'ERRO_RELACIONAL' };
      }
    } catch (error) {
      console.error(`Erro na escrita dupla de ${endpoint}:`, error);
      return { status: 'ERRO_REDE' };
    } finally {
      setLoading(false);
    }
  };

  const executarRollback = async () => {
    if (!idPendente) return;
    try {
      await api.delete(`${endpoint}/${idPendente}`);
      alert('Rollback executado: Cadastro desfeito do PostgreSQL com sucesso!');
    } catch (err) {
      console.error('Falha crítica ao executar compensação (Rollback):', err);
    } finally {
      setModalAberto(false);
      setIdPendente(null);
      if (onSuccess) onSuccess();
    }
  };

  const manterApenasNoPostgres = () => {
    setModalAberto(false);
    setIdPendente(null);
    if (onSuccess) onSuccess();
  };

  return { modalAberto, loading, executarEscritaDupla, executarRollback, manterApenasNoPostgres };
}
