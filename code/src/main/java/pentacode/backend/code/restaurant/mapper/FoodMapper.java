package pentacode.backend.code.restaurant.mapper;
import java.util.List;
import org.mapstruct.Mapper;
import pentacode.backend.code.common.mapper.BaseMapper;
import pentacode.backend.code.restaurant.dto.FoodDTO;
import pentacode.backend.code.restaurant.entity.Food;

@Mapper(componentModel="spring")
public interface FoodMapper extends BaseMapper<Food, FoodDTO> {
    FoodDTO mapToDTO(Food food);
    Food mapToEntity(FoodDTO foodDTO);
    List<FoodDTO> mapToListDTO(List<Food> foods);
    List<Food> mapToListEntity(List<FoodDTO> foodDTOs);
}                                               