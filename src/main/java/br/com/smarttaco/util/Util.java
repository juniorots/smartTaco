/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.util;

import br.com.smarttaco.modelo.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jose Alves
 */
public class Util {
    
    /**
     * Capta a data no formato, especificado pelo usuario
     * caso nao seja especificado um formato valido
     * ser utilizado o padrao: dd/MM/yyyy
     * 
     * @return 
     */
    public static String captarDataFormatada ( Date data, String padrao) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formato = null;
        if ( isEmpty (padrao) ) {
            formato  = new SimpleDateFormat("dd/MM/yyyy");        
        } else {
            formato  = new SimpleDateFormat( padrao );        
        }
        sb.append( formato.format(data) );
        return sb.toString();
    }
    
    /**
     * Trabalha na formatacao da data no formato yyyy-MM-dd
     * @param data
     * @return 
     */
    public static Date formatarDataBanco ( String pData ) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = new Date( formato.parse(pData).getTime() );
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return data;
    }
    
    public static String cifrar(String texto) {
        StringBuilder sb = new StringBuilder();
        
        if ( isEmpty( texto )) {
            return "";
        }
        
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
            byte message[] = algoritmo.digest(texto.getBytes("ISO-8859-1"));
            for (byte b : message) {
                sb.append( String.format("%02X",0xFF & b) );
            }
        } catch (NoSuchAlgorithmException ae) {
            ae.printStackTrace();
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }
        return sb.toString();
    }
    
    /**
     * Dever-se-a ser utilizado para recuperacao de senha, quando o usuario solicitar tal funcionalidade
     * @param texto
     * @return 
     */
    public static String cifrarRecuperacao (String texto) {
        StringBuilder sb = new StringBuilder();
        
        if ( isEmpty( texto )) {
            return "";
        }
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("MD5");
            byte message[] = algoritmo.digest(texto.getBytes("ISO-8859-1"));
            
            sb.append(message);
        }  catch (NoSuchAlgorithmException ae) {
            ae.printStackTrace();
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }
        
        return sb.toString();
    }
    
    /**
     * Verificando objeto nulo
     * @param obj
     * @return 
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && ((String) obj).equals("") ) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).size() == 0)  {
            return true;
        } else {
            return false;
        }
    }
    
   /*
    * Gravando o usuario na sessao
    */
    public static void gravarUsuarioSessao(Usuario usuario) {
        FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute( "CODIGO_USUARIO", usuario.getId() );
        sessao.setAttribute( "NOME_USUARIO", usuario.getNome() );
        sessao.setAttribute( "NOME_TITULO", usuario.getNomeTitulo() );
        sessao.setAttribute( "EMAIL_USUARIO", usuario.getEmail() );
        sessao.setAttribute( "TELEFONE_USUARIO", usuario.getTelefone() );
        sessao.setAttribute( "DT_NASCIMENTO_USUARIO", usuario.getDtNascimento() );
    }
    
   /*
    * Captando o usuário da sessao
    */
    public static Usuario captarUsuarioSessao() {
        Usuario usuario = null;
        ExternalContext external =  FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sessao = (HttpSession) external.getSession(true);
        
        if ( !isEmpty(sessao.getAttribute("CODIGO_USUARIO") ) ) {
            usuario = new Usuario();
            usuario.setId( (UUID) sessao.getAttribute("CODIGO_USUARIO") );
            usuario.setNome( (String) sessao.getAttribute("NOME_USUARIO") );
            usuario.setNomeTitulo( (String) sessao.getAttribute("NOME_TITULO") );
            usuario.setEmail( (String) sessao.getAttribute("EMAIL_USUARIO") );
            usuario.setTelefone( (String) sessao.getAttribute("TELEFONE_USUARIO") );
            usuario.setDtNascimento( (Date) sessao.getAttribute("DT_NASCIMENTO_USUARIO") );
        }
        
        return usuario;
    }
    
    /*
    * Gravando o usuario na sessao
    
    public static void gravarConcursoSessao(Concurso concurso) {
        FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute( "CODIGO_CONCURSO", concurso.getId() );
        sessao.setAttribute( "NOME_CONCURSO", concurso.getNomeConcurso());
        sessao.setAttribute( "URL_IMAGEM", concurso.getUrlImagem());
        sessao.setAttribute( "URL", concurso.getUrl() );
    }
    */
    
   /*
    * Captando o usuário da sessao
    
    public static Concurso captarConcursoSessao() {
        Concurso concurso = null;
        ExternalContext external =  FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sessao = (HttpSession) external.getSession(true);
        
        if ( !isEmpty(sessao.getAttribute("CODIGO_CONCURSO") ) ) {
            concurso = new Concurso();
            concurso.setId( (UUID) sessao.getAttribute("CODIGO_CONCURSO") );
            concurso.setNomeConcurso( (String) sessao.getAttribute("NOME_CONCURSO") );
            concurso.setUrlImagem( (String) sessao.getAttribute("URL_IMAGEM") );
            concurso.setUrl( (String) sessao.getAttribute("URL") );
        }
        
        return concurso;
    }
    */
    
    /*
     * Util para limpar dados da sessao
     */
    public static void limparUsuarioSessao() {
       FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute("CODIGO_USUARIO", "");
    }
    
    /*
     * Util para limpar dados da sessao
     */
    public static void limparConcursoSessao() {
       FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute("CODIGO_CONCURSO", "");
    }
}
