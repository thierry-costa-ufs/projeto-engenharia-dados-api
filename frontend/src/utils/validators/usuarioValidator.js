/* src/utils/validators/usuarioValidator.js */
import { z } from 'zod';

export const usuarioSchema = z.object({
  cpf: z.string()
    .min(11, 'O CPF deve ter no mínimo 11 dígitos')
    .max(13, 'O CPF deve ter no máximo 13 caracteres numéricos') // Alinhado com tipo_cpf NUMERIC(13)
    .regex(/^\d+$/, 'O CPF deve conter apenas números'),
  nome: z.string()
    .min(3, 'O nome deve ter pelo menos 3 caracteres')
    .max(100, 'O nome não pode passar de 100 caracteres'), // VARCHAR(100)
  data_nascimento: z.string().or(z.date()).optional(),
  login: z.string()
    .min(4, 'O login deve ter pelo menos 4 caracteres')
    .max(45, 'O login não pode passar de 45 caracteres'), // VARCHAR(45)
  senha: z.string()
    .min(6, 'A senha deve ter pelo menos 6 caracteres')
    .max(32, 'A senha não pode passar de 32 caracteres') // VARCHAR(32)
});