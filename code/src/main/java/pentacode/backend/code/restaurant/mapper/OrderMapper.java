package pentacode.backend.code.restaurant.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pentacode.backend.code.common.mapper.BaseMapper;
import pentacode.backend.code.restaurant.dto.OrderDTO;
import pentacode.backend.code.restaurant.entity.Order;

@Mapper(componentModel="spring")
public interface OrderMapper extends BaseMapper<Order, OrderDTO>{
    Order mapToEntity(OrderDTO orderDTO);
    OrderDTO mapToDTO(Order order);   
    List<OrderDTO> mapToListDTO(List<Order> orders);
    List<Order> mapToListEntity(List<OrderDTO> orderDTOs);
}
