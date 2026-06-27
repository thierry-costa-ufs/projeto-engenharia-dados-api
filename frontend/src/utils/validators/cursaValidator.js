import { z } from 'zod';

export const cursaSchema = z.object({
  mat_estudante: z.string()
      .min(1, 'A matrícula é obrigatória')
      .max(7, 'A matrícula deve ter no máximo 7 caracteres'),
  id_turma: z.coerce.number().int().positive('O ID da turma deve ser um número inteiro positivo'),
  nota: z.preprocess(
    (val) => (val === '' ? null : val),
    z.coerce.number().min(0, 'A nota mínima é 0.0').max(10, 'A nota máxima é 10.0').nullable()
  )
});