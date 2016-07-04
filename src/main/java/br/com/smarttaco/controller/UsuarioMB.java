package br.com.smarttaco.controller;

import br.com.smarttaco.base.UsuarioDAO;
import br.com.smarttaco.modelo.Usuario;
import br.com.smarttaco.util.Constantes;
import br.com.smarttaco.util.EnviarEmail;
import br.com.smarttaco.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@RequestScoped
public class UsuarioMB implements Serializable {

    private Usuario usuario= null;
    private Collection<Usuario> listaUsuario = new ArrayList<>();
    
    /**
     * Creates a new instance of UsuarioMB
     */
    public UsuarioMB() {
        usuario = Util.captarUsuarioSessao();
        if ( Util.isEmpty( getUsuario() ) ) {
            usuario = new Usuario();
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Collection<Usuario> getListaUsuario() {
        if (listaUsuario == null) {
            return new ArrayList<Usuario>();
        }
    
        return listaUsuario;
    }

    public void setListaUsuario(Collection<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    /**
     * Responsavel por alterar as informacoes do usuario logado
     */
    public void alterarUsuario() {
        FacesMessage mensagem = null;
        
        if ( !validarDados() ) return;
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        UsuarioDAO dao = new UsuarioDAO(entityManager);
        getUsuario().setSenha( Util.cifrar( getUsuario().getSenha() ) );
        Usuario usAlterado = dao.update( getUsuario() );
        entityManager.getTransaction().commit();
        
        Util.montarMensagem(FacesMessage.SEVERITY_INFO, "Dados alterados com sucesso.");
        usAlterado.setNomeTitulo( getUsuario().getNome() );
        Util.gravarUsuarioSessao( usAlterado );
        setUsuario ( Util.captarUsuarioSessao() );
        
//        mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hum...", "descritivo aqui...");
//        RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }

    /**
     * Responsavel por persistir as informacoes digitadas na base
     */
    public void salvarUsuario() {
        
        FacesMessage mensagem = null;
        
        if ( !validarDados() ) return;
        
        if ( !continuarRegistro( getUsuario() ) ) {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no cadastro. E-mail já registrado no sistema.");
            return;
        }
        
        @Cleanup
        final EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entManager = entityFactory.createEntityManager();
        entManager.getTransaction().begin();
        UsuarioDAO dao = new UsuarioDAO(entManager);
        
        getUsuario().setSenha( Util.cifrar( getUsuario().getSenha() ) );
        Usuario usInserido = dao.insert( getUsuario() );
        entManager.getTransaction().commit();
        
        if ( !Util.isEmpty( usInserido.getId() ) ) {
            Util.montarMensagem(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso.");
            usInserido.setNomeTitulo( usInserido.getNome() );
            Util.gravarUsuarioSessao( usInserido );
            setUsuario( Util.captarUsuarioSessao() );
        } else {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no cadastro. Operação cancelada.");
        }
    }
    
    /*
     * Validando dados inseridos
     */
    public boolean validarDados() {
        if ( !getUsuario().getSenha().equalsIgnoreCase( getUsuario().getConfirmaSenha() )) {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no processo. Senhas diferentes");
            return false; // fail! :-(
        }
        
        if ( !validarEmail() ) return false; // fail! :-(
        
        return true; // passou! :-)
    }
    
    /**
     * Tratando da validacao de emails...
     * @return 
     */
    public boolean validarEmail() {
        if ( !Util.validarEmail( getUsuario().getEmail() ) ) {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no processo. E-mail invalido");
            return false; // fail! :-(
        }
        return true; // acerto! :-)
    }
    
    /**
     * Responsavel por verificar se o e-mail ja nao esta inserido na base de dados
     * @param usuario
     * @return 
     */
    private boolean continuarRegistro(Usuario usuario) {
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        UsuarioDAO dao = new UsuarioDAO(entityManager);
        return Util.isEmpty( dao.findByStringField("email", usuario.getEmail(), true, 0, 1) );
    }
    
    /**
     * Credenciando usuario
     */
    public void validarUsuario() {

        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        UsuarioDAO dao = new UsuarioDAO(entityManager);
        
        HashMap<String, String> campos = new HashMap<>();
        campos.put("email", getUsuario().getEmail() );
        campos.put("senha", Util.cifrar( getUsuario().getSenha() ) );

        ArrayList<Usuario> retorno = (ArrayList<Usuario>) dao.findByStringFields(campos, true, 0, 1);
        
        if (!Util.isEmpty( retorno ) ) {
            Usuario retornoUsuario = retorno.get(0);
            retornoUsuario.setNomeTitulo( retornoUsuario.getNome() );
            Util.gravarUsuarioSessao( retornoUsuario );
            setUsuario( retornoUsuario );
        } else {
            getUsuario().setEmail("");
            Util.montarMensagemModal(FacesMessage.SEVERITY_ERROR, "Vixi...", "E-mail ou Senha inválidos.");
        }
    }
    
    /**
     * Tratando da solicitacao de recuperacao de conta
     */
    public void recuperarConta() {
        
        FacesMessage mensagem = null;
        
        if ( !validarEmail() ) return;
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        UsuarioDAO dao = new UsuarioDAO(entityManager);
        
        HashMap<String, String> campos = new HashMap<>();
        HashMap<String, Date> campoData = new HashMap<>();
        campos.put("email", getUsuario().getEmail() );
        campoData.put("dtNascimento", getUsuario().getDtNascimento() );
        
        Usuario retorno = (Usuario) dao.findByStringDateOperatorEqual(campos, campoData, true, 0, 1);
        
        if ( !Util.isEmpty( retorno ) ) {
    
            Random random = new Random();
            String novaSenha = Util.cifrarRecuperacao( String.valueOf( random.nextInt( 1000000 ) ) );
            retorno.setSenha( Util.cifrar( novaSenha ) );
            
            dao.update( retorno );
            entityManager.getTransaction().commit();
            
            ArrayList emails = new ArrayList();
            emails.add( retorno.getEmail() );
            
            EnviarEmail.recuperarSenha(emails, novaSenha);
            
            Util.montarMensagem(FacesMessage.SEVERITY_INFO, "Uma senha automática fora enviado para o e-mail informado, <br />"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;após a sua validação procure alterá-la.");
        } else {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Informações inexistentes em nossa base de dados, favor tentar novamente.");
        }
        usuario = new Usuario();
    }
    
    /**
     * Tratando do fechamento da sessao aberta pelo usuario
     * @return 
     */
    public void sairSistema() {
        setUsuario( new Usuario() );
        Util.gravarUsuarioSessao( getUsuario() );
        Util.forward( Constantes.INICIO_SISTEMA );
    }
}
