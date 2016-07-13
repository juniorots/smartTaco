/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.controller;

import br.com.smarttaco.modelo.TabelaCarrocel;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@SessionScoped
public class PrincipalCarrocelMB implements Serializable {
    private UsuarioMB usuario = new UsuarioMB();
    private ArrayList<TabelaCarrocel> tabelas = null;
    
    @PostConstruct
    public void init() {
        tabelas = new ArrayList<>();
        tabelas.add(new TabelaCarrocel("acidosGraxos.png"));
        tabelas.add(new TabelaCarrocel("tagnames.png"));
    }
    
    
    public ArrayList<TabelaCarrocel> getTabelas() {
        return tabelas;
    }

    public void setTabelas(ArrayList<TabelaCarrocel> tabelas) {
        this.tabelas = tabelas;
    }
    
}
