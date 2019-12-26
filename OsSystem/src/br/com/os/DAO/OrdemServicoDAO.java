/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.DAO;

import br.com.os.jdbc.ConnectionFactory;
import br.com.os.model.OrdemServico;
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
public class OrdemServicoDAO {

    private final Connection con;

    public OrdemServicoDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    public TableModel pesquisarCliente(String obj) {
        String sql = "SELECT id as ID, nome as Nome, cpf as Cpf FROM tb_clientes WHERE nome like ?";
        try {
            //Conectar ao bando de dados.
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, obj + "%");
            ResultSet rs = stmt.executeQuery();

            TableModel aux = DbUtils.resultSetToTableModel(rs);

            stmt.close();
            return aux;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
        return null;
    }

    public void emitirOrdemServico(OrdemServico obj, String situacao, String tipo) {
        try {
            String sql = "INSERT INTO tb_ordem_servicos(tipo, situacao, equipamento, defeito, servico, tecnico, valor, id_cliente) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            if (obj.getEquipamento().isEmpty() || obj.getDefeito().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios!");
            } else {
                //Passando o perfil por um parametro separado.
                stmt.setString(1, tipo);
                //Passando o perfil por um parametro separado.
                stmt.setString(2, situacao);

                stmt.setString(3, obj.getEquipamento());
                stmt.setString(4, obj.getDefeito());
                stmt.setString(5, obj.getServico());
                stmt.setString(6, obj.getTecnico());
                stmt.setFloat(7, obj.getValor());
                stmt.setInt(8, obj.getId_cliente());

                stmt.executeUpdate();
                stmt.close();

                JOptionPane.showMessageDialog(null, "OS gerada com sucesso!");
            }
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            System.out.println(e);
        }
    }

    public ResultSet pesquisarOrdemServicoo(OrdemServico obj) {
        try {
            //String sql = "SELECT * FROM tb_ordem_servicos WHERE id=?";
            String sql = "SELECT o.data_os, o.tipo, o.situacao, o.equipamento, o.defeito, o.servico, o.tecnico, o.valor, o.id_cliente, o.id, c.id, c.nome, c.cpf FROM tb_ordem_servicos o, tb_clientes c WHERE o.id_cliente = c.id AND o.id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getId());

            ResultSet rs = stmt.executeQuery();

            //stmt.close();
            return rs;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
        return null;
    }

    public void excluirOrdemServico(OrdemServico obj) {
        try {
            //Criar comando SQL
            String sql = "DELETE FROM tb_ordem_servicos WHERE id=?";

            //Conectar ao bando de dados.
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getId());

            //Executar o comando SQL
            stmt.executeUpdate();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Ordem de Serviço EXCLUIDA com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro " + e);
        }
    }

    public void atualizarOrdemServico(OrdemServico obj) {
        try {
             String sql = "UPDATE tb_ordem_servicos SET equipamento=?, defeito=?, servico=?, tecnico=?, valor=? WHERE id=?";
             PreparedStatement stmt = con.prepareStatement(sql);
            //tipo=?, situacao=?, 
            //stmt.setString(1, tipo);
            //stmt.setString(2, situacao);
            stmt.setString(1, obj.getEquipamento());
            stmt.setString(2, obj.getDefeito());
            stmt.setString(3, obj.getServico());
            stmt.setString(4, obj.getTecnico());
            stmt.setDouble(5, obj.getValor());
            stmt.setInt(6, obj.getId());
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "OS atualizada com sucesso!");
        } catch (SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            System.out.println(e);
        }
    }

   

}
