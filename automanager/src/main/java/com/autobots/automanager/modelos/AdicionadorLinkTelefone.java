package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.DocumentoControle;
import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {
	
	@Override
	public void adicionarLink(List<Telefone> lista) {
		for(Telefone telefone: lista) {
			long id = telefone.getId();
			Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
				.methodOn(TelefoneControle.class)
				.buscarTelefoneID(id))
				.withSelfRel();
			telefone.add(linkProprio);
		}
		
	}

	@Override
	public void adicionarLink(Telefone objeto) {
		Link linkProprio = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder
			.methodOn(TelefoneControle.class)
			.buscarTelefones())
			.withRel("Lista de telefones");
		objeto.add(linkProprio);
	}

	@Override
	public void adicionarLinkCriar(Telefone objeto) {
		long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
			.linkTo(WebMvcLinkBuilder
			.methodOn(TelefoneControle.class)
			.buscarTelefoneID(id))
			.withSelfRel();
		objeto.add(linkProprio);
		
	}

	@Override
	public void adicionarLinkUpdate(Telefone objeto) {		
			Link linkProprio = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(TelefoneControle.class)
							.editarTelefonePorId(objeto))
					.withRel("clientes");
			objeto.add(linkProprio);
		
	}

	@Override
	public void adicionarLinkDelete(Telefone objeto) {
		Long id = objeto.getId();
		Link linkProprio = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(TelefoneControle.class)
						.excluirTelefone(id))
				.withSelfRel();
		objeto.add(linkProprio);
	}
}
