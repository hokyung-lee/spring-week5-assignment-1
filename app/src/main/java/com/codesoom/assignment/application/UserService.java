package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.User;
import com.codesoom.assignment.domain.UserRepository;
import com.codesoom.assignment.dto.UserPostDto;
import com.codesoom.assignment.dto.UserUpdateDto;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.stereotype.Service;

/**
 * 사용자를 추가,수정,삭제합니다.
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Mapper mapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.mapper = DozerBeanMapperBuilder.buildDefault();
    }

    /**
     * 해당 식별자의 사용자를 리턴합니다.
     * @param id 사용자 식별자
     * @param message 해당 식별자의 사용자를 찾지 못한 경우 보여줄 메시지
     * @return 사용자
     * @throws UserNotFoundException 해당 식별자의 사용자를 찾지 못한 경우
     */
    public User findUserById(Long id, String message) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id, message));
    }

    /**
     * 사용자를 생성하여 리턴합니다.
     * @param userPostDto 사용자 생성 정보
     * @return 생성된 사용자
     */
    public User createUser(UserPostDto userPostDto) {
        User user = mapper.map(userPostDto, User.class);

        userRepository.save(user);

        return user;
    }

    /**
     * 해당 식별자의 사용자를 삭제합니다.
     * @param id 사용자 식별자
     */
    public void deleteUser(Long id) {
        String failureMessage = String.format(
                "id가 %d 인 사용자를 찾지 못했기 떄문에 사용자 정보를 삭제하지 못했습니다.",
                id);

        findUserById(id, failureMessage);

        this.userRepository.deleteById(id);
    }

    /**
     * 해당 식별자의 사용자 정보를 수정하고 리턴합니다.
     * @param id 사용자 식별자
     * @param userDto 사용자 수정 정보
     * @return 수정된 사용자
     */
    public User updateUser(Long id, UserUpdateDto userDto) {
        String failureMessage = String.format(
                "id가 %d 인 사용자를 찾지 못했기 떄문에 사용자 정보를 업데이트하지 못했습니다.",
                id);

        User user = findUserById(id, failureMessage);

        User updatedUser = user.update(
                userDto.getName(),
                userDto.getPassword()
        );

        this.userRepository.save(updatedUser);

        return updatedUser;
    }
}