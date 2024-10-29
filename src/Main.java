import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ContatoDAO contatoDAO = new ContatoDAO();

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\nMenu de Contatos:");
            System.out.println("1. Adicionar Contato");
            System.out.println("2. Listar Contatos");
            System.out.println("3. Atualizar Contato");
            System.out.println("4. Remover Contato");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    adicionarContato();
                    break;
                case 2:
                    listarContatos();
                    break;
                case 3:
                    atualizarContato();
                    break;
                case 4:
                    deletarContato();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    Conexao.getInstance().desconectar();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 5);

        scanner.close();
    }

    private static void adicionarContato() {
        Contato contato = new Contato();

        System.out.println("\nEntre com os dados do novo Contato");
        System.out.print("Nome: ");
        contato.setNome(scanner.nextLine());
        System.out.print("Email: ");
        contato.setEmail(scanner.nextLine());
        System.out.print("Telefone: ");
        contato.setTelefone(scanner.nextLine());

        contatoDAO.inserir(contato);
        System.out.println("Contato adicionado com sucesso!");
    }

    private static void listarContatos() {
        System.out.println("\nLista de Contatos:");
        List<Contato> contatos = contatoDAO.listar();

        if (contatos != null && !contatos.isEmpty()) {
            for (Contato contato : contatos) {
                System.out.println("ID: " + contato.getId());
                System.out.println("Nome: " + contato.getNome());
                System.out.println("Email: " + contato.getEmail());
                System.out.println("Telefone: " + contato.getTelefone());
                System.out.println("------------------------");
            }
        } else {
            System.out.println("Nenhum contato encontrado.");
        }
    }

    private static void atualizarContato() {
        System.out.print("Digite o nome do contato que deseja atualizar: ");
        String nome = scanner.nextLine();

        List<Contato> contatos = contatoDAO.pesquisarPorNome(nome);
        if (contatos != null && !contatos.isEmpty()) {
            System.out.println("Contatos encontrados:");
            for (Contato contato : contatos) {
                System.out.println("ID: " + contato.getId());
                System.out.println("Nome: " + contato.getNome());
                System.out.println("Email: " + contato.getEmail());
                System.out.println("Telefone: " + contato.getTelefone());
                System.out.println("------------------------");
            }

            System.out.print("Digite o ID do contato que deseja atualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Contato contato = contatoDAO.pesquisarPorId(id);
            if (contato != null) {
                System.out.print("Novo Nome (deixe vazio para manter): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isEmpty()) {
                    contato.setNome(novoNome);
                }

                System.out.print("Novo Email (deixe vazio para manter): ");
                String novoEmail = scanner.nextLine();
                if (!novoEmail.isEmpty()) {
                    contato.setEmail(novoEmail);
                }

                System.out.print("Novo Telefone (deixe vazio para manter): ");
                String novoTelefone = scanner.nextLine();
                if (!novoTelefone.isEmpty()) {
                    contato.setTelefone(novoTelefone);
                }

                if (contatoDAO.atualizar(contato) > 0) {
                    System.out.println("Contato atualizado com sucesso!");
                } else {
                    System.out.println("Erro ao atualizar o contato.");
                }
            } else {
                System.out.println("Contato não encontrado.");
            }
        } else {
            System.out.println("Nenhum contato encontrado com o nome fornecido.");
        }
    }

    private static void deletarContato() {
        listarContatos();
        System.out.print("Digite o ID do contato que deseja remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Contato contato = contatoDAO.pesquisarPorId(id);
        if (contato != null) {
            if (contatoDAO.deletar(contato) > 0) {
                System.out.println("Contato deletado com sucesso!");
            } else {
                System.out.println("Falha ao deletar o contato.");
            }
        } else {
            System.out.println("Contato não encontrado.");
        }
    }

}
