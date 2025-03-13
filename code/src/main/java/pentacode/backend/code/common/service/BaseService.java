package pentacode.backend.code.common.service;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pentacode.backend.code.common.entity.BaseAudityModel;
import pentacode.backend.code.common.repository.BaseRepository;
@Service
@Data
@SuperBuilder
@NoArgsConstructor
public class BaseService<Entity extends BaseAudityModel>{
	protected BaseRepository<Entity> repository;

    public Entity findByPkOr404(Long pk){
        Entity entity = repository.findByPk(pk);
        if(entity == null){
            return null;
        }
        else{
            return entity;
        }
    }
}