package com.sample.Work.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sample.Work.model.*;
import com.sample.Work.payload.*;
import com.sample.Work.repository.*;
import com.sample.Work.utils.ExceptionUtils;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserOrderRepository userOrderRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            ProductRepository productRepository,
                            UserOrderRepository userOrderRepository,
                            OrderProductRepository orderProductRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userOrderRepository = userOrderRepository;
        this.orderProductRepository = orderProductRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OrderResponseDTO createNewOrder() {
        logger.trace("Entered createNewOrder()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.debug("Creating order for user: {}", auth.getName());

        OrderEntity order = new OrderEntity();
        orderRepository.save(order);
        logger.debug("Saved new order: {}", order);

        UserEntity user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> ExceptionUtils.userNotFoundError(auth.getName()));

        UserOrderEntity userOrder = new UserOrderEntity(user, order);
        userOrderRepository.save(userOrder);
        logger.debug("Created user-order mapping: {}", userOrder);

        logger.trace("Exiting createNewOrder()");
        return new OrderResponseDTO(order);
    }

    @Override
    public OrderEntity addItemToOrder(AddProductToOrderRequestDTO request) {
        logger.trace("Entered addItemToOrder()");

        OrderEntity order;
        if (request.getOrderId() == null) {
            logger.debug("Order ID is null. Creating new order...");
            OrderResponseDTO newOrder = createNewOrder();
            order = orderRepository.findById(newOrder.getOrderId())
                    .orElseThrow(() -> ExceptionUtils.orderNotFoundError(newOrder.getOrderId()));
        } else {
            order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> ExceptionUtils.orderNotFoundError(request.getOrderId()));
            logger.debug("Fetched existing order: {}", order);
        }

        ProductEntity product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> ExceptionUtils.productNotFoundError(request.getProductId()));
        logger.debug("Fetched product: {}", product);

        OrderProductEntity orderProduct = new OrderProductEntity(order, product, request.getQuantity());
        orderProductRepository.save(orderProduct);
        logger.debug("Saved order-product mapping: {}", orderProduct);

        Long addedPrice = product.getPrice() * request.getQuantity();
        order.setTotalPrice(addedPrice);
        orderRepository.save(order);
        logger.debug("Updated order total price: {}", order.getTotalPrice());

        logger.trace("Exiting addItemToOrder()");
        return order;
    }

    @Override
    public OrderEntity removeItemFromOrder(RemoveProductToOrderRequestDTO request) {
        logger.trace("Entered removeItemFromOrder()");

        OrderProductId id = new OrderProductId(request.getOrderId(), request.getProductId());
        OrderProductEntity relation = orderProductRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.orderProductNotFoundError(id));
        OrderEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> ExceptionUtils.orderNotFoundError(request.getOrderId()));

        int newQuantity = relation.getQuantity() - request.getRemoveQuantity();
        if (newQuantity < 0) {
            logger.error("Remove quantity exceeds current quantity. Requested: {}, Existing: {}",
                    request.getRemoveQuantity(), relation.getQuantity());
            throw new RuntimeException("Remove quantity exceeds existing quantity.");
        }

        Long removedAmount = relation.getProduct().getPrice() * request.getRemoveQuantity();
        order.setTotalPrice(order.getTotalPrice() - removedAmount);

        if (newQuantity == 0) {
            orderProductRepository.delete(relation);
            logger.debug("Deleted order-product mapping: {}", relation);
        } else {
            relation.setQuantity(newQuantity);
            orderProductRepository.save(relation);
            logger.debug("Updated product quantity in order: {}", relation);
        }

        orderRepository.save(order);
        logger.debug("Updated order total after removal: {}", order.getTotalPrice());

        logger.trace("Exiting removeItemFromOrder()");
        return order;
    }

    @Override
    public OrderEntity updateItemInOrder(UpdateProductInOrderRequestDTO request) {
        logger.trace("Entered updateItemInOrder()");

        OrderProductId id = new OrderProductId(request.getOrderId(), request.getProductId());
        OrderProductEntity relation = orderProductRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.orderProductNotFoundError(id));
        OrderEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> ExceptionUtils.orderNotFoundError(request.getOrderId()));

        int newQuantity = request.getChangeQuantity();
        if (newQuantity < 0) {
            logger.error("Invalid quantity: {}", newQuantity);
            throw new RuntimeException("New quantity cannot be less than zero");
        }

        Long unitPrice = relation.getProduct().getPrice();
        Long oldTotal = unitPrice * relation.getQuantity();
        Long newTotal = unitPrice * newQuantity;

        if (newQuantity == 0) {
            order.setTotalPrice(order.getTotalPrice() - oldTotal);
            orderProductRepository.delete(relation);
            logger.debug("Deleted order-product mapping (quantity set to 0): {}", relation);
        } else {
            relation.setQuantity(newQuantity);
            order.setTotalPrice(order.getTotalPrice() + (newTotal - oldTotal));
            orderProductRepository.save(relation);
            logger.debug("Updated quantity in order-product mapping: {}", relation);
        }

        orderRepository.save(order);
        logger.debug("Updated order total price: {}", order.getTotalPrice());

        logger.trace("Exiting updateItemInOrder()");
        return order;
    }

    @Override
    public OrderResponseDTO deleteOrder(DeleteOrderRequestDTO request) {
        logger.trace("Entered deleteOrder()");

        OrderEntity order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> ExceptionUtils.orderNotFoundError(request.getOrderId()));

        orderRepository.delete(order);
        logger.debug("Deleted order: {}", order);

        logger.trace("Exiting deleteOrder()");
        return new OrderResponseDTO(order);
    }

    @Override
    public List<OrderResponseDTO> displayAllOrderAdmin() {
        logger.trace("Entered displayAllOrderAdmin()");

        List<OrderResponseDTO> orders = orderRepository.findAll()
                .stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());

        logger.debug("Fetched all orders: count = {}", orders.size());

        logger.trace("Exiting displayAllOrderAdmin()");
        return orders;
    }

    @Override
    public List<OrderResponseDTO> displayOrderHistoryUser() {
        logger.trace("Entered displayOrderHistoryUser()");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> ExceptionUtils.userNotFoundError(auth.getName()));

        List<OrderResponseDTO> history = userOrderRepository.findByUser(user)
                .stream()
                .map(uo -> new OrderResponseDTO(uo.getOrder()))
                .collect(Collectors.toList());

        logger.debug("Fetched order history for user {}: count = {}", auth.getName(), history.size());

        logger.trace("Exiting displayOrderHistoryUser()");
        return history;
    }
    
	@Override
	public OrderResponseDetailDTO displayOrderDetailsUser(Long orderId) {
		logger.trace("Entered displayOrderDetailsUser()");
		
		OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Could not find order details"));
		OrderResponseDetailDTO response = new OrderResponseDetailDTO();
		response.setBillNo(order.getBillNo());
		response.setCreatedAt(order.getCreatedAt());
		response.setDelivered(order.isDelivered());
		response.setOrderId(orderId);
		response.setPaymentType(order.getPaymentType());
		response.setProducts(order.getProducts()
								  .stream()

			.map(rel -> new ProductResponseDetailsDTO(
			    rel.getProduct().getProductId(),
			    rel.getProduct().getProductName(),
			    rel.getProduct().getCategory(),
			    rel.getProduct().getPrice(),
			    rel.getQuantity()))
			
			.collect(Collectors.toList()));
					return response;
				}

}
