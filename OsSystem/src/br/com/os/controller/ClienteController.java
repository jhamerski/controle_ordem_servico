/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.controller;

import br.com.os.DAO.ClienteDAO;
import javax.swing.table.TableModel;
import br.com.os.model.Cliente;
import br.com.os.view.TelaCliente;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonas Hamerski
 */
public class ClienteController {

    private final TelaCliente view;

    public ClienteController(TelaCliente view) {
        this.view = view;
    }

    public void cadastrarCliente() {
        int confirma = JOptionPane.showConfirmDialog(null, "Os dados digitados estão corretos?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Cliente obj = new Cliente();
            obj.setNome(view.getTxtCliNome().getText());
            obj.setCpf(view.getTxtCliCpf().getText());
            obj.setEndereco(view.getTxtCliEndereco().getText());
            obj.setTelefone(view.getTxtCliTelefone().getText());
            obj.setEmail(view.getTxtCliEmail().getText());

            ClienteDAO dao = new ClienteDAO();
            dao.cadastrarCliente(obj);
            limparCamposCliente();
        }
    }

    public void atualizarCliente() {
        int confirma = JOptionPane.showConfirmDialog(null, "Você atualizou os dados do cliente!", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Cliente obj = new Cliente();
            obj.setNome(view.getTxtCliNome().getText());
            obj.setCpf(view.getTxtCliCpf().getText());
            obj.setEndereco(view.getTxtCliEndereco().getText());
            obj.setTelefone(view.getTxtCliTelefone().getText());
            obj.setEmail(view.getTxtCliEmail().getText());

            obj.setId(Integer.parseInt(view.getTxtCliId().getText()));

            ClienteDAO dao = new ClienteDAO();
            dao.atualizarCliente(obj);
            limparCamposCliente();
        }
    }

    public void excluirCliente() {
        int confirma = JOptionPane.showConfirmDialog(null, "Você irá EXCLUIR o cliente selecionado!", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Cliente obj = new Cliente();
            obj.setCpf(view.getTxtCliCpf().getText());

            ClienteDAO dao = new ClienteDAO();
            dao.excluirCliente(obj);
            limparCamposCliente();
        }
    }

    public void setarCampos() {
        //Desativando o botão cadastrar. OBS: Para não gerar registros duplicados
        view.getBtnAdicionar().setEnabled(false);

        int setar = view.getTabelaClientes().getSelectedRow();

        view.getTxtCliId().setText(view.getTabelaClientes().getModel().getValueAt(setar, 0).toString());
        view.getTxtCliNome().setText((String) view.getTabelaClientes().getModel().getValueAt(setar, 1));
        view.getTxtCliCpf().setText((String) view.getTabelaClientes().getModel().getValueAt(setar, 2));
        view.getTxtCliEmail().setText((String) view.getTabelaClientes().getModel().getValueAt(setar, 3));
        view.getTxtCliTelefone().setText((String) view.getTabelaClientes().getModel().getValueAt(setar, 4));
        view.getTxtCliEndereco().setText((String) view.getTabelaClientes().getModel().getValueAt(setar, 5));
    }

    public void pesquisarCliente() {
        String obj = (view.getTxtPesquisarCliente().getText());

        //Ativando o botão cadastrar
        view.getBtnAdicionar().setEnabled(true);

        ClienteDAO dao = new ClienteDAO();
        TableModel rs = dao.pesquisarCliente(obj);
        view.getTabelaClientes().setModel(rs);
    }

    public void limparCamposCliente() {
        view.getTxtCliNome().setText((String) "");
        view.getTxtCliCpf().setText((String) "");
        view.getTxtCliId().setText((String) "");
        view.getTxtCliEndereco().setText((String) "");
        view.getTxtCliTelefone().setText((String) "");
        view.getTxtCliEmail().setText((String) "");
    }
}
