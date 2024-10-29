import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainGUI {
    private static ContatoDAO contatoDAO = new ContatoDAO();

    public static void main(String[] args) {
        int option = JOptionPane.showOptionDialog(null,
                "Deseja usar a interface gráfica ou o console?",
                "Escolha a Interface",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[] { "Interface Gráfica", "Console" },
                null);

        if (option == JOptionPane.YES_OPTION) {
            SwingUtilities.invokeLater(MainGUI::createAndShowGUI);
        } else {
            Main.main(args); // Chama o método main do console
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Gerenciador de Contatos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JButton btnAdd = new JButton("Adicionar Contato");
        JButton btnList = new JButton("Listar Contatos");
        JButton btnUpdate = new JButton("Atualizar Contato");
        JButton btnDelete = new JButton("Remover Contato");
        JButton btnExit = new JButton("Sair");

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarContato();
            }
        });

        btnList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarContatos();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarContato();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarContato();
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexao.getInstance().desconectar();
                System.exit(0);
            }
        });

        panel.add(btnAdd);
        panel.add(btnList);
        panel.add(btnUpdate);
        panel.add(btnDelete);
        panel.add(btnExit);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void adicionarContato() {
        String nome = JOptionPane.showInputDialog("Nome:");
        String email = JOptionPane.showInputDialog("Email:");
        String telefone = JOptionPane.showInputDialog("Telefone:");

        Contato contato = new Contato();
        contato.setNome(nome);
        contato.setEmail(email);
        contato.setTelefone(telefone);

        contatoDAO.inserir(contato);
        JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso!");
    }

    private static void listarContatos() {
        List<Contato> contatos = contatoDAO.listar();
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Contatos:\n");

        if (contatos != null && !contatos.isEmpty()) {
            for (Contato contato : contatos) {
                sb.append("ID: ").append(contato.getId()).append("\n");
                sb.append("Nome: ").append(contato.getNome()).append("\n");
                sb.append("Email: ").append(contato.getEmail()).append("\n");
                sb.append("Telefone: ").append(contato.getTelefone()).append("\n");
                sb.append("------------------------\n");
            }
        } else {
            sb.append("Nenhum contato encontrado.");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void atualizarContato() {
        String nome = JOptionPane.showInputDialog("Digite o nome do contato que deseja atualizar:");
        List<Contato> contatos = contatoDAO.pesquisarPorNome(nome);
        if (contatos != null && !contatos.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Contatos encontrados:\n");
            for (Contato contato : contatos) {
                sb.append("ID: ").append(contato.getId()).append("\n");
                sb.append("Nome: ").append(contato.getNome()).append("\n");
                sb.append("Email: ").append(contato.getEmail()).append("\n");
                sb.append("Telefone: ").append(contato.getTelefone()).append("\n");
                sb.append("------------------------\n");
            }

            String idString = JOptionPane
                    .showInputDialog(sb.toString() + "Digite o ID do contato que deseja atualizar:");
            int id = Integer.parseInt(idString);
            Contato contato = contatoDAO.pesquisarPorId(id);
            if (contato != null) {
                String novoNome = JOptionPane.showInputDialog("Novo Nome (deixe vazio para manter):");
                if (novoNome != null && !novoNome.isEmpty()) {
                    contato.setNome(novoNome);
                }

                String novoEmail = JOptionPane.showInputDialog("Novo Email (deixe vazio para manter):");
                if (novoEmail != null && !novoEmail.isEmpty()) {
                    contato.setEmail(novoEmail);
                }

                String novoTelefone = JOptionPane.showInputDialog("Novo Telefone (deixe vazio para manter):");
                if (novoTelefone != null && !novoTelefone.isEmpty()) {
                    contato.setTelefone(novoTelefone);
                }

                if (contatoDAO.atualizar(contato) > 0) {
                    JOptionPane.showMessageDialog(null, "Contato atualizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar o contato.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Contato não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum contato encontrado com o nome fornecido.");
        }
    }

    private static void deletarContato() {
        listarContatos();
        String idString = JOptionPane.showInputDialog("Digite o ID do contato que deseja remover:");
        int id = Integer.parseInt(idString);

        Contato contato = contatoDAO.pesquisarPorId(id);
        if (contato != null) {
            if (contatoDAO.deletar(contato) > 0) {
                JOptionPane.showMessageDialog(null, "Contato deletado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao deletar o contato.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Contato não encontrado.");
        }
    }
}
