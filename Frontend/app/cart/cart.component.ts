import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../services/cart.service';
import { forkJoin } from 'rxjs';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  cart: { [key: number]: { product: Product; quantity: number } } = {};
  successMessage = '';
  showSuccess = false;

  constructor(private router: Router , private cartService:CartService , private orderService: OrderService) {}  

  ngOnInit(): void {
    this.cart = this.cartService.getCart(); // Initial snapshot
    // Optional: use observable for reactive updates
    // this.cartService.cart$.subscribe(cart => this.cart = cart);
  }


  get totalPrice(): number {
    return Object.values(this.cart)
      .reduce((acc: number, item) => acc + item.product.price * item.quantity, 0);
  }

 removeProduct(productId: number): void {
    const updatedCart = { ...this.cart };
    if (updatedCart[productId]) {
      updatedCart[productId].quantity--;
      if (updatedCart[productId].quantity <= 0) {
        delete updatedCart[productId];
      }
      this.cartService.setCart(updatedCart);
      this.cart = updatedCart;
    }
  }

  placeOrderNow(): void {
    console.log("entered place order service");
  this.orderService.createOrder().subscribe({
    next: (resp) => {
      const orderId = resp.data.orderId;
      const addItemCalls = Object.values(this.cart).map(item =>
        this.orderService.addProductToOrder({
          orderId,
          productId: item.product.productId,
          quantity: item.quantity
        })
      );

      forkJoin(addItemCalls).subscribe({
        next: (results) => {
          console.log('Order placed', results);
          this.successMessage = 'Order placed successfully!';
          this.showSuccess = true; 
          this.cartService.clearCart();     // ✅ Clear cart in service
          this.cart = {};   
          setTimeout(() => {
            this.showSuccess = false;
          }, 3000);         
          this.cart = {};
        },
        error: (err) => console.error('Error placing order items', err)
      });
    },
    error: (err) => console.error('Error creating order', err)
  });
}

  goBack(): void {
    this.router.navigate(['/user-dashboard']);  
  }

  get hasItems(): boolean {
  return Object.keys(this.cart).length > 0;
}

placeOrder(): void {
  this.orderService.createOrder().subscribe({
    next: (resp) => {
      const orderId = resp.data.orderId;
      const addItemCalls = Object.values(this.cart).map(item =>
        this.orderService.addProductToOrder({
          orderId,
          productId: item.product.productId,
          quantity: item.quantity
        })
      );

      forkJoin(addItemCalls).subscribe({
        next: (results) => {
          console.log('Order placed', results);
          this.cart = {};
        },
        error: (err) => console.error('Error placing order items', err)
      });
    },
    error: (err) => console.error('Error creating order', err)
  });
}
}


export interface Product {
  productId: number;
  productName: string;
  price: number;
  category: string;
}
