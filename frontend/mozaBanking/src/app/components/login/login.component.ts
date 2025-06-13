import { Component, inject } from '@angular/core';

import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';


// Angular Material
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { Router } from '@angular/router';
import {jwtDecode} from 'jwt-decode';
import { AuthService } from '../../services/auth-service.service';

@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

   private fb = inject(FormBuilder);
  private router = inject(Router);
  private authService = inject(AuthService);

  loginForm: FormGroup = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  onSubmit() {
    if (this.loginForm.valid) {
      localStorage.removeItem('token');
      this.authService.login(this.loginForm.value).subscribe({
        next: () => {
          const token = localStorage.getItem('token');
          if (token) {
            const decoded: any = jwtDecode(token);
            const role = decoded.role || decoded.roles?.[0];

            if (role === 'ROLE_ADMIN') {
              this.router.navigate(['/admin-dashboard']);
            } else if (role === 'ROLE_CLIENTE') {
              this.router.navigate(['/user-dashboard']);
            } else {
              alert('Perfil não autorizado.');
            }
          }
        },
        error: () => {
          alert('Credenciais inválidas');
        }
      });
    }
  }

}
