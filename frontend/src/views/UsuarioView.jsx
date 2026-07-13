import { Pencil, Trash2 } from 'lucide-react';
import ModuleViewLayout from '../components/shared/ModuleViewLayout';
import { UsuarioForm } from '../components/forms';

export default function UsuariosView() {
  const formatarData = (dataString) => {
    if (!dataString) return '—';
    return dataString.split('-').reverse().join('/');
  };

  return (
    <ModuleViewLayout
      title="Módulo de Usuários"
      subtitle="Gerenciamento de credenciais e dados base com persistência poliglota simultânea."
      formTitle="Cadastrar Novo Usuário"
      endpoint="usuarios"
      FormComponent={UsuarioForm}
      tableHeaders={[
        'CPF',
        'Nome',
        'Data de Nascimento',
        'E-mails',
        'Telefones',
        'Login',
        'Senha'
      ]}
      renderRow={(usuario, idx, sharedStyles, onEdit, onDelete) => {
        // Normaliza o ID para o ModuleViewLayout encontrar o identificador no Postgres (cpf) e no Mongo (mongoId)
        const idUsuario = usuario.cpf || usuario.mongoId || idx;

        return (
          <tr key={idUsuario} className={sharedStyles.tableRow}>
            <td>{usuario.cpf}</td>
            <td>{usuario.nome}</td>
            <td>{formatarData(usuario.dataNascimento)}</td>

            <td>
              <div className={sharedStyles.textListWrapper}>
                {usuario.email && usuario.email.length > 0 ? (
                  usuario.email.map((email, i) => <span key={i}>{email}</span>)
                ) : '—'}
              </div>
            </td>

            <td>
              <div className={sharedStyles.textListWrapper}>
                {usuario.telefone && usuario.telefone.length > 0 ? (
                  usuario.telefone.map((tel, i) => <span key={i}>{tel}</span>)
                ) : '—'}
              </div>
            </td>

            <td>{usuario.login}</td>
            <td>{usuario.senha || '—'}</td>

            <td>
              <div className={sharedStyles.actionsCell}>
                <button
                  type="button"
                  title="Editar Usuário"
                  onClick={() => onEdit(usuario)}
                  className={sharedStyles.btnTableEdit}
                >
                  <Pencil size={14} />
                </button>
                <button
                  type="button"
                  title="Deletar Usuário"
                  onClick={() => onDelete(usuario.cpf)}
                  className={sharedStyles.btnTableDelete}
                >
                  <Trash2 size={14} />
                </button>
              </div>
            </td>
          </tr>
        );
      }}
    />
  );
}