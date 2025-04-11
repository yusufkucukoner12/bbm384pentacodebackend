package pentacode.backend.code.common.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import pentacode.backend.code.common.entity.base.BaseAudityModel;

@Entity 
@Getter
@Setter
@Table(name = "address") 
public class Address extends BaseAudityModel {

    @Column(name = "address_name", nullable = false, length = 50)
    private String addressName;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Column(name = "neighbourhood", nullable = false, length = 50)
    private String neighbourhood;

    @Column(name = "street", nullable = false, length = 50)
    private String street;

    @Column(name = "building_number", nullable = false, length = 10)
    private String buildingNumber;

    @Column(name = "door_number", nullable = false, length = 10)
    private String doorNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}