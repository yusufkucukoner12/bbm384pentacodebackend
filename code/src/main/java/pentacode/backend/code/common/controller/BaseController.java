package pentacode.backend.code.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pentacode.backend.code.common.entity.BaseAudityModel;
import pentacode.backend.code.common.mapper.BaseMapper;
import pentacode.backend.code.common.service.BaseService;
import pentacode.backend.code.common.utils.ResponseHandler;


public class BaseController<T extends BaseAudityModel, D extends BaseMapper> {  
    private final BaseService<T> baseService;
    private final D mapper;

    public BaseController(BaseService<T> baseService, D mapper){
        this.baseService = baseService;
        this.mapper = mapper;
    }


    public ResponseEntity<Object> getByPkOr404(Long pk){
        try {
            T entity = (T) baseService.findByPkOr404(pk);
            return ResponseHandler.generatePkResponse("Success",HttpStatus.OK, mapper.mapToDTO(entity));
        } catch (Exception e) {
            return ResponseHandler.generatePkResponse("Failed", HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}