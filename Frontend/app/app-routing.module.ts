import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { HomeComponent } from './home/home.component';
import { UserDashboardComponent } from './user-dashboard/user-dashboard.component';
import { AuthGuard } from './auth.guard';
import { CartComponent } from './cart/cart.component';
import { OrderHistoryComponent } from './order-history/order-history.component';
import { OrderDetailsComponent } from './order-detail/order-detail.component';

const routes: Routes = [
  { path: '', component: HomeComponent },    
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'user-dashboard', component: UserDashboardComponent , canActivate: [AuthGuard]},
  { path: 'cart' , component: CartComponent , canActivate: [AuthGuard]},
  { path: 'order-history', component: OrderHistoryComponent , canActivate: [AuthGuard]},
  { path: 'order-details/:id', component: OrderDetailsComponent , canActivate:[AuthGuard] }


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
