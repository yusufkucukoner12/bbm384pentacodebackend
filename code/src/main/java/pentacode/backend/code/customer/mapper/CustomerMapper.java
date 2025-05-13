package pentacode.backend.code.customer.mapper;
import java.util.List;
import org.mapstruct.Mapper;

import pentacode.backend.code.common.mapper.base.BaseMapper;
import pentacode.backend.code.customer.dto.CustomerDTO;
import pentacode.backend.code.customer.entity.Customer;


@Mapper(componentModel="spring")
public interface CustomerMapper extends BaseMapper<Customer, CustomerDTO> {
    Customer mapToEntity(CustomerDTO customerDTO);
    CustomerDTO mapToDTO(Customer customer);   
    List<CustomerDTO> mapToListDTO(List<Customer> customers); 
}