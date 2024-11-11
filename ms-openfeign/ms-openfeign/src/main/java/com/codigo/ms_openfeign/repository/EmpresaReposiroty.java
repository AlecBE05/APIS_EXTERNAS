package com.codigo.ms_openfeign.repository;

import com.codigo.ms_openfeign.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaReposiroty extends JpaRepository<EmpresaEntity, Long> {
}
