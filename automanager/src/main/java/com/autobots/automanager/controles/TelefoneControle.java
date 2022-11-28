package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;


@RestController
@RequestMapping("/Telefone")
public class TelefoneControle {

	@Autowired
	private TelefoneRepositorio repositorioTelefone;
	
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/telefones")
	public List<Telefone> obterTelefone(){
		return repositorioTelefone.findAll();
	}
	@GetMapping("/telefone/{id}")
	public Telefone obterTelefoneID(@PathVariable long id) {
		return repositorioTelefone.findById(id).get();
	}
	@PutMapping("/atualizarTelefone")
	private void atualizarTelefone(@RequestBody Cliente atualizacao) {
	Cliente	cliente = repositorioCliente.getById(atualizacao.getId());
	cliente.getTelefones().addAll(atualizacao.getTelefones());
	repositorioCliente.save(cliente);
	}
	@DeleteMapping("/DeletarTelefones")
	public void excluirTelefones(@RequestBody Cliente excluir){
		Cliente selecionado = repositorioCliente.getById(excluir.getId());
		selecionado.getTelefones().clear();
		repositorioCliente.save(selecionado);
	}
}