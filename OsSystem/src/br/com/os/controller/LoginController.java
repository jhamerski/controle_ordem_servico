/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.controller;

import br.com.os.DAO.UsuarioDAO;
import br.com.os.jdbc.ConnectionFactory;
import br.com.os.view.ViewLogin;
import java.sql.Connection;

/**
 *
 * @author Arthur Ern
 */
public class LoginController {

    private Connection con;
    private final ViewLogin view;

    public LoginController(ViewLogin view) {
        this.view = view;
    }
    
    public void setarImagem(){      
        con = new ConnectionFactory().getConnection();
        
        //a linha abaixo serve de apoio ao status da conexao
        if (con != null) {
            view.getLblStatus().setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/imagens/DBOk.png")));
        } else {
            view.getLblStatus().setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/os/imagens/DBError.png")));
        }
    }
      
    public void entrarNoSistema(){
        String email = view.getTxtUsuario().getText();
        String senha = view.getTxtSenha().getText();

        UsuarioDAO dao = new UsuarioDAO();
        dao.efetuaLogin(email, senha);
        
    }
    
    public void limpaTela(){
        view.getTxtUsuario().setText("");
        view.getTxtSenha().setText("");
    }

}
