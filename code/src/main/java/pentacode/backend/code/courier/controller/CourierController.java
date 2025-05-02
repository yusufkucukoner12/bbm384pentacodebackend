package pentacode.backend.code.courier.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pentacode.backend.code.auth.entity.User;
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
    public ResponseEntity<Object> getCourierById(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        CourierDTO courier = courierService.getByPk(id);
        return ResponseHandler.generatePkResponse("Courier fetched successfully",
                                                 HttpStatus.OK,
                                                 courier);
    }

    @GetMapping("/status")
    public ResponseEntity<Object> getCourierStatus(@AuthenticationPrincipal User user) {
        CourierDTO courier = courierService.getByPk(user.getCourier().getPk());
        return ResponseHandler.generatePkResponse("Courier status fetched successfully",
                                                 HttpStatus.OK,
                                                 courier);
    }

    @PatchMapping("/status")
    public ResponseEntity<Object> updateCourierStatus(
            @RequestBody CourierDTO statusUpdate,
            @AuthenticationPrincipal User user) {
        CourierDTO updatedCourier = courierService.updateCourier(user.getCourier().getPk(), statusUpdate);
        return ResponseHandler.generatePkResponse("Courier profile updated successfully",
                                                 HttpStatus.OK,
                                                 updatedCourier);
    }

    @PostMapping("/profile-picture")
    public ResponseEntity<Object> uploadProfilePicture(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user) {
        System.out.println("File: " + file.getOriginalFilename());
        System.out.println("File size: " + file.getSize());
        CourierDTO updatedCourier = courierService.updateProfilePicture(user.getCourier().getPk(), file);
        return ResponseHandler.generatePkResponse("Profile picture uploaded successfully",
                                                 HttpStatus.OK,
                                                 updatedCourier);
    }
}