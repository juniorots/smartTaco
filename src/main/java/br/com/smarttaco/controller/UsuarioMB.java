package br.com.smarttaco.controller;

import br.com.smarttaco.base.UsuarioDAO;
import br.com.smarttaco.modelo.Usuario;
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
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;
import org.primefaces.context.RequestContext;

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
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        UsuarioDAO dao = new UsuarioDAO(entityManager);
        getUsuario().setSenha( Util.cifrar( getUsuario().getSenha() ) );
        Usuario usAlterado = dao.update( getUsuario() );
        entityManager.getTransaction().commit();
        
        mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Status", "Dados alterados com sucesso.");
        usAlterado.setNomeTitulo( getUsuario().getNome() );
        Util.gravarUsuarioSessao( usAlterado );
        setUsuario ( Util.captarUsuarioSessao() );
        
        RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }
    
    /**
     * Responsavel por persistir as informacoes digitadas na base
     */
    public void salvarUsuario() {
        
        FacesMessage mensagem = null;
        
        if ( !continuarRegistro( getUsuario() ) ) {
            mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no cadastro. E-mail já registrado no sistema.", "");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return;
        }
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        UsuarioDAO dao = new UsuarioDAO(entityManager);
        getUsuario().setSenha( Util.cifrar( getUsuario().getSenha() ) );
        Usuario usInserido = dao.insert( getUsuario() );
        entityManager.getTransaction().commit();
        
        if ( !Util.isEmpty( usInserido.getId() ) ) {
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Status", "Usuário cadastrado com sucesso.");
            usInserido.setNomeTitulo( usInserido.getNome() );
            Util.gravarUsuarioSessao( usInserido );
            setUsuario( Util.captarUsuarioSessao() );
        } else {
            mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Status", "Falha no cadastro. Operação cancelada.");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
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
        
        if ( !Util.isEmpty( dao.findByStringField("email", usuario.getEmail(), true, 0, 1) ) ) {
            return false;
        }
        
        return true;
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
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "E-mail ou Senha inválidos.") );
            context.getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    /**
     * Tratando da solicitacao de recuperacao de conta
     */
    public void recuperarConta() {
        
        FacesMessage mensagem = null;
        
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
            
            mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Status", "Uma senha automática fora enviado para o e-mail informado, <br />"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;após a sua validação procure alterá-la.");
        } else {
            mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Informações inexistentes em nossa base de dados, favor tentar novamente.");            
        }
        usuario = new Usuario();
        
        RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }
    
    /**
     * Tratando do fechamento da sessao aberta perlo usuario
     */
    public void sairSistema() {
        setUsuario( new Usuario() );
        Util.gravarUsuarioSessao( getUsuario() );
    }
}
