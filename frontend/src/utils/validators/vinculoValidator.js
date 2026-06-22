import { z } from 'zod';

export const vinculoSchema = z.object({
  matEstudante: z.string().length(7, 'A matrícula deve ter exatamente 7 caracteres'),
  codCurso: z.coerce.number().int().positive('Código do curso inválido'),
  dataEntrada: z.string().min(1, 'A data de entrada é obrigatória'),
  dataSaida: z.preprocess((val) => (val === '' ? null : val), z.string().nullable().optional()),
  status: z.enum(['Ativo', 'Cancelada', 'Formando', 'Graduado'], {
    errorMap: () => ({ message: 'Selecione um status acadêmico válido' })
  })
});