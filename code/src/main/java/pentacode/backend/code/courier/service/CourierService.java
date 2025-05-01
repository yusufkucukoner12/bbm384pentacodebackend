package pentacode.backend.code.courier.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pentacode.backend.code.common.service.base.BaseService;
import pentacode.backend.code.courier.dto.CourierDTO;
import pentacode.backend.code.courier.entity.Courier;
import pentacode.backend.code.courier.mapper.CourierMapper;
import pentacode.backend.code.courier.repository.CourierRepository;

@Service
public class CourierService extends BaseService<Courier> {
    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;
    
    public CourierService(CourierRepository courierRepository, CourierMapper courierMapper) {
        super(courierRepository);
        this.courierRepository = courierRepository;
        this.courierMapper = courierMapper;
    }
    
    public List<CourierDTO> getAvailableCouriers() {
        return courierMapper.mapToListDTO(courierRepository.findByIsOnlineAndIsAvailable(true, true));
    }
    
    public CourierDTO getByPk(Long pk) {
        return courierMapper.mapToDTO(super.findByPkOr404(pk));
    }
    
    public CourierDTO updateStatus(Long pk, boolean isAvailable, boolean isOnline) {
        Courier courier = super.findByPkOr404(pk);
        courier.setAvailable(isAvailable);
        courier.setOnline(isOnline);
        courierRepository.save(courier);
        return courierMapper.mapToDTO(courier);
    }
}