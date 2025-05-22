package pentacode.backend.code.courier.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    public CourierDTO updateCourier(Long pk, CourierDTO dto) {
        Courier courier = findByPkOr404(pk);

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            courier.setName(dto.getName());
        }
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().trim().isEmpty()) {
            courier.setPhoneNumber(dto.getPhoneNumber());
        }
            courier.setAvailable(dto.isAvailable());
            courier.setOnline(dto.isOnline());
        
        Courier savedCourier = courierRepository.save(courier);
        return courierMapper.mapToDTO(savedCourier);
    }

    public CourierDTO updateProfilePicture(Long pk, MultipartFile file) {
        Courier courier = super.findByPkOr404(pk);

        try {
            String filename = pk + "_" + file.getOriginalFilename();
            String uploadDir = new File("src/main/resources/static/images").getAbsolutePath(); // writable dir in project root
            File saveFile = new File(uploadDir, filename);
            file.transferTo(saveFile); // saves the file

            // Expose file via URL
            String profilePictureUrl = "http://localhost:8080/images/" + filename;
            courier.setProfilePictureUrl(profilePictureUrl);
            courierRepository.save(courier);
            return courierMapper.mapToDTO(courier);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }
        
    }

    public List<CourierDTO> getAllCouriers() {
        List<Courier> couriers = courierRepository.findAllByDeletedFalse();
        return courierMapper.mapToListDTO(couriers);
    }
    

}