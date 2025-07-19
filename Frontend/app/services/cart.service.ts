import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../user-dashboard/user-dashboard.component';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartSubject = new BehaviorSubject<{ [key: number]: { product: Product; quantity: number } }>({});
  cart$ = this.cartSubject.asObservable();

  constructor() {}

  // Returns current cart value
  getCart(): { [key: number]: { product: Product; quantity: number } } {
    return this.cartSubject.getValue();
  }

  // Updates the cart
  setCart(cart: { [key: number]: { product: Product; quantity: number } }): void {
    this.cartSubject.next(cart);
  }

  // Clears the cart
  clearCart(): void {
    this.cartSubject.next({});
  }
}
