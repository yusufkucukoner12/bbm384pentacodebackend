package pentacode.backend.code.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pentacode.backend.code.common.entity.BaseAudityModel;
import pentacode.backend.code.common.mapper.BaseMapper;
import pentacode.backend.code.common.service.BaseService;
import pentacode.backend.code.common.utils.ResponseHandler;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseController<T extends BaseAudityModel, D extends BaseMapper> {  
    private BaseService<T> baseService;

    private D mapper;
    public ResponseEntity<Object> getByPkOr404(Long pk){
        try {
            T entity = (T) baseService.findByPkOr404(pk);
            return ResponseHandler.generatePkResponse("Success",HttpStatus.OK, mapper.mapToDTO(entity));
        } catch (Exception e) {
            return ResponseHandler.generatePkResponse("Failed", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}