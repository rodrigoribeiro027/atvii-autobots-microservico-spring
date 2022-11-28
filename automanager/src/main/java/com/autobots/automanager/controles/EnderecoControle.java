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
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;


@RestController
@RequestMapping("/Endereco")
public class EnderecoControle {

	
	@Autowired
	private EnderecoRepositorio repositorioEndereco;
	@Autowired
	private ClienteRepositorio repositorioCliente;
	
	@GetMapping("/enderecos")
	public List<Endereco> obterEndereco(){
		return repositorioEndereco.findAll();
	}
	@GetMapping("/enderecoid/{id}")
	public Endereco ObterEnderecoID(@PathVariable long id ) {
		return repositorioEndereco.findById(id).get();
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
