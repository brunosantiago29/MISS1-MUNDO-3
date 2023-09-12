import java.io.*;
import java.util.*;

interface Persistencia {
    void persistir(String nomeArquivo) throws IOException;
    void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException;
}

class Pessoa implements Persistencia, Serializable {
    int id;
    String nome;

    public Pessoa() {
    }

    public Pessoa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void persistir(String nomeArquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(this);
        }
    }

    @Override
    public void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Pessoa pessoa = (Pessoa) in.readObject();
            this.id = pessoa.id;
            this.nome = pessoa.nome;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + "\nNome: " + nome;
    }
}

class PessoaFisica extends Pessoa {
    private String cpf;
    private int idade;

    public PessoaFisica() {
    }

    public PessoaFisica(int id, String nome, String cpf, int idade) {
        super(id, nome);
        this.cpf = cpf;
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCPF: " + cpf + "\nIdade: " + idade;
    }

    @Override
    public void persistir(String nomeArquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(this);
        }
    }

    @Override
    public void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            PessoaFisica pessoaFisica = (PessoaFisica) in.readObject();
            this.id = pessoaFisica.getId();
            this.nome = pessoaFisica.nome;
            this.cpf = pessoaFisica.cpf;
            this.idade = pessoaFisica.idade;
        }
    }
}

class PessoaJuridica extends Pessoa {
    private String cnpj;

    public PessoaJuridica() {
    }

    public PessoaJuridica(int id, String nome, String cnpj) {
        super(id, nome);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCNPJ: " + cnpj;
    }

    @Override
    public void persistir(String nomeArquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(this);
        }
    }

    @Override
    public void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            PessoaJuridica pessoaJuridica = (PessoaJuridica) in.readObject();
            this.id = pessoaJuridica.getId();
            this.nome = pessoaJuridica.nome;
            this.cnpj = pessoaJuridica.cnpj;
        }
    }
}

class PessoaFisicaRepo {
    private final ArrayList<PessoaFisica> pessoasFisicas = new ArrayList<>();
    private int nextId = 1;

    public boolean inserir(PessoaFisica pessoa) {
        if (existeId(pessoa.getId())) {
            System.out.println("ID já existe! Digite um ID diferente.");
            return false;
        }

        pessoasFisicas.add(pessoa);
        nextId = Math.max(nextId, pessoa.getId() + 1);
        return true;
    }

    public void alterar(PessoaFisica pessoa) {
        for (int i = 0; i < pessoasFisicas.size(); i++) {
            if (pessoasFisicas.get(i).getId() == pessoa.getId()) {
                pessoasFisicas.set(i, pessoa);
                break;
            }
        }
    }

    public boolean excluir(int id) {
        return pessoasFisicas.removeIf(pessoa -> pessoa.getId() == id);
    }

    public PessoaFisica obter(int id) {
        for (PessoaFisica pessoa : pessoasFisicas) {
            if (pessoa.getId() == id) {
                return pessoa;
            }
        }
        return null;
    }

    public ArrayList<PessoaFisica> obterTodos() {
        return pessoasFisicas;
    }

    public void persistir(String nomeArquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(pessoasFisicas);
        }
    }

    public void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            ArrayList<PessoaFisica> lista = (ArrayList<PessoaFisica>) in.readObject();
            pessoasFisicas.clear();
            pessoasFisicas.addAll(lista);
            nextId = pessoasFisicas.isEmpty() ? 1 : pessoasFisicas.get(pessoasFisicas.size() - 1).getId() + 1;
        }
    }

    public boolean existeId(int id) {
        for (PessoaFisica pessoa : pessoasFisicas) {
            if (pessoa.getId() == id) {
                return true;
            }
        }
        return false;
    }
}

class PessoaJuridicaRepo {
    private final ArrayList<PessoaJuridica> pessoasJuridicas = new ArrayList<>();
    private int nextId = 1;

    public boolean inserir(PessoaJuridica pessoa) {
        if (existeId(pessoa.getId())) {
            System.out.println("ID já existe! Digite um ID diferente.");
            return false;
        }

        pessoasJuridicas.add(pessoa);
        nextId = Math.max(nextId, pessoa.getId() + 1);
        return true;
    }

    public void alterar(PessoaJuridica pessoa) {
        for (int i = 0; i < pessoasJuridicas.size(); i++) {
            if (pessoasJuridicas.get(i).getId() == pessoa.getId()) {
                pessoasJuridicas.set(i, pessoa);
                break;
            }
        }
    }

    public boolean excluir(int id) {
        return pessoasJuridicas.removeIf(pessoa -> pessoa.getId() == id);
    }

