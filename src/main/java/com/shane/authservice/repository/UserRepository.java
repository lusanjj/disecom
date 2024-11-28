/**
 * ClassName: UserRepository
 * Package: com.shane.authservice.repository
 * Description: Repository interface for User entity
 *
 * @Author Shane Liu
 * @Create 2024/11/28 16:45
 * @Version 1.1
 */

package com.shane.authservice.repository;

import com.shane.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
