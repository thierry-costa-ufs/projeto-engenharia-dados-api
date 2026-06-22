/* src/utils/validators/disciplinaValidator.js */
import { z } from 'zod';

export const disciplinaSchema = z.object({
  cod_disc: z.string().min(3, 'Código inválido').max(8, 'Máximo de 8 caracteres'),
  creditos: z.coerce.number().int().min(1, 'Mínimo 1 crédito').max(11, 'Máximo 11 créditos'),
  nome: z.string().min(3, 'Nome muito curto').max(40, 'Máximo de 40 caracteres'),
  pre_req: z.preprocess((val) => (val === '' ? null : val), z.string().max(8).nullable()),
  depto_responsavel: z.preprocess((val) => (val === '' ? null : val), z.string().max(5).nullable())
});