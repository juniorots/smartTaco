/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.util;

/**
 *
 * @author Jose Alves
 */
public class Constantes {
    /*
     * Util - navegacao
     */
    public static String INICIO_SISTEMA = "/index.xhtml";
    public static String CADASTRAR_USUARIO = "/jsp/cadastrarUsuario.xhtml";
    public static String ACIDOS_GRAXOS = "/jsp/acidosGraxos.xhtml";
    public static String TAGNAMES = "/jsp/tagnames.xhtml";
    
    public static String NOMES_CIENTIFICOS = "/jsp/nomesCientificos.xhtml";
    public static String COMPOSICAO_ELEMENTOS = "/jsp/composicaoElementos.xhtml";
    public static String COMPOSICAO_ACIDOS = "/jsp/composicaoAcidos.xhtml";
    public static String COMPOSICAO_AMINOACIDOS = "/jsp/composicaoAminoacidos.xhtml";
    
    public static String HOST_NAME_GMAIL = "smtp.gmail.com";
    public static String HOST_NAME_HOTMAIL = "smtp.live.com";
    public static String ADMINISTRADOR_1 = "juniorots@gmail.com";
    public static Integer PORTA_SMTP_GMAIL = 465;

   /*
    * Nota: tal usuario somente sera utilizado para envio automatico
    * de senhas, assim nao deve ser utilizado para responder qualquer questionamento
    * ou item relacionado a correspondencia que nao seja destinado ao sistema Dedoduro.
    */
    public static String EMAIL_REMETENTE_GMAIL = "dedoduro.default.user@gmail.com";
    public static String SENHA_REMETENTE_GMAIL = "d3d0Dur0123";
    
    /**
     * Uteis em threads
     */
    public static int UM_SEGUNDO = 1000;
    public static int UM_MINUTO = 60 * UM_SEGUNDO;
    public static int UMA_HORA = 60 * UM_MINUTO;
    
    public static final Integer LIMITE_COLUNAS = 10;
    
    public static String ELIPSE_TACO = "TACO";
    public static String ELIPSE_ACIDOS = "Ácidos Graxos";
    public static String ELIPSE_TAGNAMES = "Tagnames segundo INFOODS e USDA";
    public static String ELIPSE_CIENTIFICOS = "Nomes Científicos";
    
    public static String ELIPSE_CENTESIMAIS = "Tabelas Centesimais";
    public static String ELIPSE_AMINOACIDOS = "Aminoácidos";
    public static String ELIPSE_ELEMENTOS = "Centesimal, minerais, vitaminas e colesterol";
}
