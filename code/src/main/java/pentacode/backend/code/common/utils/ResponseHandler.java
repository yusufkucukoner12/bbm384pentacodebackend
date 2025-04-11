package pentacode.backend.code.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static ResponseEntity<Object> generateListResponse(String message, HttpStatus status, Object responseObj, int count) {
        Map<String, Object> map = new HashMap<>();
        
        // Eğer responseObj null ise 404 hatası döner
        if (responseObj == null) {
            map.put("message", message);
            map.put("status", HttpStatus.NOT_FOUND.value());
            map.put("data", "Not found");
            map.put("code", 404);
        } else {
            // Eğer responseObj mevcutsa başarıyla yanıt döner
            map.put("message", message);
            map.put("status", status.value());
            map.put("code", 200);
            map.put("data", responseObj);
            map.put("Total Character Count", count);
        }
        
        // ResponseEntity ile dönüş
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generatePkResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();

        // Eğer responseObj null ise 404 hatası döner
        if (responseObj == null) {
            map.put("message", message);
            map.put("status", HttpStatus.NOT_FOUND.value());
            map.put("data", "Not found");
            map.put("code", 404);
        } else {
            // Eğer responseObj mevcutsa başarıyla yanıt döner
            map.put("message", message);
            map.put("status", status.value());
            map.put("code", 200);
            map.put("data", responseObj);
        }

        // ResponseEntity ile dönüş
        return new ResponseEntity<>(map, status);
    }
}
