package com.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.entity.Tabela;

@Repository
public interface TabelaRepository extends JpaRepository<Tabela, Integer>  {

	 
}
