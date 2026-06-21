import { Users, Pencil, Trash2, Mail, Phone, Calendar, Lock } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import UsuarioForm from '../components/usuarios/UsuarioForm';

export default function UsuariosView() {
  const formatarData = (dataString) => {
    if (!dataString) return '—';
    const [ano, mes, dia] = dataString.split('-');
    return `${dia}/${mes}/${ano}`;
  };

  return (
    <ModuleViewLayout
      title="Módulo de Usuários"
      subtitle="Gerenciamento de credenciais e dados base com persistência poliglota simultânea."
      formTitle="Cadastrar Novo Usuário"
      endpoint="usuarios"
      FormComponent={UsuarioForm}
      // 'Ações' foi removido daqui pois o ModuleViewLayout adiciona essa coluna automaticamente por baixo dos panos
      tableHeaders={[
        'CPF',
        'Nome',
        'Data de Nascimento',
        'E-mails',
        'Telefones',
        'Login',
        'Senha'
      ]}
      renderRow={(usuario, idx, sharedStyles, onEdit, onDelete) => (
        <tr key={usuario.cpf || idx} className={sharedStyles.tableRow}>
          {/* 1. CPF */}
          <td className={sharedStyles.cpfCell}>{usuario.cpf}</td>

          {/* 2. Nome */}
          <td className={sharedStyles.nameCell}>
            <Users size={14} className="text-gray-400 mr-1" />
            <span className="font-medium text-gray-700">{usuario.nome}</span>
          </td>

          {/* 3. Data de Nascimento */}
          <td className="px-4 py-3 text-sm text-gray-600">
            <div className="flex items-center gap-1">
              <Calendar size={13} className="text-gray-400" />
              <span>{formatarData(usuario.dataNascimento)}</span>
            </div>
          </td>

          {/* 4. E-mails */}
          <td className="px-4 py-3 text-sm">
            <div className="flex flex-col gap-1">
              {usuario.email && usuario.email.length > 0 ? (
                usuario.email.map((email, i) => (
                  <span key={i} className="inline-flex items-center gap-1 text-xs bg-blue-50 text-blue-700 px-2 py-0.5 rounded border border-blue-100 w-fit">
                    <Mail size={10} />
                    {email}
                  </span>
                ))
              ) : (
                <span className="text-gray-400">—</span>
              )}
            </div>
          </td>

          {/* 5. Telefones */}
          <td className="px-4 py-3 text-sm">
            <div className="flex flex-col gap-1">
              {usuario.telefone && usuario.telefone.length > 0 ? (
                usuario.telefone.map((tel, i) => (
                  <span key={i} className="inline-flex items-center gap-1 text-xs bg-green-50 text-green-700 px-2 py-0.5 rounded border border-green-100 w-fit">
                    <Phone size={10} />
                    {tel}
                  </span>
                ))
              ) : (
                <span className="text-gray-400">—</span>
              )}
            </div>
          </td>

          {/* 6. Login */}
          <td className="px-4 py-3 text-sm font-mono text-gray-600">{usuario.login}</td>

          {/* 7. Senha - Agora mapeada e recebida corretamente através do DTO atualizado */}
          <td className="px-4 py-3 text-sm font-mono text-gray-500">
            <div className="flex items-center gap-1">
              <Lock size={12} className="text-gray-400" />
              <span>{usuario.senha || '—'}</span>
            </div>
          </td>

          {/* 8. Célula operacional (Preenche perfeitamente a coluna automatizada pelo layout) */}
          <td className="px-4 py-3 text-sm">
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
                onClick={() => onDelete(usuario.cpf)}
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