    public PessoaJuridica obter(int id) {
        for (PessoaJuridica pessoa : pessoasJuridicas) {
            if (pessoa.getId() == id) {
                return pessoa;
            }
        }
        return null;
    }

    public ArrayList<PessoaJuridica> obterTodos() {
        return pessoasJuridicas;
    }

    public void persistir(String nomeArquivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(pessoasJuridicas);
        }
    }

    public void recuperar(String nomeArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            ArrayList<PessoaJuridica> lista = (ArrayList<PessoaJuridica>) in.readObject();
            pessoasJuridicas.clear();
            pessoasJuridicas.addAll(lista);
            nextId = pessoasJuridicas.isEmpty() ? 1 : pessoasJuridicas.get(pessoasJuridicas.size() - 1).getId() + 1;
        }
    }

    public boolean existeId(int id) {
        for (PessoaJuridica pessoa : pessoasJuridicas) {
            if (pessoa.getId() == id) {
                return true;
            }
        }
        return false;
    }
}

public class CadastroPOO {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        try (Scanner scanner = new Scanner(System.in).useDelimiter("\n")) {
            PessoaFisicaRepo repoPF = new PessoaFisicaRepo();
            PessoaJuridicaRepo repoPJ = new PessoaJuridicaRepo();

            int opcao;
            do {
                System.out.println("Menu:");
                System.out.println("1 - Incluir");
                System.out.println("2 - Alterar");
                System.out.println("3 - Excluir");
                System.out.println("4 - Exibir pelo ID");
                System.out.println("5 - Exibir todos");
                System.out.println("6 - Salvar dados");
                System.out.println("7 - Recuperar dados");
                System.out.println("0 - Sair");
                System.out.print("Escolha a opção: ");

                try {
                    opcao = scanner.nextInt();
                    scanner.nextLine(); // Limpar o buffer

                    switch (opcao) {
                        case 1 -> {
                            System.out.print("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica): ");
                            String tipo = scanner.nextLine().toUpperCase();
                            if (tipo.equals("F")) {
                                PessoaFisica pf = lerDadosPessoaFisica(scanner, repoPF);
                                if (pf != null) {
                                    if (repoPF.inserir(pf)) {
                                        System.out.println("Pessoa Física adicionada com sucesso!");
                                    }
                                }
                            } else if (tipo.equals("J")) {
                                PessoaJuridica pj = lerDadosPessoaJuridica(scanner, repoPJ);
                                if (pj != null) {
                                    if (repoPJ.inserir(pj)) {
                                        System.out.println("Pessoa Jurídica adicionada com sucesso!");
                                    }
                                }
                            } else {
                                System.out.println("Opção inválida!");
                            }
                        }
                        case 2 -> {
                            System.out.print("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica): ");
                            String tipo = scanner.nextLine().toUpperCase();
                            if (tipo.equals("F")) {
                                PessoaFisica pfAlterar = lerDadosPessoaFisica(scanner, repoPF);
                                if (pfAlterar != null) {
                                    System.out.print("Digite o ID da Pessoa Física a ser alterada: ");
                                    int idPessoaFisicaAlterar = scanner.nextInt();
                                    pfAlterar.setId(idPessoaFisicaAlterar);
                                    repoPF.alterar(pfAlterar);
                                    System.out.println("Pessoa Física alterada com sucesso!");
                                }
                            } else if (tipo.equals("J")) {
                                PessoaJuridica pjAlterar = lerDadosPessoaJuridica(scanner, repoPJ);
                                if (pjAlterar != null) {
                                    System.out.print("Digite o ID da Pessoa Jurídica a ser alterada: ");
                                    int idPessoaJuridicaAlterar = scanner.nextInt();
                                    pjAlterar.setId(idPessoaJuridicaAlterar);
                                    repoPJ.alterar(pjAlterar);
                                    System.out.println("Pessoa Jurídica alterada com sucesso!");
                                }
                            } else {
                                System.out.println("Opção inválida!");
                            }
                        }
                        case 3 -> {
                            System.out.print("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica): ");
                            String tipo = scanner.nextLine().toUpperCase();
                            if (tipo.equals("F")) {
                                System.out.print("Digite o ID da Pessoa Física a ser excluída: ");
                                int idPessoaFisicaExcluir = scanner.nextInt();
                                if (repoPF.excluir(idPessoaFisicaExcluir)) {
                                    System.out.println("Pessoa Física excluída com sucesso!");
                                } else {
                                    System.out.println("Pessoa Física não encontrada.");
                                }
                            } else if (tipo.equals("J")) {
                                System.out.print("Digite o ID da Pessoa Jurídica a ser excluída: ");
                                int idPessoaJuridicaExcluir = scanner.nextInt();
                                if (repoPJ.excluir(idPessoaJuridicaExcluir)) {
                                    System.out.println("Pessoa Jurídica excluída com sucesso!");
                                } else {
                                    System.out.println("Pessoa Jurídica não encontrada.");
                                }
                            } else {
                                System.out.println("Opção inválida!");
                            }
                        }
                        case 4 -> {
                            System.out.print("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica): ");
                            String tipo = scanner.nextLine().toUpperCase();
                            if (tipo.equals("F")) {
                                System.out.print("Digite o ID da Pessoa Física a ser exibida: ");
                                int idPessoaFisicaExibir = scanner.nextInt();
                                PessoaFisica pessoaFisica = repoPF.obter(idPessoaFisicaExibir);
                                if (pessoaFisica != null) {
                                    System.out.println(pessoaFisica);
                                } else {
                                    System.out.println("Pessoa Física não encontrada.");
                                }
                            } else if (tipo.equals("J")) {
                                System.out.print("Digite o ID da Pessoa Jurídica a ser exibida: ");
                                int idPessoaJuridicaExibir = scanner.nextInt();
                                PessoaJuridica pessoaJuridica = repoPJ.obter(idPessoaJuridicaExibir);
                                if (pessoaJuridica != null) {
                                    System.out.println(pessoaJuridica);
                                } else {
                                    System.out.println("Pessoa Jurídica não encontrada.");
                                }
                            } else {
                                System.out.println("Opção inválida!");
                            }
                        }
                        case 5 -> {
                            System.out.print("Escolha o tipo (F - Pessoa Física, J - Pessoa Jurídica): ");
                            String tipo = scanner.nextLine().toUpperCase();
                            if (tipo.equals("F")) {
                                listarPessoasFisicas(repoPF);
                            } else if (tipo.equals("J")) {
                                listarPessoasJuridicas(repoPJ);
                            } else {
                                System.out.println("Opção inválida!");
                            }
                        }
                        case 6 -> {
                            repoPF.persistir("pessoasFisicas.dat");
                            repoPJ.persistir("pessoasJuridicas.dat");
                            System.out.println("Dados salvos com sucesso!");
                        }
                        case 7 -> {
                            repoPF.recuperar("pessoasFisicas.dat");
                            repoPJ.recuperar("pessoasJuridicas.dat");
                            System.out.println("Dados recuperados com sucesso!");
                        }
                        case 0 -> System.out.println("Saindo...");
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida! Digite um número.");
                    scanner.nextLine(); // Limpar o buffer
                    opcao = -1; // Para continuar o loop
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Erro ao salvar/recuperar dados: " + e.getMessage());
                    opcao = -1; // Para continuar o loop
                }
            } while (opcao != 0);
        }
    }

