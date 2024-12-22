package com.apply.course.infra.db.user.repository;

import com.apply.course.infra.db.user.entity.UserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserCrudRepositoryTest {

    @Mock
    private UserCrudRepository userCrudRepository;

    @Test
    @DisplayName("사용자 저장 테스트")
    void testSaveUser() {
        UserEntity user = UserEntity.builder()
                .name("Test User")
                .build();

        when(userCrudRepository.save(user)).thenReturn(user);

        UserEntity savedUser = userCrudRepository.save(user);

        assertEquals("Test User", savedUser.getName());
    }
}