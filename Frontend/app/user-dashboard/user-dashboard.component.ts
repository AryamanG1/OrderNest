import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { forkJoin } from 'rxjs';
import { OrderService } from '../services/order.service';
import { CartService } from '../services/cart.service';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {
  username: string = '';
  products: any[] = [];
  page: number = 1;
  categories: any[] = [];
  cart: { [key: number]: { product: Product; quantity: number } } = {};
  selectedCategory: string = '';
  minPrice: number = 0;
  maxPrice: number = 9999999;

  

  constructor(private productService: ProductService, 
              private cdr: ChangeDetectorRef , 
              private orderService: OrderService,
              private cartService: CartService 
              ) {}


  ngOnInit(): void {
    this.username = localStorage.getItem('username') || 'User';
    this.cartService.cart$.subscribe(cart => {
    this.cart = cart;
      });
    this.productService.getAllProducts().subscribe({
      next: (resp) => {
        this.products = resp.data;
        this.categories = [...new Set(resp.data.map((p: any) => p.category))];
        console.log('Extracted Categories:', this.categories);
      },
      error: (err) => {
        console.error('Error fetching products', err);
      }
    });
  }

  applyFilters(): void {
    const filterRequest = {
      category: this.selectedCategory || '', // fallback to 0th category or empty
      lowerPrice: this.minPrice || 0,
      upperPrice: this.maxPrice || 9999999
    };

    if(filterRequest.category==''){
      this.ngOnInit();
    }

    this.productService.searchProducts(filterRequest).subscribe({
      next: (resp) => {

        this.products = resp.data;
        this.page=1;
        this.cdr.detectChanges();
        console.log('found products',this.products)
      },
      error: (err) => {
        console.error('Error filtering products', err);
      }
    });
  }

  addToCart(product: Product): void {
  if (this.cart[product.productId]) {
    this.cart[product.productId].quantity++;
  } else {
    this.cart[product.productId] = { product, quantity: 1 };
  }
  console.log('Cart is: ',this.cart);
}

removeFromCart(product: Product): void {
  if (this.cart[product.productId]) {
    this.cart[product.productId].quantity--;
    if (this.cart[product.productId].quantity <= 0) {
      delete this.cart[product.productId];
    }
  }
    console.log('Cart is: ',this.cart);
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

export interface ApiResponse<T> {
  message: string;
  status: string;
  data: T;
}

export interface Order {
  orderId: number;
  billNo: number;
  createdAt: string; // You can use Date instead of string if you parse it
  paymentType: string; // Add other types if needed
  delivered: boolean;
}