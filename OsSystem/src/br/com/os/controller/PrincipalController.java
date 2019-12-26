package br.com.os.controller;


import java.text.DateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import br.com.os.view.ViewSobre;
import br.com.os.view.TelaCliente;
import br.com.os.view.ViewOrdemServico;
import br.com.os.view.ViewPrincipal;
import br.com.os.view.ViewUsuario;

public class PrincipalController {

    private final ViewPrincipal view;

    public PrincipalController(ViewPrincipal view) {
        this.view = view;
    }
    
    public void formatarData() {
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
        view.getLblData().setText(formatador.format(data));
    }

    public void sairDoSistema() {
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void telaSobre() {
        ViewSobre sobre = new ViewSobre();
        sobre.setVisible(true);
    }
    
    public void telaUsuario() {  
        ViewUsuario usuario = new ViewUsuario();
        usuario.setVisible(true);
        view.getDesktop().add(usuario);
    }

    public void telaCliente() {
        TelaCliente cliente = new TelaCliente(); 
        cliente.setVisible(true);
        view.getDesktop().add(cliente);
    }
    
    public void telaOrdemServico(){
        ViewOrdemServico tela = new ViewOrdemServico();
        tela.setVisible(true);
        view.getDesktop().add(tela);
    }

}
