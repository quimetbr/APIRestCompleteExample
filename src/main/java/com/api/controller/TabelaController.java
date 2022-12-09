package com.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.DTO.APIError;
import com.api.entity.Tabela;
import com.api.service.TabelaService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TabelaController {
	
	@Autowired
	Tabela tabela;
	
	@Autowired
	TabelaService tabelaService;
	
	@PostMapping("/tabelas/")
    //public Tabela postTabela(@RequestBody Tabela tabela) {
	//	log.info("Dentro do PostMapping");
    //    return tabelaService.postTabela(tabela);
    //}
	
	public ResponseEntity<Tabela> postTabela(@RequestBody Tabela tabela) {
		log.info("Dentro do Post controller");
		//if (true) {
		//	throw new NullPointerException();
		//}
		return new ResponseEntity(tabelaService.postTabela(tabela), HttpStatus.CREATED);
	}
	
	@GetMapping("/tabelas/{id}")
    public ResponseEntity<Tabela> getTabelaById(@PathVariable("id") Integer tabelaId) {
		log.info("Dentro do GetMapping por id: " + tabelaId);
		//optional já que pode não retornar valor para esse id
		Optional<Tabela> tabelaBanco = tabelaService.getRegister(tabelaId);
		log.info("tabelaBanco = "+tabelaBanco.toString());
		if (tabelaBanco.isEmpty()) {
			log.info("tabelaBanco é nulo");
			APIError error = new APIError(HttpStatus.NOT_FOUND.value(), "Registro não encontrado");
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		};
		return new ResponseEntity(tabelaBanco, HttpStatus.OK);
    }

	@GetMapping("/tabelas/")
	public ResponseEntity<List<Tabela>> getTabela( ){
		log.info("Dentro do GetMapping para obter listas");
		List<Tabela> lista = tabelaService.getTabela();
		if (lista.isEmpty()) {
			log.info("Sem lista que retornar");
			APIError error = new APIError(HttpStatus.NOT_FOUND.value(), "Sem lista que retornar");
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(lista, HttpStatus.OK);
	}
	
	@GetMapping("/tabelas/customQuery/{codigo}")
	public ResponseEntity<List<Tabela>> getCustomQueryTabela(@PathVariable("codigo") String codigo){
		log.info("Dentro do GetMapping para usar query customizada");
		List<Tabela> lista = tabelaService.getCustomQuery(codigo);
		if (lista.isEmpty()) {
			log.info("Sem lista que retornar");
			APIError error = new APIError(HttpStatus.NOT_FOUND.value(), "Sem lista que retornar via customização");
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(lista, HttpStatus.OK);
	}
	

	@GetMapping("/tabelas/paginas/")
	//http://localhost:8080/tabelas/paginas/?page=0&size=10
	public ResponseEntity<Page<Tabela>> pageTabela(Pageable pageable){
		log.info("Dentro do GetMapping para obter páginas");
		Page<Tabela> pagina = tabelaService.getPage(pageable);
		if (pagina.getTotalElements()== 0 ) {
			log.info("Sem pagina que retornar");
			APIError error = new APIError(HttpStatus.NOT_FOUND.value(), "Sem página que retornar");
			log.info("Error: " + error.toString());
			//o código NOCONTENT faz que o ResponseEntity não devolva o objeto error
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
		if (!pagina.isLast()) {
			log.info("Temos mais páginas que retornar");
			return new ResponseEntity(pagina, HttpStatus.PARTIAL_CONTENT);
		}
		else {
			log.info("Apenas uma página ou já é a última");
			return new ResponseEntity(pagina, HttpStatus.OK);	
		}	
	}
	
	@DeleteMapping("/tabelas/{id}")
	public ResponseEntity<Object> deleteRegistro(@PathVariable("id") Integer tabelaId){
		log.info("Dentro do DeleteMapping");
		if (tabelaService.getRegister(tabelaId).isPresent()) {
			log.info("Registro a deletar encontrato");
			tabelaService.deleteTabela(tabelaId);
			return new ResponseEntity(HttpStatus.OK);				
		}
		else
		{
			log.info("Registro a deletar não encontrado");
			APIError error = new APIError(HttpStatus.NOT_FOUND.value(), "Sem registro a deletar");
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
		}
		
	
		
	}
	
	@PutMapping("/tabelas/{id}")
	public ResponseEntity<Tabela> updateTabela(@PathVariable("id") Integer id, @RequestBody Tabela tabela){
		Optional<Tabela> tabelaDB = tabelaService.getRegister(id);
		if (tabelaDB.isPresent()){
			log.info("Registro a atualizar encontrado");
			Tabela tabelaUpdated = tabelaService.putTabela(id, tabela);
			return new ResponseEntity(tabelaUpdated, HttpStatus.OK);
		}
		else {
			log.info("Registro a atualizar não encontrado");
			APIError error = new APIError(HttpStatus.NOT_FOUND.value(), "Sem registro a atualizar");
			return new ResponseEntity(error, HttpStatus.NOT_FOUND);
			
		}
			
	}
		
		
	
}
