import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  loginData = { username: '', password: '' };
  errorMessage: string | null = null;
  successMessage: string | null = null;
  username: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.authService.login(this.loginData).subscribe({
      next: (res) => {
        
        this.successMessage = " Successfully logged in , "
        this.errorMessage = null
        this.username = res.username
        console.log('Clearing token and username');
        localStorage.clear();
        console.log('Token after removal:', localStorage.getItem('token')); // should be null
        localStorage.setItem('token', res.jwt);
        localStorage.setItem('username',res.username);
        console.log(res.jwt,res.username);
        console.log(this.successMessage, " Welcome ", this.username);
        setTimeout(() => {
        this.successMessage = null;
        }, 5000);

this.router.navigate(['/user-dashboard'])
},
      error: (err) => {
        this.errorMessage = err.error.message
        
        this.successMessage = null
        
        console.error('Login Failed', this.errorMessage);
        
        setTimeout(() => {
        this.errorMessage = null;
      }, 3000);

      }
    });
  }
}
