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

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/v1/accounts';

  createAccount(payload: AccountCreatePayload): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, payload);
  }

  getAllAccounts(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/`);
  }

  // ✅ Novo método
  getMyAccount(): Observable<AccountCreatePayload> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<AccountCreatePayload>(`${this.baseUrl}/me`, { headers });
  }
}
