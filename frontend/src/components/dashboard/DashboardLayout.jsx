/* src/components/dashboard/DashboardLayout.jsx */
import React from 'react';
import {
  Users, GraduationCap, Link2, BookOpen,
  Building2, Briefcase, BookMarked, Presentation, ClipboardCheck
} from 'lucide-react';
import styles from './DashboardLayout.module.css';

// O Schema agora é puramente descritivo de navegação. Zero acoplamento com Views.
const NAVIGATION_SCHEMA = [
  { id: 'cursos', label: 'Cursos', icon: BookOpen },
  { id: 'vinculos', label: 'Vínculos', icon: Link2 },
  { id: 'usuarios', label: 'Usuários', icon: Users },
  { id: 'professores', label: 'Professores', icon: Briefcase },
  { id: 'estudantes', label: 'Estudantes [WIP]', icon: GraduationCap },
  { id: 'departamentos', label: 'Departamentos [WIP]', icon: Building2 },
  { id: 'disciplinas', label: 'Disciplinas [WIP]', icon: BookMarked },
  { id: 'turmas', label: 'Turmas [WIP]', icon: Presentation },
  { id: 'cursa', label: 'Cursa [WIP]', icon: ClipboardCheck }
];

export default function DashboardLayout({ activeView, onViewChange, children }) {
  return (
    <div className={styles.dashboardContainer}>
      <aside className={styles.sidebar}>
        <div className={styles.logoSection}>
          <div className={styles.logoIcon}>UFS</div>
          <div>
            <h1 className={styles.logoText}>EngDados</h1>
            <span className={styles.logoSubtext}>Persistência Poliglota</span>
          </div>
        </div>

        <nav className={styles.navigation}>
          {NAVIGATION_SCHEMA.map((item) => {
            const IconComponent = item.icon;
            return (
              <button
                key={item.id}
                type="button"
                className={`${styles.navButton} ${activeView === item.id ? styles.activeNav : ''}`}
                onClick={() => onViewChange(item.id)}
              >
                <IconComponent size={20} />
                <span>{item.label}</span>
              </button>
            );
          })}
        </nav>
      </aside>

      <main className={styles.mainContent}>
        <section className={styles.contentBody}>
          {/* O conteúdo da View ativa entra de forma dinâmica aqui */}
          {children}
        </section>
      </main>
    </div>
  );
}