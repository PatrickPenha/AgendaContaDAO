import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Conexao instancia; // Instância única de Conexao (Singleton)
    private Connection conexao;
    private String connectionString = "jdbc:postgresql://localhost:5432/ContatoDAO";
    private String usuario = "postgres";
    private String senha = "123";

    // Construtor privado para evitar múltiplas instâncias
    private Conexao() {
        try {
            Class.forName("org.postgresql.Driver");
            conexao = DriverManager.getConnection(connectionString, usuario, senha);
        } catch (Exception ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
        }
    }

    // Método para obter a instância única da conexão
    public static Conexao getInstance() {
        if (instancia == null) {
            instancia = new Conexao();
        }
        return instancia;
    }

    public Connection getConnection() {
        return conexao;
    }

    // Método para fechar a conexão
    public void desconectar() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao desconectar: " + ex.getMessage());
        }
    }
}
