package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.OrderStatus;
import com.project.shopapp.models.Users;
import com.project.shopapp.models.order;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import com.project.shopapp.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
  private final UserRepository userRepository;
  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;
  @Override
  public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
    //tim xem userId co ton tai hay khong
    Users user = userRepository.findById(orderDTO.getUserId())
      .orElseThrow(() -> new DataNotFoundException("Can not found user with id: "+orderDTO.getUserId()));
    //convert orderDTO -> order
    // dung thu vien modelMapper
    modelMapper.typeMap(OrderDTO.class, order.class)
      .addMappings(mapper -> mapper.skip(order::setId));
    order order = new order();
    modelMapper.map(orderDTO, order);
    order.setUser(user);
    order.setOrderDate(new Date());
    order.setStatus(OrderStatus.PENDING);
    // kiem tra shipping date phai >= ngay hom qua
    LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
    if(shippingDate.isBefore(LocalDate.now())){
      throw new DataNotFoundException("Date must be at least today !");
    }

    order.setActive(true);
    orderRepository.save(order);
    return modelMapper.map(order, OrderResponse.class);
  }

  @Override
  public OrderResponse getOrder(Long id) {
    return null;
  }

  @Override
  public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
    return null;
  }

  @Override
  public void deleteOrder(Long id) {

  }

  @Override
  public List<OrderResponse> getAllOrders(Long userId) {
    return null;
  }
}
