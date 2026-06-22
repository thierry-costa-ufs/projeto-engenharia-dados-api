import { z } from 'zod';

export const cursoSchema = z.object({
  nome: z.string().min(3, 'O nome deve ter pelo menos 3 caracteres').max(100, 'Máximo 100 caracteres'),
  campus: z.string().min(2, 'O campus deve ser informado').max(100),
  grau: z.enum(['BACHARELADO', 'LICENCIATURA_PLENA'], {
    errorMap: () => ({ message: 'Selecione um grau válido' })
  }),
  turno: z.enum(['MATUTINO', 'VESPERTINO', 'NOTURNO', 'TURNO_INDEFINIDO'], {
    errorMap: () => ({ message: 'Selecione um turno válido' })
  }),
  nivel: z.enum(['GRADUACAO', 'MESTRADO', 'DOUTORADO', 'LATO'], {
    errorMap: () => ({ message: 'Selecione um nível acadêmico válido' })
  })
});