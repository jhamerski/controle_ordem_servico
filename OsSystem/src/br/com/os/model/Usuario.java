/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.model;

/**
 *
 * @author Jonas Hamerski
 */
public class Usuario extends Pessoa{
    private String login;
    private String senha;
    private String perfil;
   
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String acesso) {
        this.perfil = perfil;
    }
    
}
