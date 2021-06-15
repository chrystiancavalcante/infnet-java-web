/*******************************************************************************
Title: Trabalho de bloco Infnet                                                                
Description: Service relacionado à tabela [PESSOA] 
                                                                                
The MIT License                                                                 
                                                                                
Copyright: Copyright (C) 2021 chrystiancavalcante@gmail.com                                         
                                                                                
Permission is hereby granted, free of charge, to any person                     
obtaining a copy of this software and associated documentation                  
files (the "Software"), to deal in the Software without                         
restriction, including without limitation the rights to use,                    
copy, modify, merge, publish, distribute, sublicense, and/or sell               
copies of the Software, and to permit persons to whom the                       
Software is furnished to do so, subject to the following                        
conditions:                                                                     
                                                                                
The above copyright notice and this permission notice shall be                  
included in all copies or substantial portions of the Software.                 
                                                                                
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,                 
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES                 
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                        
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                     
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,                    
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING                    
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR                   
OTHER DEALINGS IN THE SOFTWARE.                                                 
                                                                                
       The author may be contacted at:                                          
           chrystiancavalcante@gmail.com                                                   
                                                                                
@author Chrystian Cavalcante (chrystiancavalcante@gmail.com)                    
@version 1.0.0
*******************************************************************************/
package com.infnet.tique.service.cadastros;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infnet.tique.exception.ExcecaoGenericaServidorException;
import com.infnet.tique.model.cadastros.Pessoa;
import com.infnet.tique.model.transiente.Filtro;
import com.infnet.tique.repository.cadastros.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;
	@PersistenceContext
	private EntityManager entityManager;

	public List<Pessoa> consultarLista() {
		return repository.findAll();
	}

	@SuppressWarnings("unchecked")
	public List<Pessoa> consultarLista(Filtro filtro) {
		String sql = "select * from PESSOA where " + filtro.getWhere();
		Query query = entityManager.createNativeQuery(sql, Pessoa.class);
		return query.getResultList();

	}

	public Pessoa consultarObjeto(Integer id) {
		return repository.findById(id).get();
	}

	public Pessoa salvar(Pessoa objeto) {
		Pessoa pessoa = repository.save(objeto);
		return pessoa;
	}

	@Transactional
	public void excluir(Integer id) {
		Pessoa objeto = consultarObjeto(id);
		entityManager.joinTransaction();
		try {
			repository.delete(objeto);
		} catch (Exception e) {
			throw new ExcecaoGenericaServidorException("Erro no Servidor [Excluir Pessoa] - Exceção: " + e.getMessage());
		}
	}

}