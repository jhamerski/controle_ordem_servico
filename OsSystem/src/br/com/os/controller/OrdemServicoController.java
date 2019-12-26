/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.controller;

import br.com.os.DAO.OrdemServicoDAO;
import br.com.os.model.OrdemServico;
import br.com.os.view.ViewOrdemServico;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Jonas Hamreski
 */
public class OrdemServicoController {

    private final ViewOrdemServico view;

    public OrdemServicoController(ViewOrdemServico view) {
        this.view = view;
    }

    public void pesquisarOrdemServico() {
        String obj = (view.getTxtCliPesquisar().getText());

        OrdemServicoDAO dao = new OrdemServicoDAO();
        TableModel rs = dao.pesquisarCliente(obj);
        view.getTabelaClientes().setModel(rs);
    }

    public void setarCampos() {
        int setar = view.getTabelaClientes().getSelectedRow();

        view.getTxtCliId().setText(view.getTabelaClientes().getModel().getValueAt(setar, 0).toString());
    }

    public void cadastrarOrdemServico() {
        int confirma = JOptionPane.showConfirmDialog(null, "Os dados digitados estão corretos?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
        OrdemServico obj = new OrdemServico();

        String tipo = view.getjCbTipoServico().getSelectedItem().toString();
        String situacao = view.getCboOsSit().getSelectedItem().toString();

        obj.setId_cliente(Integer.parseInt(view.getTxtCliId().getText()));

        obj.setValor(Float.parseFloat(view.getTxtOsValor().getText().replace(",", ".")));
        obj.setEquipamento(view.getTxtOsEquipamento().getText());
        obj.setDefeito(view.getTxtOsDefeito().getText());
        obj.setServico(view.getTxtOsServicos().getText());
        obj.setTecnico(view.getTxtOsTecnico().getText());

        OrdemServicoDAO dao = new OrdemServicoDAO();
        dao.emitirOrdemServico(obj, situacao, tipo);
        limparCamposOs();
        }
    }

    public void pesquisarOrdemServicos() {
        String numeroOrdemServico = JOptionPane.showInputDialog("Informe o número da Ordem de Serviços: ");

        OrdemServico obj = new OrdemServico();
        obj.setId(Integer.parseInt(numeroOrdemServico));

        OrdemServicoDAO dao = new OrdemServicoDAO();
        dao.pesquisarOrdemServicoo(obj);

        ResultSet rs = dao.pesquisarOrdemServicoo(obj);

        try {
            if (rs.next()) {
                view.getTxtOS().setText(Integer.toString(rs.getInt(10)));
                view.getTxtData().setText(rs.getString(1));

                //view.getjCbTipoServico().setSelectedIndex(rs.getInt(3));
                //view.getCboOsSit().setSelectedIndex(rs.getInt(4));
                view.getTxtOsEquipamento().setText(rs.getString(4));
                view.getTxtOsDefeito().setText(rs.getString(5));
                view.getTxtOsServicos().setText(rs.getString(6));
                view.getTxtOsTecnico().setText(rs.getString(7));
                view.getTxtOsValor().setText(Double.toString(rs.getDouble(8)));
                view.getTxtCliId().setText(Integer.toString(rs.getInt(9)));

                
                 DefaultTableModel modelo = (DefaultTableModel) view.getTabelaClientes().getModel();
                 modelo.setNumRows(0);
                 modelo.addRow(new Object[] {					
                 rs.getString(11),
                 rs.getString(12),
                 rs.getString(13)
                 });
                 
                view.getTxtCliPesquisar().setEnabled(false);
                view.getBtnOsAdicionar().setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Ordem de Serviço não existe! Favor digitar uma OS válida!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void atualizarOrdemServicos() throws UnsupportedEncodingException, ParseException{
       
        int confirma = JOptionPane.showConfirmDialog(null, "Você irá EDITAR OS selecionada!", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION){
        
        OrdemServico obj = new OrdemServico();      
        //obj.setData(view.getTxtData().getText());
        obj.setId(Integer.parseInt(view.getTxtOS().getText()));
        obj.setValor(Float.parseFloat(view.getTxtOsValor().getText().replace(",", ".")));    
        obj.setEquipamento(view.getTxtOsEquipamento().getText());
        obj.setDefeito(view.getTxtOsDefeito().getText());
        obj.setServico(view.getTxtOsServicos().getText());
        obj.setTecnico(view.getTxtOsTecnico().getText());
        
        String situacao = view.getCboOsSit().getSelectedItem().toString();
        String tipo = view.getjCbTipoServico().getSelectedItem().toString();
        
        OrdemServicoDAO dao = new OrdemServicoDAO();
        dao.atualizarOrdemServico(obj);
        limparCamposOs();
        }
    }

    public void excluirOrdemServico() {
        int confirma = JOptionPane.showConfirmDialog(null, "Você irá EXCLUIR a OS selecionada!", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            OrdemServico obj = new OrdemServico();
            obj.setId(Integer.parseInt(view.getTxtOS().getText()));

            OrdemServicoDAO dao = new OrdemServicoDAO();
            dao.excluirOrdemServico(obj);
            limparCamposOs();
        }
    }

    private void limparCamposOs() {
        view.getTxtOS().setText((String) "");
        view.getTxtData().setText((String) "");
        view.getTxtOsEquipamento().setText((String) "");
        view.getTxtOsDefeito().setText((String) "");
        view.getTxtOsServicos().setText((String) "");
        view.getTxtOsTecnico().setText((String) "");
        view.getTxtOsValor().setText((String) "0");
        view.getTxtCliId().setText((String) "");
        view.getBtnOsAdicionar().setEnabled(true);
        view.getTxtCliPesquisar().setEnabled(true);
    }

    

}
