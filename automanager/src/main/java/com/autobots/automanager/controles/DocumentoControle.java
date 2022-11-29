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
import com.autobots.automanager.entidades.Documento;

import com.autobots.automanager.modelos.AdicionadorLinkDocumento;

import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private ClienteRepositorio repositorio;
	@Autowired
	private DocumentoRepositorio repositoriodoc;
	@Autowired
	private AdicionadorLinkDocumento adicionadorLink;
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> obterDocumentos(){
		List<Documento> documentos = repositoriodoc.findAll();
		
		adicionadorLink.adicionarLink(documentos);
		return new ResponseEntity<List<Documento>>(documentos,HttpStatus.FOUND);
	}
	
	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> obterDocumentoID(@PathVariable Long id) {
		Documento documento = repositoriodoc.findById(id).orElse(null);
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
	
	@DeleteMapping("/excluir/{id}")
	public ResponseEntity<?> excluirDocumento(@PathVariable long id, @RequestBody Cliente exclusao) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Cliente alvo = repositorio.getById(id);
		
		if (alvo != null) {
			List<Documento> documentos = alvo.getDocumentos(); 
			for (Documento documento : documentos) { 
				if (documento.getId() == exclusao.getDocumentos().get(0).getId()) {
					alvo.getDocumentos().remove(documento);
					break;
				}
			}
			repositorio.save(alvo); 
			status = HttpStatus.OK;
		}

		return new ResponseEntity<>(status);
	}

	
	
	
	
}
