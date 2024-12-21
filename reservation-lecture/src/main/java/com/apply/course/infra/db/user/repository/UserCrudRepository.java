package com.apply.course.infra.db.user.repository;

import com.apply.course.infra.db.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCrudRepository extends JpaRepository<UserEntity, Long> {

}