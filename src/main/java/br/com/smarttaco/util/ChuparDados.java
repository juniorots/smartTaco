/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.util;

import br.com.smarttaco.base.AcidosGraxosDAO;
import br.com.smarttaco.modelo.AcidoGraxo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;

/**
 * Captando as informacoes dos arquivos e jogando na base de dados
 * @author Jose Alves
 */
public class ChuparDados {
    
    // Grupos Nomes sistematicos
    private static final String GRUPO_SATURADOS = "Ácidos graxos saturados";
    private static final String GRUPO_MONOINSATURADOS = "Ácidos graxos monoinsaturados";
    private static final String GRUPO_POLI_INSATURADOS = "Ácidos graxos polinsaturados";
    private static final String GRUPO_GRAXOS_TRANS = "Ácidos graxos trans";
    
    // Grupos Tagnames
    private static final String GRUPO_COMPOSICAO_CENTESIMAL = "Composição Centesimal";
    private static final String GRUPO_MINERAIS = "Minerais";
    private static final String GRUPO_VITAMINAS = "Vitaminas";
    private static final String GRUPO_AMINOACIDOS = "Aminoácidos";
    private static final String GRUPO_ACIDOS_GRAXOS = "Ácidos graxos";
    
    /*
     * Responsavel por identificar os nomes compostos
     */
//    private int identificarNomeComposto(String[] lista, String linha,
//            int inicioAnterior, int inicioProximo) {
//        int retorno = inicio;
//        for (int i = 0; i < lista.length; i++) {
//            if (linha.substring(inicio+1, inicio+3).equalsIgnoreCase(lista[i])) {
//                while(!linha.substring(inicio, inicio+1).equals(" ")) 
//                    retorno++;
//                identificarNomeComposto(lista, linha, retorno);
//            }
//        }
//        
//        
//        return retorno;
//    }
    
    /*
    * Trabalhando com a tabela Quadro 5
    */
    public static void tratarTabelaNomesSistematicos(String arquivo) {
        try {
            Pattern p = Pattern.compile("(^Quadro 5).+(:?(.|\\n).+$){33}", Pattern.MULTILINE);
            Matcher m = p.matcher(arquivo);
            String[] colunaSeguinte = {"Tim", "Clu"};
            
            @Cleanup
            final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

            @Cleanup
            final EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            AcidosGraxosDAO dao = new AcidosGraxosDAO(entityManager);
            
            Pattern pTmp = null;
            Matcher mTmp = null;
            String grupoTmp = GRUPO_SATURADOS;
            if (m.find() == true) {
                for (int i = 2; i <= 33; i++) {
                    pTmp = Pattern.compile("(?=(\\n.*){"+i+"})");
                    mTmp = pTmp.matcher( m.group() );
                    boolean grupo = false;  
                    if (mTmp.find() == true) {
                        AcidoGraxo acido = new AcidoGraxo();
//                        System.out.println( mTmp.group(1) );
                        
                        if ( GRUPO_SATURADOS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( GRUPO_MONOINSATURADOS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( GRUPO_POLI_INSATURADOS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( GRUPO_GRAXOS_TRANS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        
                        if (grupo) {
                            grupoTmp = mTmp.group(1).trim();
                            continue;
                        }

                        acido.setGrupo(grupoTmp);
                        int j = 1;
                        int t = 0;
                        
                        // Primeira coluna
                        while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                            t++;
//                        System.out.print("["+mTmp.group(1).substring(j, t)+"] ");
                        acido.setAcidosGraxos(mTmp.group(1).substring(j, t));
                        
                        // Segunda coluna
                        j = t++;
                        j++;
                        while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                            t++;    
                        
//                        System.out.print("["+mTmp.group(1).substring(j, t)+"] ");
                        acido.setNomeSistematico(mTmp.group(1).substring(j, t));
                        
                        // Terceira coluna
                        t++;
//                        System.out.println("["+mTmp.group(1).substring(t, mTmp.group(1).length()).trim()+"] ");
                        acido.setNomeComum(mTmp.group(1).substring(t, mTmp.group(1).length()).trim());
                        
                        dao.insert( acido );
                    }

                }
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    * Trabalhando com a tabela Quadro 6
    */
    public static void tratarTabelaTagnames(String arquivo) {
        try {
            Pattern p = Pattern.compile("(^Quadro 6).+(:?(.|\\n).+$){111}", Pattern.MULTILINE);
            Matcher m = p.matcher(arquivo);
            
            Pattern pTmp = null;
            Matcher mTmp = null;
            String grupoTmp = GRUPO_SATURADOS;
            
            if (m.find() == true) {
                System.out.println(m.group());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
