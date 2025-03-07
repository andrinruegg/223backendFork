package com.example.demo.domain.user;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.group.Group;
import com.example.demo.domain.role.Role;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class User extends AbstractEntity {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "password")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles = new HashSet<>();

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "group_id", referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Group group;

  public User(UUID id, String firstName, String lastName, String email, String password, Set<Role> roles, Group group) {
    super(id);
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.roles = roles;
    this.group = group;
  }

}
