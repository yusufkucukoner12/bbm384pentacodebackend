package pentacode.backend.code.common.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import pentacode.backend.code.common.entity.BaseAudityModel;

public interface BaseRepository<O extends BaseAudityModel> extends JpaRepository<O, Long>{
    O findByPk(Long pk);
}
