import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { AgendamentoService } from '../../services/agendamento.service';
import { Agendamento, STATUS_OPCOES, StatusAgendamento } from '../../models/agendamento.model';

@Component({
  selector: 'app-agendamento-list',
  standalone: true,
  imports: [ReactiveFormsModule, DatePipe],
  templateUrl: './agendamento-list.component.html',
  styleUrl: './agendamento-list.component.css'
})
export class AgendamentoListComponent implements OnInit {
  readonly agendamentos = signal<Agendamento[]>([]);
  readonly erro = signal('');
  readonly carregando = signal(false);
  readonly salvando = signal(false);
  readonly editandoId = signal<number | null>(null);
  readonly statusOpcoes = STATUS_OPCOES;
  readonly form;

  constructor(
    private readonly fb: FormBuilder,
    private readonly agendamentoService: AgendamentoService
  ) {
    this.form = this.fb.nonNullable.group({
      titulo: ['', [Validators.required, Validators.maxLength(150)]],
      descricao: [''],
      responsavel: ['', [Validators.required, Validators.maxLength(200)]],
      participantes: ['', [Validators.required, Validators.maxLength(500)]],
      localReuniao: [''],
      dataHoraInicio: ['', Validators.required],
      dataHoraFim: ['', Validators.required],
      status: ['AGENDADO' as StatusAgendamento, Validators.required]
    });
  }

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando.set(true);
    this.erro.set('');

    this.agendamentoService.listar().subscribe({
      next: data => {
        this.agendamentos.set(data);
        this.carregando.set(false);
      },
      error: err => {
        this.erro.set(err.error?.mensagem ?? 'Erro ao carregar agendamentos.');
        this.carregando.set(false);
      }
    });
  }

  novo(): void {
    this.editandoId.set(null);
    this.form.reset({
      titulo: '',
      descricao: '',
      responsavel: '',
      participantes: '',
      localReuniao: '',
      dataHoraInicio: '',
      dataHoraFim: '',
      status: 'AGENDADO'
    });
  }

  editar(agendamento: Agendamento): void {
    this.editandoId.set(agendamento.id ?? null);
    this.form.reset({
      titulo: agendamento.titulo,
      descricao: agendamento.descricao ?? '',
      responsavel: agendamento.responsavel,
      participantes: agendamento.participantes,
      localReuniao: agendamento.localReuniao ?? '',
      dataHoraInicio: this.toInputDateTime(agendamento.dataHoraInicio),
      dataHoraFim: this.toInputDateTime(agendamento.dataHoraFim),
      status: agendamento.status
    });
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  salvar(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const raw = this.form.getRawValue();
    const payload: Agendamento = {
      titulo: raw.titulo,
      descricao: raw.descricao || undefined,
      responsavel: raw.responsavel,
      participantes: raw.participantes,
      localReuniao: raw.localReuniao || undefined,
      dataHoraInicio: this.toApiDateTime(raw.dataHoraInicio),
      dataHoraFim: this.toApiDateTime(raw.dataHoraFim),
      status: raw.status
    };

    this.salvando.set(true);
    this.erro.set('');

    const id = this.editandoId();
    const request = id
      ? this.agendamentoService.atualizar(id, payload)
      : this.agendamentoService.criar(payload);

    request.subscribe({
      next: () => {
        this.novo();
        this.carregar();
        this.salvando.set(false);
      },
      error: err => {
        this.erro.set(err.error?.mensagem ?? 'Erro ao salvar agendamento.');
        this.salvando.set(false);
      }
    });
  }

  excluir(id: number): void {
    if (!confirm('Deseja excluir este agendamento?')) {
      return;
    }

    this.agendamentoService.excluir(id).subscribe({
      next: () => this.carregar(),
      error: err => this.erro.set(err.error?.mensagem ?? 'Erro ao excluir agendamento.')
    });
  }

  private toInputDateTime(value: string): string {
    return value.length >= 16 ? value.slice(0, 16) : value;
  }

  private toApiDateTime(value: string): string {
    return value.length === 16 ? `${value}:00` : value;
  }
}
