package com.devwue.spring.api.repository;

import com.devwue.spring.dto.entity.ServiceRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRoleRepository extends JpaRepository<ServiceRole, Long> {
    List<ServiceRole> findByUserId(Long userId);
}
