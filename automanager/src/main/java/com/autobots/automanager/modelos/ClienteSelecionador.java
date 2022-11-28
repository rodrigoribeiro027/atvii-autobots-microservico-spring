package com.autobots.automanager.modelos;

import java.util.List;

import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;

@Component
public class ClienteSelecionador {
	public Cliente selecionar(List<Cliente> clientes, long id) {
		Cliente selecionado = null;
		for (Cliente cliente : clientes) {
			if (cliente.getId() == id) {
				selecionado = cliente;
			}
		}
		return selecionado;
	}
	public List<Telefone> selecionarTelefone(List<Cliente> clientes, long id) {
		List<Telefone> selecionado = null;
		for(Cliente cliente : clientes) {
			if(cliente.getId() == id) {
				selecionado = cliente.getTelefones();
			} 
		}
		return selecionado;
	}
	public List<Documento> selecionarDocumento(List<Cliente> clientes, long id) {
		List<Documento> selecionado = null;
		for(Cliente cliente : clientes) {
			if(cliente.getId() == id) {
				selecionado = cliente.getDocumentos();
			} 
		}
		return selecionado;
	}
	public Endereco selecionarEndereco(List<Cliente> clientes, long id) {
		Endereco selecionado = null;
		for(Cliente cliente : clientes) {
			if(cliente.getId() == id) {
				selecionado = cliente.getEndereco();
			} 
		}
		return selecionado;
	}
	
	
}
