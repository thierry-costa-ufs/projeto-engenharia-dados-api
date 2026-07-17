/* src/utils/validators/professorValidator.js */
import { z } from 'zod';

export const professorSchema = z.object({
  matricula: z.string().min(2).max(7, 'A matrícula deve ter até 7 caracteres'),
  departamento: z.string().min(2).max(5, 'Código de departamento inválido'),
  cpf: z.string().regex(/^\d+$/, 'O CPF deve conter apenas números'),
  formacao: z.enum(['Graduação', 'Especialização', 'Mestrado', 'Doutorado']),
  jornada: z.enum(['20h', '40h', 'DE']),
  salario: z.coerce.number().min(0, 'O salário não pode ser negativo'),
  dataAdmissao: z.string()
});