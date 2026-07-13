import { z } from 'zod';

export const turmaSchema = z.object({
  cod_disc: z.string().min(3).max(8, 'Código de disciplina inválido'),
  numero: z.coerce.number().int().positive('Número da turma inválido'),
  ano: z.coerce.number().int().min(1900, 'Ano inválido'),
  semestre: z.coerce.number().int().min(1).max(2, 'O semestre deve ser 1 ou 2')
});