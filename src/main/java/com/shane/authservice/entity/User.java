/**
 * ClassName: User
 * Package: com.shane.authservice.entity
 * Description: Entity class for User
 *
 * @Author Shane Liu
 * @Create 2024/11/28 16:55
 * @Version 1.2
 */

package com.shane.authservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Username is required")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password is required")
    private String password;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
