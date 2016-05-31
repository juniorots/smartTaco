/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.util;

import br.com.smarttaco.base.AcidosGraxosDAO;
import br.com.smarttaco.base.TagnamesDAO;
import br.com.smarttaco.modelo.AcidoGraxo;
import br.com.smarttaco.modelo.Tagnames;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
    private static final String GRUPO_ACIDOS_ERRADO = "Ácdios graxos";
    private static final String GRUPO_ACIDOS_GRAXOS = "Ácidos graxos";
    
    /*
     * Responsavel por identificar os nomes compostos
     */
    private static int identificarNomeComposto( HashMap<String, String> lista, String linha,
            int inicio ) {
        int retorno = inicio;
        for (Iterator it = lista.entrySet().iterator(); it.hasNext();) {
            Map.Entry tmp = (Map.Entry) it.next();

            if (linha.contains( (String) tmp.getKey() )) {
                if (Util.isEmpty( (String) tmp.getValue() ) ) {
                    return linha.length();
                }
                
                int j = ++inicio;
                while(!linha.substring(inicio, inicio+1).equals(" ")) {
                    inicio++;
                }
                if (linha.substring(j, inicio).trim().equalsIgnoreCase( (String) tmp.getValue() )) {
                    return j-1; // retirando espacos no final do campo...
                } else {
                    return identificarNomeComposto(lista, linha, inicio);
                }
            }
        }
        return retorno;
    }
    
    /*
    * Trabalhando com a tabela Quadro 5
    */
    public static void tratarTabelaNomesSistematicos(String arquivo) {
        try {
            int linhas = 33;
            Pattern p = Pattern.compile("(^Quadro 5).+(:?(.|\\n).+$){"+linhas+"}", Pattern.MULTILINE);
            Matcher m = p.matcher(arquivo);
            
            /*
             * Nota:
             * Semantica do campoSensivel:
             * key: valor unico entre os elementos da tabela
             * value: Representa o limite da coluna, assim deve-se concatenar o seu valor
             * ate encontrar esse valor limite... (null = campo nao contem valor)
             */
            HashMap<String, String> campoSensivel = new HashMap<>();
            
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
                for (int i = 2; i <= linhas; i++) {
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
                        
                        /*
                         * Primeira Coluna
                         */
                        while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                            t++;
                        
                        campoSensivel.clear();
                        campoSensivel.put("Trans-octadecadienóico", "Trans-octadecadienóico");
                        campoSensivel.put("Trans-octadecenóico", "Trans-octadecenóico");
                        t = identificarNomeComposto(campoSensivel, mTmp.group(1), t);
//                        System.out.print("["+mTmp.group(1).substring(j, t)+"] ");
                        acido.setAcidosGraxos(mTmp.group(1).substring(j, t));
                        
                        /*
                         * Segunda Coluna
                         */
                        j = t++;
                        j++;
                        while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                            t++;    
                        
                        campoSensivel.clear();
                        campoSensivel.put("Eicosapentaenóico", "Timnodônico");
                        campoSensivel.put("Docosapentaenóico", "Clupanodônico");
                        campoSensivel.put("Docosahexaenóico", null);
                        t = identificarNomeComposto(campoSensivel, mTmp.group(1), t);
//                        System.out.print("["+mTmp.group(1).substring(j, t).trim()+"] ");
                        acido.setNomeSistematico(mTmp.group(1).substring(j, t));
                        
                        /*
                         * Terceira Coluna
                         */
                        t++;
                        try {
//                            System.out.println("["+mTmp.group(1).substring(t, mTmp.group(1).length()).trim()+"] ");
                            acido.setNomeComum(mTmp.group(1).substring(t, mTmp.group(1).length()).trim());
                        } catch (StringIndexOutOfBoundsException e) {
//                            System.out.println("[]");
                        }
                        
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
            int linha = 111;
            Pattern p = Pattern.compile("(^Quadro 6).+(:?(.|\\n).+$){"+linha+"}", Pattern.MULTILINE);
            Matcher m = p.matcher(arquivo);
            
            /*
             * Nota:
             * Semantica do campoSensivel:
             * key: valor unico entre os elementos da tabela
             * value: Representa o limite da coluna, assim deve-se concatenar o seu valor
             * ate encontrar esse valor limite... (null = campo nao contem valor)
             */
            HashMap<String, String> campoSensivel = new HashMap<>();
            
            Pattern pTmp = null;
            Matcher mTmp = null;
            String grupoTmp = GRUPO_COMPOSICAO_CENTESIMAL;
            
            @Cleanup
            final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

            @Cleanup
            final EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            TagnamesDAO dao = new TagnamesDAO(entityManager);
            
            if (m.find() == true) {
//                System.out.println(m.group());
                for (int i = 2; i <= linha; i++) {
                    pTmp = Pattern.compile("(?=(\\n.*){"+i+"})");
                    mTmp = pTmp.matcher( m.group() );
                    boolean grupo = false;  
                    if (mTmp.find() == true) {
                        Tagnames tagnames = new Tagnames();
//                        System.out.println( mTmp.group(1) );
                        
                        if ( GRUPO_COMPOSICAO_CENTESIMAL.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( GRUPO_VITAMINAS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( GRUPO_MINERAIS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( mTmp.group(1).trim().contains( GRUPO_AMINOACIDOS ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                            grupoTmp = GRUPO_AMINOACIDOS;
                        }
                        if ( GRUPO_ACIDOS_ERRADO.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                            grupoTmp = GRUPO_ACIDOS_GRAXOS;
                        }
                        
                        if (grupo) {
                            if ( !GRUPO_AMINOACIDOS.equalsIgnoreCase( grupoTmp ) 
                                    && !GRUPO_ACIDOS_GRAXOS.equalsIgnoreCase( grupoTmp ))
                                grupoTmp = mTmp.group(1).trim();
                            continue;
                        }
                        
                        tagnames.setGrupo(grupoTmp);
                        int j = 1;
                        int t = 0;
//                        System.out.print("[Grupo="+tagnames.getGrupo()+"] ");
                        
                        /*
                         * Primeira Coluna
                         */
                        while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                            t++;
                        
                        campoSensivel.clear();
                        campoSensivel.put("Lipídeos totais", "g");
                        campoSensivel.put("Carboidrato", "g");
                        campoSensivel.put("Fibra,", "g");
                        campoSensivel.put("Cálcio,", "mg");
                        campoSensivel.put("Ferro,", "mg");
                        campoSensivel.put("Magnésio,", "mg");
                        campoSensivel.put("Fósforo,", "mg");
                        campoSensivel.put("Potássio,", "mg");
                        campoSensivel.put("Sódio,", "mg");
                        campoSensivel.put("Zinco", "mg");
                        campoSensivel.put("Cobre,", "mg");
                        campoSensivel.put("Manganês,", "mg");
                        campoSensivel.put("Vitamina B", "mg");
                        campoSensivel.put("Vitamina C", "mg");
                        campoSensivel.put("<VITB6A>", "mg");
                        campoSensivel.put("<ASP_G>", "g");
                        campoSensivel.put("<GLU_G>", "g");
                        campoSensivel.put("<FASAT>", "g");
                        campoSensivel.put("<FAMS>", "g");
                        campoSensivel.put("<F18D1CN9>", "g");
                        campoSensivel.put("<FAPU>", "g");
                        campoSensivel.put("<F18D2CN6>", "g");
                        campoSensivel.put("<F18D3CN3>", "g");
                        campoSensivel.put("<F18D4N3>", "g");
                        campoSensivel.put("<F20D4N6>", "g");
                        campoSensivel.put("<F20D5N3>", "g");
                        campoSensivel.put("<F22D6N3>", "g");
                        campoSensivel.put("<FATRN>", "g");
                        campoSensivel.put("<F18D1T>", "g");
                        campoSensivel.put("18:2 t", "g");
                        campoSensivel.put("<F20D2CN6>", "g");
                        
                        // excecoes...
                        if (mTmp.group(1).contains("<F18D2TN6>")) continue;
                        if (mTmp.group(1).contains("<MG>")) t = 12;
                        if (mTmp.group(1).contains("RE (equivalente de retinol)")) {
//                            System.out.println("[RE (equivalente de retinol)] [μg] [<VITA>]");
                            tagnames.setNutriente("RE (equivalente de retinol)");
                            tagnames.setUnidade("μg");
                            tagnames.setInfoods("<VITA>");
                            dao.insert( tagnames );
                            i += 2;
                            continue;
                        }
                        if (mTmp.group(1).contains("RAE")) {
//                            System.out.println("[RAE (equivalente de atividade de retinol)] [μg] [<VITA>]");
                            tagnames.setNutriente("RAE (equivalente de atividade de retinol)");
                            tagnames.setUnidade("μg");
                            tagnames.setInfoods("<VITA>");
                            dao.insert( tagnames );
                            i += 4;
                            continue;
                        }
                        
                        t = identificarNomeComposto(campoSensivel, mTmp.group(1), t);
//                        System.out.print("["+mTmp.group(1).substring(j, t)+"] ");
                        tagnames.setNutriente( mTmp.group(1).substring(j, t) );
                        
                        /*
                         * Segunda Coluna
                         */
                        j = ++t;
                        
                        if ( mTmp.group(1).contains("18:2 t") ) {
//                            System.out.println("[g] [<F18D2TN9>]");  
                            tagnames.setUnidade("g");
                            tagnames.setInfoods("<F18D2TN9>");
                            i += 2;
                            dao.insert( tagnames );
                            continue;
                        }
                        
                        if ( !mTmp.group(1).substring(t, t+3).equals("m g") ) {
                            while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                                t++;    
//                            System.out.print("["+mTmp.group(1).substring(j, t).trim()+"] ");
                            tagnames.setUnidade(mTmp.group(1).substring(j, t));
                        } else {
//                            System.out.print("[μg] ");
                            tagnames.setUnidade("μg");
                            t += 3;
                        } 
                                
                        /*
                         * Terceira Coluna
                         */
                        t++;
                        try {
//                            System.out.println("["+mTmp.group(1).substring(t, mTmp.group(1).length()).trim()+"] ");
                            tagnames.setInfoods(mTmp.group(1).substring(t, mTmp.group(1).length()).trim());
                        } catch (StringIndexOutOfBoundsException e) {
//                            System.out.println("[]");
                        }
                        
                        // Tratando a quebra de pagina...
                        if ( mTmp.group(1).contains("<VAL_G>") ) {
                            i += 13;
                        }
                        
                        if ( mTmp.group(1).contains("<CHOLE>") ) {
                            dao.insert( tagnames );
                            break; // terminou a leitura da tabela...
                        }
                        dao.insert( tagnames );
                    }
                } // for
             
                entityManager.getTransaction().commit();   
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
