package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Documento;

import com.autobots.automanager.modelos.AdicionadorLinkDocumento;

import com.autobots.automanager.modelos.DocumentoAtualizador;

import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {

	@Autowired
	private DocumentoRepositorio repositorio;
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos(){
		List<Documento> documentos = repositorio.findAll();
		
		adicionadorLink.adicionarLink(documentos);
		return new ResponseEntity<List<Documento>>(documentos,HttpStatus.FOUND);
	}
	
	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> obterDocumentoID(@PathVariable Long id) {
		Documento documento = repositorio.findById(id).orElse(null);
		HttpStatus status = null;
		if(documento == null) {
			status = HttpStatus.NOT_FOUND;
		}
		else{
			adicionadorLink.adicionarLink(documento);
			status = HttpStatus.FOUND;
		}
		return new ResponseEntity<Documento>(documento,status);
	}
	
	@PutMapping("/atualizar")
	public void editarDocumentoPorId(@RequestBody Documento atualizacao) {
		Documento documentoSelecionado = repositorio.getById(atualizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documentoSelecionado, atualizacao);
		repositorio.save(documentoSelecionado);
	}
	
	
	
	
}
