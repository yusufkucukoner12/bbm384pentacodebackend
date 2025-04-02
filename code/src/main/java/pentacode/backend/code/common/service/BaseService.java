package pentacode.backend.code.common.service;

import pentacode.backend.code.common.entity.BaseAudityModel;
import pentacode.backend.code.common.repository.BaseRepository;

public abstract class BaseService<Entity extends BaseAudityModel>{
	protected BaseRepository<Entity> repository;

    public BaseService(BaseRepository<Entity> repository){
        this.repository = repository;
    }

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