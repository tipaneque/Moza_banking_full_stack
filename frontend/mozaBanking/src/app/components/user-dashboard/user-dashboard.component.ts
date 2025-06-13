import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountService, AccountCreatePayload, Transaction } from '../../services/account.service';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.scss'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule,
    MatCardModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule
  ]
})
export class UserDashboardComponent implements OnInit {

  account?: AccountCreatePayload;
  transferForm!: FormGroup;
  isLoading = true;
  transactions: Transaction[] = [];

  constructor(private accountService: AccountService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.accountService.getMyAccount().subscribe({
      next: (data) => {
        this.account = data;
        this.initForm(data.accountNumber);
        this.loadTransactions();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Erro ao buscar dados da conta', err);
        alert('Erro ao carregar dados da conta.');
        this.isLoading = false;
      }
    });
  }

  initForm(fromAccountNumber: string): void {
    this.transferForm = this.fb.group({
      fromAccountNumber: [{ value: fromAccountNumber, disabled: true }, Validators.required],
      toAccountNumber: ['', Validators.required],
      amount: [0, [Validators.required, Validators.min(1)]],
      description: ['', Validators.required]
    });
  }

  

submitTransfer(): void {
  if (this.transferForm.invalid || !this.account) return;

  const payload = this.transferForm.getRawValue();

  this.accountService.transfer(payload).subscribe({
    next: (response) => {
      alert(response);
      this.transferForm.reset({
        fromAccountNumber: this.account?.accountNumber,
        toAccountNumber: '',
        amount: 0,
        description: ''
      });
      this.loadTransactions();
      this.accountService.getMyAccount().subscribe({
        next: (updatedAccount) => {
          this.account = updatedAccount;
        },
        error: () => {
          console.warn('Não foi possível atualizar saldo após transferência.');
        }
      });
    },
    error: (err) => {
      console.error('Erro ao fazer transferência:', err);
      alert('Erro ao efetuar transferência.');
    }
  });
  
}

loadTransactions(): void {
  this.accountService.getTransactions().subscribe({
    next: (data) => {
      this.transactions = data;
    },
    error: (err) => {
      console.error('Erro ao carregar extrato:', err);
      alert('Erro ao carregar extrato de transações.');
    }
  });
}

}
