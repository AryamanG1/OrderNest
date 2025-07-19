package com.sample.Work.service;

import java.util.List;

import com.sample.Work.model.OrderEntity;
import com.sample.Work.payload.AddProductToOrderRequestDTO;
import com.sample.Work.payload.DeleteOrderRequestDTO;
import com.sample.Work.payload.OrderResponseDTO;
import com.sample.Work.payload.OrderResponseDetailDTO;
import com.sample.Work.payload.RemoveProductToOrderRequestDTO;
import com.sample.Work.payload.UpdateProductInOrderRequestDTO;

public interface OrderService {
		public OrderResponseDTO createNewOrder();
		public OrderEntity addItemToOrder(AddProductToOrderRequestDTO request);
		public OrderEntity removeItemFromOrder(RemoveProductToOrderRequestDTO request);
		public OrderEntity updateItemInOrder(UpdateProductInOrderRequestDTO request);
		public OrderResponseDTO deleteOrder(DeleteOrderRequestDTO request);
		public List<OrderResponseDTO> displayAllOrderAdmin();
		public List<OrderResponseDTO> displayOrderHistoryUser();
		public OrderResponseDetailDTO displayOrderDetailsUser(Long orderId);

}

