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
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;


@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

	@Autowired
	private TelefoneRepositorio repositorioTelefone;
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private AdicionadorLinkTelefone adicionadorLink;
	
	@GetMapping("/telefones")
	public ResponseEntity<List<Telefone>> buscarTelefones(){
		List<Telefone> telefones = repositorioTelefone.findAll();
		adicionadorLink.adicionarLink(telefones);
		return new ResponseEntity<List<Telefone>>(telefones,HttpStatus.FOUND);
	}
	
	@GetMapping("/telefone/{id}")
	public ResponseEntity<Telefone> buscarTelefoneID(@PathVariable Long id) {
		Telefone selecionado = repositorioTelefone.findById(id).orElse(null);
		HttpStatus status = null;
		if(selecionado == null) {
			status = HttpStatus.NOT_FOUND;
		}
		else {
			adicionadorLink.adicionarLink(selecionado);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Telefone>(selecionado,status);
	}
	
	@PutMapping("/atualizarTelefone")
	public void editarTelefonePorId(@RequestBody Telefone atualizacao) {
		Telefone telefoneSelecionado = repositorioTelefone.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(telefoneSelecionado, atualizacao);
		repositorioTelefone.save(telefoneSelecionado);
	}
	
	
}