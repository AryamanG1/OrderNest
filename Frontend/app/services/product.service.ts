import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api/product';

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<any> {
    return this.http.get<ApiResponse<Product[]>>(`${this.baseUrl}/all`);
  }

  searchProducts(body: { category: string; lowerPrice: number; upperPrice: number }): Observable<any> {
  const { category, lowerPrice, upperPrice } = body;

  const payload = {
    category,
    lowerPrice,
    upperPrice
  };

  return this.http.post<ApiResponse<Product[]>>(`${this.baseUrl}/search`, payload);
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



