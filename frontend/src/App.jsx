/* src/App.jsx */
import { useState } from 'react';
import DashboardLayout from './components/dashboard/DashboardLayout';

// Importações das suas Views estruturadas
import {
  CursoView, VinculoView, UsuarioView, ProfessorView,
  EstudanteView, DepartamentoView, DisciplinaView, TurmaView, CursaView
} from './views';

export default function App() {
  // Inicializa na aba padrão definida no seu layout original
  const [currentView, setCurrentView] = useState('cursos');

  const renderViewContent = () => {
    switch (currentView) {
      case 'cursos':
        return <CursoView />;
      case 'vinculos':
        return <VinculoView />;
      case 'usuarios':
        return <UsuarioView />;
      case 'professores':
        return <ProfessorView />;
      case 'estudantes':
        return <EstudanteView />;
      case 'departamentos':
        return <DepartamentoView />;
      case 'disciplinas':
        return <DisciplinaView />;
      case 'turmas':
        return <TurmaView />;
      case 'cursa':
        return <CursaView />;
      default:
        return <CursoView />;
    }
  };

  return (
    <DashboardLayout activeView={currentView} onViewChange={setCurrentView}>
      {renderViewContent()}
    </DashboardLayout>
  );
}