package com.nishanth.authserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nishanth.authserver.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long>{

//	@Query("SELECT user FROM UserEntity user WHERE BINARY  user.userName = :username, nativeQuery = true ")
//	@Query("SELECT u FROM UserEntity u WHERE u.userName = :username")
	UserEntity findByUserName(@Param("username") String username);

}
