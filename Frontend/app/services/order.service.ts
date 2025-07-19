import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private baseUrl = 'http://localhost:8080/api/order';
  constructor(private http: HttpClient) {}

  createOrder(): Observable<any> {
    return this.http.post<ApiResponse<Product[]>>(`${this.baseUrl}`,{});
  }


  addProductToOrder(payload: {
      orderId: number;
      productId: number;
      quantity: number;
    }): Observable<any> {
      return this.http.post(`${this.baseUrl}/item`, payload);
    }

  getOrderHistory(){
    return this.http.get<ApiResponse<Order[]>>(`${this.baseUrl}`);
  }

  getOrderDetails(orderId: number) {
  return this.http.get<{ message: string, status: string, data: OrderDetails }>(
    `http://localhost:8080/api/order/${orderId}`
  );
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


export interface OrderProduct {
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
  products: OrderProduct[];
}