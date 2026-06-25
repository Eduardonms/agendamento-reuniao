import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Agendamento, StatusAgendamento } from '../models/agendamento.model';

@Injectable({ providedIn: 'root' })
export class AgendamentoService {
  private readonly baseUrl = `${environment.apiUrl}/api/agendamentos`;

  constructor(private readonly http: HttpClient) {}

  listar(filtros?: {
    status?: StatusAgendamento;
    responsavel?: string;
    inicio?: string;
    fim?: string;
  }): Observable<Agendamento[]> {
    const params: Record<string, string> = {};
    if (filtros?.status) params['status'] = filtros.status;
    if (filtros?.responsavel) params['responsavel'] = filtros.responsavel;
    if (filtros?.inicio) params['inicio'] = filtros.inicio;
    if (filtros?.fim) params['fim'] = filtros.fim;
    return this.http.get<Agendamento[]>(this.baseUrl, { params });
  }

  buscar(id: number): Observable<Agendamento> {
    return this.http.get<Agendamento>(`${this.baseUrl}/${id}`);
  }

  criar(agendamento: Agendamento): Observable<Agendamento> {
    return this.http.post<Agendamento>(this.baseUrl, agendamento);
  }

  atualizar(id: number, agendamento: Agendamento): Observable<Agendamento> {
    return this.http.put<Agendamento>(`${this.baseUrl}/${id}`, agendamento);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
