import { z } from 'zod';

export const estudanteSchema = z.object({
  matEstudante: z.string()
      .min(1, 'A matrícula é obrigatória')
      .max(7, 'A matrícula deve possuir no máximo 7 caracteres'),
  cpf: z.string().regex(/^\d+$/, 'O CPF deve conter apenas números'), //
  mc: z.preprocess(
      (val) => (val === '' ? 0 : val),
      z.coerce.number().min(0).max(10, 'A média máxima é 10.0')
  ), //[cite: 6]
  anoIngresso: z.coerce.number().int().min(1900, 'Ano inválido').max(new Date().getFullYear() + 1) //[cite: 6]
});