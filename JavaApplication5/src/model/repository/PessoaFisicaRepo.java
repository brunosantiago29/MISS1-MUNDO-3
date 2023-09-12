package repository;

import java.util.ArrayList;
import model.PessoaFisica;

public class PessoaFisicaRepo {
    private ArrayList<PessoaFisica> pessoasFisicas = new ArrayList<>();

    public void inserir(PessoaFisica pessoa) {
        pessoasFisicas.add(pessoa);
    }

    public ArrayList<PessoaFisica> obterTodos() {
        return pessoasFisicas;
    }
}
