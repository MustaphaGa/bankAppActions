package com.example.bancApp.repositories;

import com.example.bancApp.models.Role;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
   Optional<Role>  findRoleByName(String roleName);
}
