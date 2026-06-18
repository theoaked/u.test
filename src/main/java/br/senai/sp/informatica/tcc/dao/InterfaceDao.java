package br.senai.sp.informatica.tcc.dao;

import java.util.List;

public interface InterfaceDao<T> {	
	public void inserir(T objeto);	
	public void alterar(T objeto);	
	public void excluir(long id);
	public T buscar(long id);
	public List<T> getLista();
}
