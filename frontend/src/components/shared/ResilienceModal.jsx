import Modal from './Modal';

export default function ResilienceModal({ aberto, entidade, onKeep, onRollback }) {
  return (
    <Modal isOpen={aberto} title={`⚠️ Falha Parcial detectada em: ${entidade}`} variant="resilience">
      <p>O registro foi salvo no <strong>PostgreSQL</strong>, mas a replicação falhou no <strong>MongoDB</strong>.</p>
      <p>Escolha a estratégia de resolução para manter a consistência eventual da Saga:</p>

      <div className="modalActions">
        <button onClick={onRollback} className="btnRollback">Executar Rollback (Compensação)</button>
        <button onClick={onKeep} className="btnKeep">Manter Apenas no Relacional</button>
      </div>
    </Modal>
  );
}