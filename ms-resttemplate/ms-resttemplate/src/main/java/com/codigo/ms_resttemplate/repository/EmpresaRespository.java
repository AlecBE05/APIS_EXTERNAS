package com.codigo.ms_resttemplate.repository;

import com.codigo.ms_resttemplate.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRespository extends JpaRepository<EmpresaEntity,Long> {
}
