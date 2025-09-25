package com.nabiji.ecommerce.entity;
import com.nabiji.ecommerce.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Column(unique = true, nullable = false)
    @Getter @Setter
    private String username;


    @Column(nullable = false)
    @Getter @Setter
    private String password;

    @Column(nullable = false)
    @Getter @Setter
    private BigDecimal balance = new BigDecimal("100.00");

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter @Setter
    private Role role = Role.USER;

    @Column(nullable = false)
    @Getter @Setter
    private boolean active = true;

    @CreationTimestamp
    @Column(updatable = false)
    @Getter
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Getter
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    @Getter @Setter
    private Set<Order> orders;

    // Controlled access to password
    public String getPassword() {
        return password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }


    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}


