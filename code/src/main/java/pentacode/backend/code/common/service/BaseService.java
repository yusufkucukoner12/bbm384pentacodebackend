package pentacode.backend.code.common.service;

import org.springframework.stereotype.Service;

import pentacode.backend.code.common.entity.BaseAudityModel;
import pentacode.backend.code.common.repository.BaseRepository;
// import pentacode.backend.code.common.repository.BaseRepository;

@Service
public class BaseService<Entity extends BaseAudityModel>{
	protected BaseRepository<Entity> repository;

    public BaseService(BaseRepository<Entity> repository){
        this.repository = repository;
    }

    public Entity findByPkOr404(Long pk){
        Entity entity = repository.findByPk(pk);
        if(entity == null){
            throw new RuntimeException("Entity not found");
        }
        else{
            System.out.println(entity.getPk());
            return entity;
        }
    }
}