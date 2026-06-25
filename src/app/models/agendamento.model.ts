export type StatusAgendamento = 'AGENDADO' | 'CONFIRMADO' | 'CANCELADO' | 'REALIZADO';

export interface Agendamento {
  id?: number;
  titulo: string;
  descricao?: string;
  responsavel: string;
  participantes: string;
  localReuniao?: string;
  dataHoraInicio: string;
  dataHoraFim: string;
  status: StatusAgendamento;
  criadoEm?: string;
  atualizadoEm?: string;
}

export const STATUS_OPCOES: StatusAgendamento[] = [
  'AGENDADO',
  'CONFIRMADO',
  'CANCELADO',
  'REALIZADO'
];
