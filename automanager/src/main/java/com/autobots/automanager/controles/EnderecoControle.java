package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.AdicionadorLinkEndereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.modelos.EnderecoSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;


@RestController
@RequestMapping("/Endereco")
public class EnderecoControle {

	
	@Autowired
	private EnderecoRepositorio repositorioEndereco;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private AdicionadorLinkEndereco adicionadorLink;
	@Autowired
	private EnderecoSelecionador selecionadorEndereco;
	
	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> buscarEndereco() {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		if(enderecos.isEmpty()) {
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
			
		} else {
			adicionadorLink.adicionarLink(enderecos);
			ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
			return resposta;
		}
	}
	@GetMapping("/endereco/{id}")
	public ResponseEntity<Endereco> enderecosCliente(@PathVariable long id) {
		List<Endereco> enderecos = repositorioEndereco.findAll();
		Endereco endereco = selecionadorEndereco.selecionar(enderecos, id);
		if (endereco == null) {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
			
		} else {
			adicionadorLink.adicionarLink(endereco);
			ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
			return resposta;
		}
	}
	@PutMapping("/atualizarEndereco")
	
	public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Endereco selecionado = repositorioEndereco.getById(atualizacao.getId());
		if(selecionado != null) {
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(selecionado, atualizacao);
			repositorioEndereco.save(selecionado);
			status = HttpStatus.OK;
		}
		else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}
	
		
		
	@DeleteMapping("/deletarEndereco/{id}")
	public ResponseEntity<?> excluirEnderecos(@PathVariable Long id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<Cliente> alvos = repositorioCliente.findAll();
		if (alvos != null) {
			for (Cliente cliente: alvos) {
				if(cliente.getEndereco().getId() == id) {
					repositorioEndereco.delete(cliente.getEndereco());
					cliente.setEndereco(null);
					repositorioCliente.save(cliente);
					status = HttpStatus.OK;
				}
				
				
			}
		}
		return new ResponseEntity<>(status);
		
	}
	
}
