import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    private Connection con;
    private PreparedStatement cmd;

    public int inserir(Contato contato) {
        try {
            String sql = "INSERT INTO contato (nome, email, telefone) " + "VALUES (?,?,?)";
            con = Conexao.getInstance().getConnection();
            cmd = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            cmd.setString(1, contato.getNome());
            cmd.setString(2, contato.getEmail());
            cmd.setString(3, contato.getTelefone());

            if (cmd.executeUpdate() > 0) {
                ResultSet rs = cmd.getGeneratedKeys();
                return rs.next() ? rs.getInt(1) : -1;
            }
            return -1;

        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
            return -1;
        } finally {
            // Conexao.getInstance().desconectar();
        }
    }

    public int atualizar(Contato contato) {
        try {
            String sql = "UPDATE contato SET nome=?,email=?,telefone=? " + "WHERE id=?";
            con = Conexao.getInstance().getConnection();
            cmd = con.prepareStatement(sql);
            cmd.setString(1, contato.getNome());
            cmd.setString(2, contato.getEmail());
            cmd.setString(3, contato.getTelefone());
            cmd.setInt(4, contato.getId());

            if (cmd.executeUpdate() > 0) {
                return contato.getId();
            }
            return -1;

        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
            return -1;
        } finally {
            // Conexao.getInstance().desconectar();
        }
    }

    public int deletar(Contato contato) {
        try {
            String sql = "DELETE FROM contato WHERE id=?";
            con = Conexao.getInstance().getConnection();
            cmd = con.prepareStatement(sql);
            cmd.setInt(1, contato.getId());

            if (cmd.executeUpdate() > 0) {
                return contato.getId();
            }
            return -1;

        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
            return -1;
        } finally {
            // Conexao.getInstance().desconectar();
        }
    }

    public List<Contato> listar() {
        try {
            String sql = "SELECT id,nome,telefone,email FROM contato";
            con = Conexao.getInstance().getConnection();
            cmd = con.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery();
            List<Contato> lista = new ArrayList<>();

            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
            return lista;

        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        } finally {
            // Conexao.getInstance().desconectar();
        }
    }

    public Contato pesquisarPorId(int id) {
        try {
            String sql = "SELECT id,nome,telefone,email FROM contato " + "WHERE id=?";
            con = Conexao.getInstance().getConnection();
            cmd = con.prepareStatement(sql);
            cmd.setInt(1, id);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getInt("id"));
                contato.setNome(rs.getString("nome"));
                contato.setTelefone(rs.getString("telefone"));
                contato.setEmail(rs.getString("email"));
                return contato;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        } finally {
            // Conexao.getInstance().desconectar();
        }
    }

    public List<Contato> pesquisarPorNome(String nome) {
        try {
            String sql = "SELECT id,nome,telefone,email FROM contato " + "WHERE nome LIKE ?";
            con = Conexao.getInstance().getConnection();
            cmd = con.prepareStatement(sql);
            cmd.setString(1, "%" + nome + "%");
            ResultSet rs = cmd.executeQuery();
            List<Contato> lista = new ArrayList<>();

            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
            return lista;

        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
            return null;
        } finally {
            // Conexao.getInstance().desconectar();
        }
    }
}