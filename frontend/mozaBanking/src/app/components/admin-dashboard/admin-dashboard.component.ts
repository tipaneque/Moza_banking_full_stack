import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';


import { AccountService, AccountCreatePayload } from '../../services/account.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatTableModule
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.scss'
})
export class AdminDashboardComponent implements OnInit {
  private fb = inject(FormBuilder);
  private accountService = inject(AccountService);

  accountForm: FormGroup = this.fb.group({
    userName: ['', Validators.required],
    nuit: ['', Validators.required],
    accountNumber: ['', Validators.required],
    balance: [0, [Validators.required, Validators.min(0)]],
    username: ['', Validators.required]
  });

  accounts: any[] = [];

  ngOnInit(): void {
    this.loadAccounts();
  }

  onCreateAccount() {
    if (this.accountForm.valid) {
      const payload: AccountCreatePayload = this.accountForm.value;
      this.accountService.createAccount(payload).subscribe({
        next: () => {
          alert('Conta criada com sucesso!');
          this.accountForm.reset();
          this.loadAccounts();
        },
        error: () => alert('Erro ao criar conta.')
      });
    }
  }

  loadAccounts() {
    this.accountService.getAllAccounts().subscribe({
      next: (data) => {
        this.accounts = data;
      },
      error: () => alert('Erro ao carregar contas.')
    });
  }
}
