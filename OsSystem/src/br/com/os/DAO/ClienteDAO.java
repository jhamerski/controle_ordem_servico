/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.DAO;

import br.com.os.jdbc.ConnectionFactory;
import br.com.os.model.Cliente;
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
 * @author Jonas Hamerski
 */
public class ClienteDAO {

    private final Connection con;

    //Iniciamos a classe conectando o CONSTRUTOR ao Banco de Dados
    public ClienteDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //Método cadastrar cliente
    public void cadastrarCliente(Cliente obj) {
        try {

            //Criar comando SQL
            String sql = "INSERT INTO tb_clientes(nome, cpf, email, telefone, endereco) VALUES(?,?,?,?,?)";

            //Conectar ao bando de dados.
            PreparedStatement stmt = con.prepareStatement(sql);

            if (obj.getNome().isEmpty() || obj.getCpf().isEmpty() || obj.getEndereco().isEmpty() || obj.getTelefone().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os CAMPOS obrigatórios!");
            } else {
                stmt.setString(1, obj.getNome());
                stmt.setString(2, obj.getCpf());
                stmt.setString(3, obj.getEmail());
                stmt.setString(4, obj.getTelefone());
                stmt.setString(5, obj.getEndereco());

                //Executar o comando SQL
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

    //Método alterar cliente
    public void atualizarCliente(Cliente obj) {
        //Comando SQL
        String sql = "UPDATE tb_clientes SET nome=?, cpf=? , endereco=?, telefone=?, email=? where id=?";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            
            if (obj.getNome().isEmpty() || obj.getCpf().isEmpty() || obj.getEndereco().isEmpty() || obj.getTelefone().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
            } else {
            stmt.setString(1, obj.getNome());
            stmt.setString(2, obj.getCpf());
            stmt.setString(3, obj.getEndereco());
            stmt.setString(4, obj.getTelefone());
            stmt.setString(5, obj.getEmail());
            stmt.setInt(6, obj.getId());

            //Executar o comando sql
            stmt.executeUpdate();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Cliente AUTALIZADO com sucesso!");
            }            
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }

    }

    //Método excluir cliente
    public void excluirCliente(Cliente obj) {
        try {
            //Criar comando SQL
            String sql = "DELETE FROM tb_clientes WHERE cpf=?";

            //Conectar ao bando de dados.
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj.getCpf());

            //Executar o comando SQL
            stmt.executeUpdate();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Cliente EXCLUIDO com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }
    }
    
    public TableModel pesquisarCliente(String obj){
        String sql = "SELECT * FROM tb_clientes WHERE nome like ?";
        try {
            //Conectar ao bando de dados.
            PreparedStatement stmt = con.prepareStatement(sql);
          
            //passando o conteudo da caixa de pesquisa para o ?
            stmt.setString(1, obj + "%");
            ResultSet rs = stmt.executeQuery();
            
            //Armazenando o resultado da "rs" na viriável AUX
            TableModel aux = DbUtils.resultSetToTableModel(rs);
            stmt.close();
            
            return aux;
        } catch (Exception e) {
        }return null;
    }
   
}
