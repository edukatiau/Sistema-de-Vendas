package org.rigon.projeto.domain.repository;

import org.rigon.projeto.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


}
