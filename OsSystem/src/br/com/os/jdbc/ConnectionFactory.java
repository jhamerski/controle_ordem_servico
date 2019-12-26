/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.os.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jonas Hamerski
 */
public class ConnectionFactory {
    
    private boolean status = false;
    private String mensagem = "";   //variavel que vai informar o status da conexao
    private Connection con = null;  //variavel para conexao
    private Statement statement;
    private ResultSet resultSet;

    //Sempre que for alterar o servidor/nome do banco/ usuario e senha (ALTERAR AQUI)
    private String servidor = "localhost";
    private String nomeDoBanco = "aps";
    private String usuario = "postgres";
    private String senha = "020812";


    public ConnectionFactory(String pServidor, String pNomeDoBanco, String pUsuario, String pSenha) {
        this.servidor = pServidor;
        this.nomeDoBanco = pNomeDoBanco;
        this.usuario = pUsuario;
        this.senha = pSenha;
    }

    public ConnectionFactory(){
        
    }
    /**
     * Abre uma conexao com o banco
     *
     * @return Connection
     */
    public Connection getConnection() {//Atualiza/alterar registros no banco
        try {
            //Driver do PostgreSQL
            Class.forName("org.postgresql.Driver").newInstance();

            //local do banco, nome do banco, usuario e senha
            String url = ("jdbc:postgresql://"+servidor+":5432/"+nomeDoBanco);
            this.setCon((Connection) DriverManager.getConnection(url, usuario, senha));

            //se ocorrer tudo bem, ou seja, se conectar a linha a seguir é executada
            this.status = true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            //JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return this.getCon();
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getNomeDoBanco() {
        return nomeDoBanco;
    }

    public void setNomeDoBanco(String nomeDoBanco) {
        this.nomeDoBanco = nomeDoBanco;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
