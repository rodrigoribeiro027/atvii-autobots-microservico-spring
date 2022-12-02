package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteAtualizador;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;

	@GetMapping("/cliente/{id}")
	public ResponseEntity<Cliente> obterCliente(@PathVariable long id) {
		List<Cliente> clientes = repositorio.findAll();
		Cliente cliente = selecionador.selecionar(clientes, id);
		if (cliente == null) {
			ResponseEntity<Cliente> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} 
		else {
			adicionadorLink.adicionarLink(cliente);
			adicionadorLink.adicionarLinkUpdate(cliente);
			adicionadorLink.adicionarLinkDelete(cliente);
			ResponseEntity<Cliente> resposta = new ResponseEntity<Cliente>(cliente, HttpStatus.FOUND);
			return resposta;
		}
	}
	@GetMapping("/cliente/{id}/ListaTelefones")
	public ResponseEntity<List<Telefone>> obterTelefone(@PathVariable Long id) {
		List<Cliente> clientes = repositorio.findAll();
		List<Telefone> telefone = selecionador.selecionarTelefone(clientes, id);
		if (telefone.isEmpty()) {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.LOCKED);
			return resposta;
		} 
		else {
			ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefone, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/cliente/{id}/ListaEnderecos")
	public ResponseEntity<Endereco> obterEndereco(@PathVariable Long id) {
		List<Cliente> clientes = repositorio.findAll();
		Endereco end = selecionador.selecionarEndereco(clientes, id);
		if (end == null){
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.LOCKED);
			return resposta;
		} 
		else {
			ResponseEntity<Endereco> resposta = new ResponseEntity<>(end, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	
	@GetMapping("/cliente/{id}/ListaDocumentos")
	public ResponseEntity<List<Documento>> obterDocumentos(@PathVariable Long id) {
		List<Cliente> clientes = repositorio.findAll();
		List<Documento> documento = selecionador.selecionarDocumento(clientes, id);
		if (documento.isEmpty()) {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.LOCKED);
			return resposta;
		} 
		else {
			ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documento, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> obterClientes() {
		List<Cliente> clientes = repositorio.findAll();
		if (clientes.isEmpty()) {
			ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} 
		else {
			for(Cliente cliente: clientes) {
				adicionadorLink.adicionarLinkUpdate(cliente);
			}
			adicionadorLink.adicionarLink(clientes);
			ResponseEntity<List<Cliente>> resposta = new ResponseEntity<>(clientes, HttpStatus.FOUND);
			return resposta;
		}
	}
	
	

	@PostMapping("/cliente/cadastro")
	public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente) {
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente clientecadastrado = repositorio.save(cliente);		
		if (clientecadastrado.getId() != null) {
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}

	@PutMapping("/cliente/atualizar")
	public ResponseEntity<?> atualizarCliente(@RequestBody Cliente atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorio.getById(atualizacao.getId());
		if (cliente != null) {
			ClienteAtualizador atualizador = new ClienteAtualizador();
			atualizador.atualizar(cliente, atualizacao);
			repositorio.save(cliente);
			status = HttpStatus.OK;
		} 
		else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/cliente/excluir")
	public ResponseEntity<?> excluirCliente(@RequestBody Cliente exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente cliente = repositorio.getById(exclusao.getId());
		if (cliente != null) {
			repositorio.delete(cliente);
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
