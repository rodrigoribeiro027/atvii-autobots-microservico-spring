package com.autobots.automanager.modelos;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.entidades.Documento;

@Component
public class AdicionadorLinkDocumento implements AdicionadorLink<Documento> {
	@Override
	public void adicionarLink(List<Documento> lista) {
		for(Documento documento:lista) {
			long id = documento.getId();
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(DocumentoControle.class)
							.obterDocumentoID(id))
					.withSelfRel();
			documento.add(linkProprio);
		}
	}

	@Override
	public void adicionarLink(Documento objeto) {
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentoControle.class)
						.obterDocumentos())
				.withRel("Lista de Documentos");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkCriar(Documento objeto) {
		long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentoControle.class)
						.obterDocumentoID(id))
				.withSelfRel();
		objeto.add(linkProprio);
		
	}

	@Override
	public void adicionarLinkUpdate(Documento objeto) {
		
		
	}

	@Override
	public void adicionarLinkDelete(Documento objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(DocumentoControle.class)
						.excluirDocumento(id))
				.withSelfRel();
		objeto.add(linkProprio);
		
	}
}
