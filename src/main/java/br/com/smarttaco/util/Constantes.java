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
    public static String INICIO_SISTEMA = "inicio";
    public static String CADASTRAR_USUARIO = "inicio";
    public static String ACIDOS_GRAXOS = "inicio";
    
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
}
