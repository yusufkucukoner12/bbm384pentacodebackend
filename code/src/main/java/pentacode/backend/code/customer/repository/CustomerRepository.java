package pentacode.backend.code.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import pentacode.backend.code.common.repository.base.BaseRepository;
import pentacode.backend.code.customer.dto.CustomerDTO;
import pentacode.backend.code.customer.entity.Customer;

public interface  CustomerRepository extends BaseRepository<Customer> {

}
