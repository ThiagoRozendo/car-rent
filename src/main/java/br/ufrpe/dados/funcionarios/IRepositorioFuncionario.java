package br.ufrpe.dados.funcionarios;

import br.ufrpe.negocio.beans.Funionarios.Funcionario;
import br.ufrpe.negocio.exceptions.AdministradorNaoEncontradoException;
import br.ufrpe.negocio.exceptions.AtendenteNaoEncontradoException;
import br.ufrpe.negocio.exceptions.DadosInvalidosException;

public interface IRepositorioFuncionario {
    public Funcionario fazerLogin(String email, String senha) throws AtendenteNaoEncontradoException, DadosInvalidosException, AdministradorNaoEncontradoException;
}
