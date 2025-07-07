package br.ufrpe.dados;

import br.ufrpe.negocio.beans.Funcionario;

import java.util.ArrayList;

public class RepositorioFuncionario {

    private ArrayList<Funcionario> funcionarios;
    private static RepositorioFuncionario instance;

    public static RepositorioFuncionario getInstance(){
        if (instance == null){
            instance = new RepositorioFuncionario();
        }
        return instance;
    }


}
