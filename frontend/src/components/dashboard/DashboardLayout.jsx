import { useState } from 'react';
import { Users, GraduationCap, Link2, BookOpen, LayoutDashboard, LogOut } from 'lucide-react';
import styles from './DashboardLayout.module.css';

import UsuariosView from '../../views/UsuariosView';
import EstudantesView from '../../views/EstudantesView';
import CursosView from '../../views/CursosView';
import VinculosView from '../../views/VinculosView';

// Componente simples para a Home da aplicação
const VisaoGeralOperacional = () => (
  <div>
    <h3>Visão Geral Operacional</h3>
    <p>Métricas de consistência do ecossistema de bancos de dados da UFS.</p>
  </div>
);

// 1. DICIONÁRIO DE RENDERIZAÇÃO (Substitui o switch-case inteiro)
const VIEWS_MAP = {
  'visao-geral': <VisaoGeralOperacional />,
  'usuarios': <UsuariosView />,
  'estudantes': <EstudantesView />,
  'cursos': <CursosView />,
  'vinculos': <VinculosView />
};

// 2. CONFIGURAÇÃO DE MENUS (Tirado de dentro do componente para não ser recriado em cada render)
const MENU_ITEMS = [
  { id: 'visao-geral', label: 'Visão Geral', icon: LayoutDashboard },
  { id: 'usuarios', label: 'Usuários', icon: Users },
  { id: 'estudantes', label: 'Estudantes', icon: GraduationCap },
  { id: 'cursos', label: 'Cursos', icon: BookOpen },
  { id: 'vinculos', label: 'Vínculos', icon: Link2 },
];

export default function DashboardLayout() {
  const [currentView, setCurrentView] = useState('visao-geral');

  // Encontra as propriedades do menu ativo para alimentar o Breadcrumb de forma inteligente
  const itemAtivo = MENU_ITEMS.find(item => item.id === currentView) || MENU_ITEMS[0];

  return (
    <div className={styles.dashboardContainer}>
      {/* SIDEBAR FIXA */}
      <aside className={styles.sidebar}>
        <div className={styles.logoSection}>
          <div className={styles.logoIcon}>UFS</div>
          <div>
            <h1 className={styles.logoText}>EngDados</h1>
            <span className={styles.logoSubtext}>Persistência Poliglota</span>
          </div>
        </div>

        <nav className={styles.navigation}>
          {MENU_ITEMS.map((item) => {
            const IconComponent = item.icon;
            return (
              <button
                key={item.id}
                type="button"
                className={`${styles.navButton} ${currentView === item.id ? styles.activeNav : ''}`}
                onClick={() => setCurrentView(item.id)}
              >
                <IconComponent size={20} />
                <span>{item.label}</span>
              </button>
            );
          })}
        </nav>

        <div className={styles.sidebarFooter}>
          <button type="button" className={styles.logoutButton} onClick={() => window.location.reload()}>
            <LogOut size={18} />
            <span>Sair do Painel</span>
          </button>
        </div>
      </aside>

      {/* ÁREA DE CONTEÚDO DINÂMICO */}
      <main className={styles.mainContent}>
        <header className={styles.topHeader}>
          <div className={styles.breadCrumb}>
            {/* Agora exibe "Visão Geral" ou "Estudantes" em vez de "visao-geral" */}
            Dashboard / <span className={styles.breadCrumbActive}>{itemAtivo.label}</span>
          </div>
          <div className={styles.userProfile}>
            <div className={styles.avatar}>T</div>
            <span>Admin Docente</span>
          </div>
        </header>

        <section className={styles.contentBody}>
          {/* Renderização direta pelo mapeamento de chave-valor com Fallback seguro */}
          {VIEWS_MAP[currentView] || VIEWS_MAP['visao-geral']}
        </section>
      </main>
    </div>
  );
}