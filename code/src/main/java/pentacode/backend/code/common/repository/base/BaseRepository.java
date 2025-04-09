package pentacode.backend.code.common.repository.base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import pentacode.backend.code.common.entity.base.BaseAudityModel;

@Service
public interface BaseRepository<O extends BaseAudityModel> extends JpaRepository<O, Long>{
    O findByPk(Long pk);
}
