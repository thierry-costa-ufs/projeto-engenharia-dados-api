/* src/utils/validators/departamentoValidator.js */
import { z } from 'zod';

export const departamentoSchema = z.object({
  codDepto: z.string().min(2, 'Mínimo 2 caracteres').max(5, 'Máximo 5 caracteres'),
  nome: z.string().min(3, 'Nome muito curto').max(50, 'Máximo 50 caracteres'),
  chefe: z.string().max(7, 'Máximo 7 caracteres'),
  orcamento: z.preprocess(
    (val) => (val === '' ? undefined : val),
    z.coerce.number().gt(0, 'O orçamento deve ser maior que zero')
  ),
  comissal: z.preprocess(
    (val) => (val === '' ? undefined : val),
    z.coerce.number().min(0, 'A comissão não pode ser negativa')
  )
});
