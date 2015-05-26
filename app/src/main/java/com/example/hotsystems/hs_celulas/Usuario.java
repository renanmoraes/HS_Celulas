package com.example.hotsystems.hs_celulas;

import android.app.Activity;

public class Usuario extends Activity{
    private String strEmail;
    private String strSenha;
    private String cod_igrej;


    public Usuario() {
        super();
    }
    public Usuario(String email, String senha, String cod_igrej) {
        super();
        this.strEmail = email;
        this.strSenha = senha;
        this.cod_igrej = cod_igrej;
    }


    public String getEmail() {
        return strEmail;
    }
    public void setEmail(String email) {
        this.strEmail = email;
    }
    public String getSenha() {
        return strSenha;
    }
    public void setSenha(String senha) {
        this.strSenha = senha;
    }
    public String getCod() {
        return cod_igrej;
    }
    public void setCod(String cod_igrej) {
        this.cod_igrej = cod_igrej;
    }

}
