package br.ifg.urt.shieldnoterpgbox.mapper;

import br.ifg.urt.shieldnoterpgbox.dto.response.UserResponse;
import br.ifg.urt.shieldnoterpgbox.model.User;
import org.mapstruct.Mapper;
import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponseDTO(User user);
    List<UserResponse> toResponseDTOList(List<User> users);
}
