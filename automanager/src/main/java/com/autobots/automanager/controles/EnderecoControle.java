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
	private void atualizarEndereco(@RequestBody Endereco atualizacao) {
		Endereco selecionado = repositorioEndereco.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(selecionado, atualizacao);
		repositorioEndereco.save(selecionado);
		}
	@DeleteMapping("/DeletarEnderecos")
	public void excluirEnderecos(@RequestBody Cliente excluir) {
		Cliente selecionado = repositorioCliente.getById(excluir.getId());
		repositorioEndereco.delete(selecionado.getEndereco());
		selecionado.setEndereco(null);
		repositorioCliente.save(selecionado);
	}
	
}
