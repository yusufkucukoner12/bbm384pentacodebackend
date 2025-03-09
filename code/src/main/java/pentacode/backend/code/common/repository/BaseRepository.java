package pentacode.backend.code.common.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pentacode.backend.code.common.entity.BaseAudityModel;

public interface BaseRepository<O extends BaseAudityModel> extends JpaRepository<O, Long>{
    @Query("SELECT o FROM #{#entityName} o WHERE o.pk = pk")
    O findByPk(Long pk);
}
