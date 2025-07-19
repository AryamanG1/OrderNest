import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from '../services/order.service';
import { Order } from '../services/product.service';

@Component({
  selector: 'app-order-history',
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css']
})
export class OrderHistoryComponent implements OnInit {
  orders: Order[] = [];

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit(): void {
    this.orderService.getOrderHistory().subscribe({
      next: (resp) => {
        this.orders = resp.data.reverse();
      },
      error: (err) => console.error('Failed to load orders', err)
    });
  }

  viewDetails(orderId: number): void {
  this.router.navigate(['/order-details', orderId]);  // Correct route
}
}
