import { z } from 'zod';

export const vinculoSchema = z.object({
  matEstudante: z.string()
    .min(1, 'A matricula e obrigatoria')
    .max(7, 'A matricula deve ter no maximo 7 caracteres'),
  codCurso: z.coerce.number().int().positive('Codigo do curso invalido'),
  
  dataEntrada: z.string().optional().or(z.literal('')),
  dataSaida: z.string().optional().or(z.literal('')),
  
  status: z.enum(['Ativo', 'Cancelada', 'Formando', 'Graduado'], {
    errorMap: () => ({ message: 'Selecione um status academico valido' })
  })
});