    private static PessoaFisica lerDadosPessoaFisica(Scanner scanner, PessoaFisicaRepo repo) {
        System.out.print("Digite o ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (repo.existeId(id)) {
            System.out.println("ID já existe! Digite um ID diferente.");
            return null;
        }

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite a idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        return new PessoaFisica(id, nome, cpf, idade);
    }

    private static PessoaJuridica lerDadosPessoaJuridica(Scanner scanner, PessoaJuridicaRepo repo) {
        System.out.print("Digite o ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer

        if (repo.existeId(id)) {
            System.out.println("ID já existe! Digite um ID diferente.");
            return null;
        }

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o CNPJ: ");
        String cnpj = scanner.nextLine();

        return new PessoaJuridica(id, nome, cnpj);
    }

    private static void listarPessoasFisicas(PessoaFisicaRepo repo) {
        ArrayList<PessoaFisica> pessoasFisicas = repo.obterTodos();
        if (pessoasFisicas.isEmpty()) {
            System.out.println("Nenhuma Pessoa Física cadastrada.");
        } else {
            for (PessoaFisica pessoa : pessoasFisicas) {
                System.out.println(pessoa);
                System.out.println();
            }
        }
    }

    private static void listarPessoasJuridicas(PessoaJuridicaRepo repo) {
        ArrayList<PessoaJuridica> pessoasJuridicas = repo.obterTodos();
        if (pessoasJuridicas.isEmpty()) {
            System.out.println("Nenhuma Pessoa Jurídica cadastrada.");
        } else {
            for (PessoaJuridica pessoa : pessoasJuridicas) {
                System.out.println(pessoa);
                System.out.println();
            }
        }
    }
}
