import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css']
})
export class OrderDetailsComponent implements OnInit {
  orderId!: number;
  orderDetails!: OrderDetails;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private orderService: OrderService
  ) {}

  ngOnInit(): void {
    this.orderId = +this.route.snapshot.paramMap.get('id')!;
    this.fetchOrderDetails();
  }

  fetchOrderDetails(): void {
    this.orderService.getOrderDetails(this.orderId).subscribe({
      next: (res) => {
        this.orderDetails = res.data;
      },
      error: (err) => console.error('Failed to fetch order details', err)
    });
  }

  goBack(): void {
    this.router.navigate(['/order-history']);
  }

  getTotalPrice(): number {
  return this.orderDetails?.products?.reduce((acc, p) => acc + (p.price * p.quantity), 0) || 0;
}
}

export interface Product {
  productId: number;
  productName: string;
  category: string;
  price: number;
  quantity: number;
}

export interface OrderDetails {
  orderId: number;
  billNo: number;
  createdAt: string;
  paymentType: string;
  delivered: boolean;
  products: Product[];
}
