import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html'
})
export class SignupComponent {
  signupData = {
    username: '',
    name: '',
    email: '',
    password: '',
    age: null
  };

  successMessage: string | null = null;
  errorMessage: string | null = null;
  validationErrors: {[key:string]:string} = {};
  userName: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  onSignup() {
    this.authService.signup(this.signupData).subscribe({
      next: (res) => {
        this.successMessage = res.message;
        this.errorMessage = null;
        this.userName = res.

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 3000);


      },
      error: (err) => {
        if (err.error && err.error.errors) {
          this.validationErrors = err.error.errors;  // This is a map of field -> message
        } else {
          this.errorMessage = err.error?.message || 'An unknown error occurred';
        }


      }
    });
  }
}
