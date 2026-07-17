/* src/utils/validators/professorValidator.js */
import { z } from 'zod';

export const professorSchema = z.object({
  matricula: z.string().min(2).max(7, 'A matrícula deve ter até 7 caracteres'),
  departamento: z.string().min(2).max(5, 'Código de departamento inválido'),
  cpf: z.string().regex(/^\d+$/, 'O CPF deve conter apenas números'),
  // Ajustado para o padrão que o Java e o BD esperam
  formacao: z.enum(['GRADUACAO', 'ESPECIALIZACAO', 'MESTRADO', 'DOUTORADO']),
  jornada: z.enum(['20H', '40H', 'DE']),
  salario: z.coerce.number().min(0, 'O salário não pode ser negativo'),
  dataAdmissao: z.string()
});