package pentacode.backend.code.common.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.dto.ReviewDTO;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.entity.Review;
import pentacode.backend.code.common.mapper.base.BaseMapper;

@Mapper(componentModel="spring")
public interface ReviewMapper extends BaseMapper<Review, ReviewDTO>{
    Review mapToEntity(ReviewDTO reviewDTO);
    ReviewDTO mapToDTO(Review review);   
    List<ReviewDTO> mapToListDTO(List<Review> reviews);
    List<Review> mapToListEntity(List<ReviewDTO> reviewDTOs);
}


