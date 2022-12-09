package com.api.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.controller.TabelaController;
import com.api.entity.Tabela;
import com.api.repository.TabelaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TabelaService {
	
	
	@Autowired
	Tabela tabela;
	
	@Autowired
	TabelaRepository tabelaRepository;
	
	public Optional<Tabela> getRegister(Integer id) {
		return tabelaRepository.findById(id);
	}
	
	public List<Tabela> getTabela( ) {
		return tabelaRepository.findAll();
	}
	
	public Page<Tabela> getPage(Pageable pageable ) {
		return tabelaRepository.findAll(pageable);
	}
	
	public Tabela postTabela(Tabela tabela ) {
		return tabelaRepository.save(tabela);
	}
	
	public void deleteTabela(Integer id) {
		tabelaRepository.deleteById(id);
	}
	
	public Tabela putTabela(Integer id, Tabela tabela) {
		//Tabela tabelaDB = tabelaRepository.findById(id);
		//if (tabelaDB.isPresent()) {
		//	return tabelaRepository.save(tabela);
		//}			
		return tabelaRepository.save(tabela);
	}
	
	public List<Tabela> getCustomQuery(String codigo) {
		log.info("Dentro do service usar query customizada");
		return tabelaRepository.useCustomQuery(codigo);
	}
}
