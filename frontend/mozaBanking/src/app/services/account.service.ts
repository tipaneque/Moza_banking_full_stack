// src/app/services/account.service.ts
import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AccountCreatePayload {
  userName: string;
  nuit: string;
  accountNumber: string;
  balance: number;
  username: string;
}

export interface Transaction {
  amount: number;
  dateTime: string;
  type: 'ENVIADA' | 'RECEBIDA';
  otherAccount: string;
}

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/accounts';

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token') ?? '';
    return new HttpHeaders({ Authorization: `Bearer ${token}` });
  }

  createAccount(payload: AccountCreatePayload): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, payload, {
      headers: this.getAuthHeaders()
    });
  }

  getAllAccounts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/`, {
      headers: this.getAuthHeaders()
    });
  }

  getMyAccount(): Observable<AccountCreatePayload> {
    return this.http.get<AccountCreatePayload>(`${this.baseUrl}/me`, {
      headers: this.getAuthHeaders()
    });
  }

  transfer(payload: any): Observable<string> {
    const url = 'http://localhost:8080/api/v1/transactions/transfer';
    return this.http.post(url, payload, {
      headers: this.getAuthHeaders(),
      responseType: 'text'
    });
  }

  getTransactions(): Observable<Transaction[]> {
    const url = 'http://localhost:8080/api/v1/transactions/extract';
    return this.http.get<Transaction[]>(url, {
      headers: this.getAuthHeaders()
    });
  }
}
