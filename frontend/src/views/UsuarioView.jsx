import { Users, Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import UsuarioForm from '../components/usuarios/UsuarioForm';

export default function UsuariosView() {
  return (
    <ModuleViewLayout
      title="Módulo de Usuários"
      subtitle="Gerenciamento de credenciais e dados base com persistência poliglota simultânea."
      formTitle="Cadastrar Novo Usuário"
      endpoint="usuarios"
      FormComponent={UsuarioForm}
      tableHeaders={['CPF', 'Nome', 'Login', 'Contatos']}
      // 1. Adicionado onEdit e onDelete na assinatura dos parâmetros abaixo:
      renderRow={(usuario, idx, sharedStyles, onEdit, onDelete) => (
        <tr key={usuario.cpf || idx} className={sharedStyles.tableRow}>
          <td className={sharedStyles.cpfCell}>{usuario.cpf}</td>
          <td className={sharedStyles.nameCell}>
            <Users size={14} />
            <span>{usuario.nome}</span>
          </td>
          <td>{usuario.login}</td>
          <td>
            <span className={sharedStyles.emailBadge}>
              {usuario.email && usuario.email[0] ? usuario.email[0] : '—'}
            </span>
          </td>

          {/* 2. Nova célula adicionada no final da linha para as ações do CRUD */}
          <td>
            <div style={{ display: 'flex', gap: '12px', alignItems: 'center' }}>
              <button
                type="button"
                title="Editar Usuário"
                onClick={() => onEdit(usuario)}
                style={{ background: 'none', border: 'none', color: '#3b82f6', cursor: 'pointer', padding: '4px', display: 'flex', alignItems: 'center' }}
              >
                <Pencil size={14} />
              </button>
              <button
                type="button"
                title="Deletar Usuário"
                onClick={() => onDelete(usuario.cpf)} // Dispara a deleção usando o CPF como identificador único
                style={{ background: 'none', border: 'none', color: '#ef4444', cursor: 'pointer', padding: '4px', display: 'flex', alignItems: 'center' }}
              >
                <Trash2 size={14} />
              </button>
            </div>
          </td>
        </tr>
      )}
    />
  );
}