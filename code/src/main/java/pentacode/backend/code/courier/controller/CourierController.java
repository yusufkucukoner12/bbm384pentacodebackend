package pentacode.backend.code.courier.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.courier.dto.CourierDTO;
import pentacode.backend.code.courier.service.CourierService;

@RestController
@RequestMapping("/api/couriers")
public class CourierController {
    private final CourierService courierService;
    
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }
    
    @GetMapping("/available")
    public ResponseEntity<Object> getAvailableCouriers() {
        List<CourierDTO> couriers = courierService.getAvailableCouriers();
        return ResponseHandler.generateListResponse("Available couriers fetched successfully", 
                                                   HttpStatus.OK, 
                                                   couriers, 
                                                   couriers.size());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCourierById(@PathVariable("id") Long id) {
        CourierDTO courier = courierService.getByPk(id);
        return ResponseHandler.generatePkResponse("Courier fetched successfully", 
                                                 HttpStatus.OK, 
                                                 courier);
    }
}