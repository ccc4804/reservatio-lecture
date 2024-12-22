package com.apply.course.business.user.service;

import com.apply.course.business.user.vo.UserVO;
import com.apply.course.infra.db.user.entity.UserEntity;
import com.apply.course.infra.db.user.repository.UserCrudRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserCrudRepository userCrudRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUserByUserId_UserExists() {
        // Given
        Long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .userId(userId)
                .name("Test User")
                .build();

        UserVO expectedUserVO = UserVO.builder()
                .userId(userId)
                .name("Test User")
                .build();

        // Mock behavior
        when(userCrudRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        // When
        UserVO result = userService.getUserByUserId(userId);

        // Then
        assertNotNull(result, "결과는 null이 아니어야 합니다.");
        assertEquals(expectedUserVO.getUserId(), result.getUserId(), "결과는 기대한 UserVO와 동일해야 합니다.");

        // Verify repository interaction
        verify(userCrudRepository).findById(userId);
    }

    @Test
    void testGetUserByUserId_UserNotFound() {
        // Given
        Long userId = 99L;

        // Mock behavior for not found
        when(userCrudRepository.findById(userId)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> userService.getUserByUserId(userId));

        assertEquals("User not found.", exception.getMessage());
        verify(userCrudRepository).findById(userId);
    }
}