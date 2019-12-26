/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.controller;

import br.com.os.DAO.UsuarioDAO;
import javax.swing.table.TableModel;
import br.com.os.model.Usuario;
import br.com.os.view.ViewUsuario;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonas Hamerski
 */
public class UsuarioController {

    private final ViewUsuario view;

    public UsuarioController(ViewUsuario view) {
        this.view = view;
    }

    public void cadastrarUsuario() {
        int confirma = JOptionPane.showConfirmDialog(null, "Os dados digitados estão corretos?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Usuario obj = new Usuario();
            obj.setNome(view.getTxtUsuNome().getText());
            obj.setCpf(view.getTxtUsuCpf().getText());
            obj.setEmail(view.getTxtUsuEmail().getText());
            obj.setTelefone(view.getTxtUsuTelefone().getText());
            obj.setEndereco(view.getTxtUsuEndereco().getText());
            obj.setLogin(view.getTxtUsuLogin().getText());
            obj.setSenha(view.getTxtUsuSenha().getText());
            
            //passando perfil por um parametro separado
            String perfil = view.getJcbUsuPerfil().getSelectedItem().toString();

            UsuarioDAO dao = new UsuarioDAO();
            dao.atualizarUsuario(obj, perfil);
            limparCamposUsuario();
        }
    }

    public void atualizarUsuario() {
        int confirma = JOptionPane.showConfirmDialog(null, "Você atualizou os dados do funcionário!", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Usuario obj = new Usuario();
            obj.setNome(view.getTxtUsuNome().getText());
            obj.setCpf(view.getTxtUsuCpf().getText());
            obj.setEmail(view.getTxtUsuEmail().getText());
            obj.setTelefone(view.getTxtUsuTelefone().getText());
            obj.setEndereco(view.getTxtUsuEndereco().getText());
            obj.setLogin(view.getTxtUsuLogin().getText());
            obj.setSenha(view.getTxtUsuSenha().getText());
            
            //passando perfil por parâmetro separado
            String perfil = view.getJcbUsuPerfil().getSelectedItem().toString();
            
            //transformando o número de String para INT
            obj.setId(Integer.parseInt(view.getTxtUsuId().getText()));

            UsuarioDAO dao = new UsuarioDAO();
            dao.alterarUsuario(obj, perfil);
            limparCamposUsuario();
        }
    }

    public void excluirUsuario() {
        int confirma = JOptionPane.showConfirmDialog(null, "Você irá EXCLUIR o funcionário selecionado!", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            Usuario obj = new Usuario();
            obj.setCpf(view.getTxtUsuCpf().getText());

            UsuarioDAO dao = new UsuarioDAO();
            dao.excluirUsuario(obj);
            limparCamposUsuario();
        }
    }

    public void setarCampos() {
        //Desativando o botão cadastrar. OBS: Para não gerar registros duplicados
        view.getBtnUsuNovo().setEnabled(false);

        int setar = view.getTabelaUsuarios().getSelectedRow();
        
        view.getTxtUsuId().setText(view.getTabelaUsuarios().getModel().getValueAt(setar, 0).toString());
        view.getTxtUsuNome().setText((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 1));
        view.getTxtUsuCpf().setText((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 2));
        view.getTxtUsuEmail().setText((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 3));
        view.getTxtUsuTelefone().setText((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 4));
        view.getTxtUsuEndereco().setText((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 5));
        view.getTxtUsuLogin().setText((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 6));
        view.getTxtUsuSenha().setText((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 7));
        
        //Verificar...
        view.getJcbUsuPerfil().setSelectedItem((String) view.getTabelaUsuarios().getModel().getValueAt(setar, 8));
    }

    public void pesquisarUsuario() {
        String obj = (view.getTxtPesquisarUsuario().getText());

        //Ativando o botão cadastrar Usuário
        view.getBtnUsuNovo().setEnabled(true);

        UsuarioDAO dao = new UsuarioDAO();
        TableModel rs = dao.pesquisarUsuario(obj);
        view.getTabelaUsuarios().setModel(rs);
    }

    public void limparCamposUsuario() {
        view.getTxtUsuNome().setText((String) "");
        view.getTxtUsuCpf().setText((String) "");
        view.getTxtUsuId().setText((String) "");
        view.getTxtUsuEndereco().setText((String) "");
        view.getTxtUsuTelefone().setText((String) "");
        view.getTxtUsuLogin().setText((String) "");
        view.getTxtUsuSenha().setText((String) "");
        view.getTxtUsuEmail().setText((String) "");
    }

}
