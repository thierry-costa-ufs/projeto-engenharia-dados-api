import { X } from 'lucide-react';
import styles from './Modal.module.css';

export default function Modal({ isOpen, onClose, title, children, variant = 'default' }) {
  if (!isOpen) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={`${styles.modalBox} ${styles[variant]}`}>
        {onClose && (
          <button type="button" onClick={onClose} className={styles.modalClose} aria-label="Fechar modal">
            <X size={20} />
          </button>
        )}
        {title && <h3 className={styles.modalTitle}>{title}</h3>}
        <div className={styles.modalContent}>
          {children}
        </div>
      </div>
    </div>
  );
}