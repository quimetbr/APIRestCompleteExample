package com.api.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.controller.TabelaController;
import com.api.entity.Tabela;

import lombok.extern.slf4j.Slf4j;

@Repository
public interface TabelaRepository extends JpaRepository<Tabela, Integer>  {
	
	//@Query(value = "select t from Tabela")
	//@Query(value = "select t from Tabela t where t.tabelaCampo1 =:codigo")
	@Query(value = "SELECT * FROM Tabela WHERE tabelaCampo1 = :codigo", nativeQuery = true)
	List<Tabela> useCustomQuery(@Param("codigo") String codigo);

}
