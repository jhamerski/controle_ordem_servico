package br.com.os.DAO;

import br.com.os.jdbc.ConnectionFactory;
import br.com.os.model.Usuario;
import br.com.os.view.ViewPrincipal;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Jonas Hmerski
 */
public class UsuarioDAO {

    private final Connection con;

    //Iniciamos a classe conectando o CONSTRUTOR ao Banco de Dados
    public UsuarioDAO() {
        con = new ConnectionFactory().getConnection();
    }

    //Método efetuar login
    public void efetuaLogin(String login, String senha) {
        try {
            //Comando sql
            String sql = "SELECT * FROM tb_funcionarios WHERE login=? AND senha=?";

            PreparedStatement stmt = con.prepareStatement(sql);

            if (login.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos para poder entrar no sistema!");
            } else {
                stmt.setString(1, login);
                stmt.setString(2, senha);
            }

            //Sempre que criar um select, precisa-se usar a classe RESULTSET
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String perfil = rs.getString(9);
                if (perfil.equals("Administrador")) {
                    ViewPrincipal principal = new ViewPrincipal();
                    principal.setVisible(true);
                    ViewPrincipal.MenRel.setEnabled(true);
                    ViewPrincipal.MenCadUsu.setEnabled(true);
                    ViewPrincipal.lblUsuario.setText(rs.getString(2));
                    ViewPrincipal.lblUsuario.setForeground(Color.red);
                } else {
                    ViewPrincipal principal = new ViewPrincipal();
                    ViewPrincipal.lblUsuario.setText(rs.getString(2));
                    principal.setVisible(true);
                }

                //JOptionPane.showMessageDialog(null, "Seja bem vindo ao sistema!");
            } else {
                JOptionPane.showMessageDialog(null, "Dados Incorretos");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }
    }

    public void atualizarUsuario(Usuario obj, String perfil) {
        try {
            String sql = "INSERT INTO tb_funcionarios (nome, cpf, email, telefone, endereco, login, senha, perfil) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            if (obj.getNome().isEmpty() || obj.getCpf().isEmpty() || obj.getTelefone().isEmpty() || obj.getEndereco().isEmpty() || obj.getLogin().isEmpty() || obj.getSenha().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os CAMPOS obrigattórios");
            } else {
                //Criptografando a senha

                stmt.setString(1, obj.getNome());
                stmt.setString(2, obj.getCpf());
                stmt.setString(3, obj.getEmail());
                stmt.setString(4, obj.getTelefone());
                stmt.setString(5, obj.getEndereco());
                stmt.setString(6, obj.getLogin());
                stmt.setString(7, obj.getSenha());

                //Passando o perfil por um parametro separado.
                stmt.setString(8, perfil);
                
                int adicionado = stmt.executeUpdate();
                
                if(adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
                }
                stmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }
    }

    public void alterarUsuario(Usuario obj, String perfil) {
        try {
            String sql = "UPDATE tb_funcionarios SET nome=?, cpf=?, email=?, telefone=?, endereco=?, login=?, senha=?, perfil=? WHERE id=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpf());
            stmt.setString(3, obj.getEmail());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getEndereco());
            stmt.setString(6, obj.getLogin());
            stmt.setString(7, obj.getSenha());
            
            //Atualizando o perfil do usuário
            stmt.setString(8, perfil);

            //Passando o perfil por um parametro separado.
            stmt.setInt(9, obj.getId());

            stmt.executeUpdate();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Funcionário AUTALIZADO com sucesso!");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }
    }

    public void excluirUsuario(Usuario obj) {
        try {
            //Criar comando SQL
            String sql = "DELETE FROM tb_funcionarios WHERE cpf=?";

            //Conectar ao bando de dados.
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getCpf());

            //Executar o comando SQL
            stmt.executeUpdate();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Funcionário EXCLUIDO com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }
    }

    public TableModel pesquisarUsuario(String obj) {  
        try {
            //Criar comando SQL
            String sql = "SELECT * FROM tb_funcionarios WHERE nome like ?";
            //Conectar ao bando de dados
            PreparedStatement stmt = con.prepareStatement(sql);

            //passando o conteudo da caixa de pesquisa para o ?
            stmt.setString(1, obj + "%");
            ResultSet rs = stmt.executeQuery();

            //Armazenando o resultado da "rs" na viriável AUX
            TableModel aux = DbUtils.resultSetToTableModel(rs);
            stmt.close();

            return aux;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }
        return null;
    }

}
