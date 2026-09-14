package com.LuiizTeixeira.locadora.repository;

import com.LuiizTeixeira.locadora.entity.CarroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<CarroEntity, Long> {
}
