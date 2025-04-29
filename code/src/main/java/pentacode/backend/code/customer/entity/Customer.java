package pentacode.backend.code.customer.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentacode.backend.code.common.entity.base.BaseAudityModel;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseAudityModel{
    private String address;
    private String phoneNumber;
    private String name;

    @Email
    private String email;


}
