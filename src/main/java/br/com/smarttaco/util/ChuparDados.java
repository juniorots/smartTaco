/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.util;

import br.com.smarttaco.base.AcidosGraxosDAO;
import br.com.smarttaco.base.ComposicaoAcidosDAO;
import br.com.smarttaco.base.ComposicaoAminoacidosDAO;
import br.com.smarttaco.base.ComposicaoElementosDAO;
import br.com.smarttaco.base.LaboratorioDAO;
import br.com.smarttaco.base.NomesCientificosDAO;
import br.com.smarttaco.base.TagnamesDAO;
import br.com.smarttaco.modelo.AcidoGraxo;
import br.com.smarttaco.modelo.ComposicaoAcidos;
import br.com.smarttaco.modelo.ComposicaoAminoacidos;
import br.com.smarttaco.modelo.ComposicaoElementos;
import br.com.smarttaco.modelo.Laboratorio;
import br.com.smarttaco.modelo.NomesCientificos;
import br.com.smarttaco.modelo.Tagnames;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jxl.Sheet;
import jxl.Workbook;
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
    private static final String GRUPO_AMINOACIDOS = "Aminoácidos ³";
    private static final String GRUPO_ACIDOS_ERRADO = "Ácdios graxos";
    private static final String GRUPO_ACIDOS_GRAXOS = "Ácidos graxos";
    
    // Grupos Nomes Cientificos
    private static final String CEREAIS_DERIVADOS = "Cereais e derivados";
    private static final String VERDURAS = "Verduras, hortaliças e derivados";
    private static final String FRUTAS = "Frutas e derivados";
    private static final String PESCADOS = "Pescados e frutos do mar";
    private static final String CARNES = "Carnes e derivados";
    private static final String MISCELANEAS = "Miscelâneas";
    private static final String LEGUMINOSAS = "Leguminosas e derivados";
    private static final String NOZES = "Nozes e Sementes";
    
    // Grupo Composicao Elementos
    private static final String GORDURAS = "Gorduras e óleos";
    private static final String LEITE = "Leite e derivados";
    private static final String BEBIDAS = "Bebidas (alcoólicas e não alcoólicas)";
    private static final String OVOS = "Ovos e derivados";
    private static final String ACUCARADOS = "Produtos açucarados";
    private static final String INDUSTRIALIZADOS = "Outros alimentos industrializados";
    private static final String PREPARADOS = "Alimentos preparados";
    
    /**
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
    
    /**
     * Tratando os grupos das tabelas centesimais
     * @param tipo
     * @return 
     */
    private static boolean identificarGrupo(String tipo, String grupo) {
        if ( "elementos".equalsIgnoreCase(tipo) 
                || "acidos".equalsIgnoreCase(tipo) ) {
            if ( CEREAIS_DERIVADOS.equalsIgnoreCase( grupo ) ) {
                return true;
            } 
            if ( VERDURAS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( FRUTAS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( PESCADOS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( CARNES.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( MISCELANEAS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( LEGUMINOSAS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( NOZES.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( GORDURAS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( LEITE.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( BEBIDAS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( OVOS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( ACUCARADOS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( INDUSTRIALIZADOS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
            if ( PREPARADOS.equalsIgnoreCase( grupo ) ) {
                return true;
            }
        } 
        return false;
    }
    
    /**
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
            
            // Limpando sujeira
            for (AcidoGraxo obj : dao.selectAll()) {
                dao.delete(obj);
            } 
            
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
    
    /**
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
            
            // Limpando sujeira
            for (Tagnames obj : dao.selectAll()) {
                dao.delete(obj);
            }
            
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
                            grupoTmp = "Aminoácidos - Os tagnames dos aminoácidos seguiram o padrão do USDA";
                        }
                        if ( GRUPO_ACIDOS_ERRADO.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.println("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                            grupoTmp = GRUPO_ACIDOS_GRAXOS;
                        }
                        
                        if (grupo) {
//                            if ( !GRUPO_ACIDOS_GRAXOS.equalsIgnoreCase( grupoTmp ))
//                                grupoTmp = mTmp.group(1).trim();
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
    
    /**
     * Trabalhando com a tabela Quadro 7
     */
    public static void tratarTabelaCientificos(String arquivo) {
        try {
            int linhas = 194;
            Pattern p = Pattern.compile("(^Quadro 7).+(:?(.|\\n).+$){"+linhas+"}", Pattern.MULTILINE);
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
            NomesCientificosDAO dao = new NomesCientificosDAO(entityManager);
            
            // Limpando sujeira
            for (NomesCientificos obj : dao.selectAll()) {
                dao.delete(obj);
            }
            
            Pattern pTmp = null;
            Matcher mTmp = null;
            String grupoTmp = CEREAIS_DERIVADOS;
            
            if (m.find() == true) {
                for (int i = 2; i <= linhas; i++) {
                    pTmp = Pattern.compile("(?=(\\n.*){"+i+"})");
                    mTmp = pTmp.matcher( m.group() );
                    boolean grupo = false; 
                    
                    if (mTmp.find() == true) {
    //                System.out.println( m.group() );

                        NomesCientificos cientifico = new NomesCientificos();

                        if ( CEREAIS_DERIVADOS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( VERDURAS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( FRUTAS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        if ( PESCADOS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        
                        if ( CARNES.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        
                        if ( MISCELANEAS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        
                        if ( LEGUMINOSAS.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }
                        
                        if ( NOZES.equalsIgnoreCase( mTmp.group(1).trim() ) ) {
//                            System.out.print("IDENTIFICADO GRUPO! >> "+mTmp.group(1));
                            grupo = true;
                        }

                        if (grupo) {
                            grupoTmp = mTmp.group(1).trim();
                            continue;
                        }

                        cientifico.setGrupo(grupoTmp);
                        int j = 1;
                        int t = 0;
                        
                        /*
                         * Primeira Coluna
                         */
                        while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                            t++;
                        
                        campoSensivel.clear();
                        campoSensivel.put("cabotian", "Cucurbita");
                        campoSensivel.put("menina", "Cucurbita");
                        campoSensivel.put("moranga", "Cucurbita");
                        campoSensivel.put("pescoço", "Cucurbita");
                        campoSensivel.put("italiana", "Cucurbita");
                        campoSensivel.put("paulista", "Cucurbita");
                        campoSensivel.put("porró", "Allium");
                        campoSensivel.put("baroa", "Chaerophyllum");
                        campoSensivel.put("Batata, doce", "Ipomoea");
                        campoSensivel.put("inglesa", "Solanum");
                        campoSensivel.put("Broto", "Vigna");
                        campoSensivel.put("manteiga", "Brassica");
                        campoSensivel.put("Zelândia", "Tetragonia");
                        campoSensivel.put("folha", "Brassica");
                        campoSensivel.put("Tomate, salada", "Lycopersicum");
                        campoSensivel.put("Banana, da terra", "Musa");
                        campoSensivel.put("Banana, figo", "Musa");
                        campoSensivel.put("Banana, maçã", "Musa");
                        campoSensivel.put("Banana, nanica", "Musa");
                        campoSensivel.put("ouro", "Musa");
                        campoSensivel.put("pacova", "Musa");
                        campoSensivel.put("Banana, prata", "Musa");
                        campoSensivel.put("Cajá", "Spondias");
                        campoSensivel.put("chocolate", "Diospyros");
                        campoSensivel.put("pão", "Artocarpus");
                        campoSensivel.put("Baía", "Citrus");
                        campoSensivel.put("var. da Terra", "Citrus");
                        campoSensivel.put("var. Lima", "Citrus");
                        campoSensivel.put("var. Pêra", "Citrus");
                        campoSensivel.put("var. Valência", "Citrus");
                        campoSensivel.put("var. Cravo", "Citrus");
                        campoSensivel.put("Galego", "Citrus");
                        campoSensivel.put("Tahiti", "Citrus");
                        campoSensivel.put("argentina", "Malus");
                        campoSensivel.put("cv. Fuji", "Malus");
                        campoSensivel.put("var. Murcote", "Citrus");
                        campoSensivel.put("var. Rio", "Citrus");
                        campoSensivel.put("cv. Aurora", "Prunus");
                        campoSensivel.put("var. Poncã", "Citrus");
                        campoSensivel.put("Rio Grande", "Penaeus");
                        campoSensivel.put("Sete Barbas", "Xiphopenaeus");
                        campoSensivel.put("Corvina de água doce", "Plagioscions");
                        campoSensivel.put("Corvina do mar", "Micropogonias");
                        campoSensivel.put("Corvina grande", "Micropogonias");
                        campoSensivel.put("Dourada", "Brachyplatystoma");
                        campoSensivel.put("branca", "Cynoscion");
                        campoSensivel.put("açúcar", "Saccharum");
                        campoSensivel.put("fradinho", "Vigna");
                        
                        t = identificarNomeComposto(campoSensivel, mTmp.group(1), t);
//                        System.out.print("["+mTmp.group(1).substring(j, t)+"] ");
                        cientifico.setAlimento(mTmp.group(1).substring(j, t));
                        
                        /*
                         * Segunda Coluna
                         */
                        t++;
                        try {
//                            System.out.println("["+mTmp.group(1).substring(t, mTmp.group(1).length()).trim()+"] ");
                            cientifico.setNomeCientifico(mTmp.group(1).substring(t, mTmp.group(1).length()).trim());
                        } catch (StringIndexOutOfBoundsException e) {
//                            System.out.println("[]");
                        }
                        
                        dao.insert( cientifico );
                        
                        // Pulando algumas linhas em branco...
                        if (i == 4 )
                            i++;
                        if (i == 46 )
                            i += 4;
                        if (i == 64 )
                            i += 2;
                        if (i == 96 )
                            i += 4;
                        if (i == 133 )
                            i++;
                        if (i == 148 )
                            i += 4;
                        if (i == 164 )
                            i++;
                        if (i == 169 )
                            i++;
                        if (i == 173 )
                            i++;
                        if (i == 183 )
                            i++;
                    } 
                    
                } // for
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
    /**
     * Trabalhando com a tabela Quadro 8
     */
    public static void tratarTabelaLaboratorio(String arquivo) {
        try {
            int linhas = 22;
            Pattern p = Pattern.compile("(^Quadro 8).+(:?(.|\\n).+$){"+linhas+"}", Pattern.MULTILINE);
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
            LaboratorioDAO dao = new LaboratorioDAO(entityManager);
            
            // Limpando sujeira
            for (Laboratorio obj : dao.selectAll()) {
                dao.delete(obj);
            }
            
            Pattern pTmp = null;
            Matcher mTmp = null;
            
            
            if (m.find() == true) {
//                System.out.println( m.group() );
                for (int i = 3; i <= linhas; i++) {
                    pTmp = Pattern.compile("(?=(\\n.*){"+i+"})");
                    mTmp = pTmp.matcher( m.group() );
                    
                    if (mTmp.find() == true) {
                        Laboratorio laboratorio = new Laboratorio();
                        int j = 1;
                        int t = 0;
//                        laboratorio.setGrupo("");
                        
                        campoSensivel.clear();
                        campoSensivel.put("UNB", "DF");
                        campoSensivel.put("CEPPA", "PR");
                        campoSensivel.put("Embrapa", "RJ");
                        campoSensivel.put("CIENTEC", "RS");
                        campoSensivel.put("FUNED", "MG");
                        campoSensivel.put("IAL", "SP");
                        campoSensivel.put("IPEN", "SP");
                        campoSensivel.put("ITAL", "SP");
                        campoSensivel.put("INPA", "AM");
                        campoSensivel.put("UNICAMP", "SP");
                        campoSensivel.put("DCA", "SP");
                        campoSensivel.put("USP", "SP");
                        campoSensivel.put("UFBA", "BA");
                        campoSensivel.put("UFPE", "PE");
                        campoSensivel.put("UFSC", "SC");
                        campoSensivel.put("UNIFESP", "SP");
                        campoSensivel.put("UFV", "MG");
                        campoSensivel.put("UFMT", "MT");
                        campoSensivel.put("UFPR", "PR");
                        campoSensivel.put("UFF", "RJ");
                        
                        /*
                         * Primeira Coluna
                         */
                        while (!mTmp.group(1).substring(t, t+1).equals(" ")) 
                            t++;
                        
                        t = identificarNomeComposto(campoSensivel, mTmp.group(1), t);
//                        System.out.print("["+mTmp.group(1).substring(j, t)+"] ");
                        laboratorio.setNoLaboratorio( mTmp.group(1).substring(j, t) );
                        
                        /*
                         * Segunda Coluna
                         */
                        t++;
                        
                        try {
//                            System.out.println("["+mTmp.group(1).substring(t, mTmp.group(1).length()).trim()+"] ");
                            laboratorio.setEstado(mTmp.group(1).substring(t, mTmp.group(1).length()).trim());
                        } catch (StringIndexOutOfBoundsException e) {
//                            System.out.println("[]");
                        }
                        dao.insert( laboratorio );
                    }
                } // for
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tratando a tabela centesimal de composicao de elementos...
     * @param book 
     */
    public static void tratarTabelaComposicaoElementos(Workbook book) {
        Sheet sheet = book.getSheet(0);
        
        int linhas = sheet.getRows();
        String tmpGrupo = CEREAIS_DERIVADOS;
        
         @Cleanup
            final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ComposicaoElementosDAO dao = new ComposicaoElementosDAO(entityManager);
        
        // Limpando sujeira
        for (ComposicaoElementos obj : dao.selectAll()) {
            dao.delete(obj);
        }
        
        for (int i = 3; i < linhas; i++) {
            ComposicaoElementos ce = new ComposicaoElementos();
            
            if ( identificarGrupo("elementos", (String) sheet.getCell(0, i).getContents() ) ) {
                tmpGrupo = (String) sheet.getCell(0, i).getContents();
                continue;
            } 

            ce.setGrupo( tmpGrupo );
            
            ce.setNumeroAlimento( Util.linkTacoVazio( (String) sheet.getCell(0, i).getContents() ) );
//            System.out.println( "["+ ce.getNumeroAlimento() +"]" );
            
            ce.setDescricaoAlimento( Util.linkTacoVazio( (String) sheet.getCell(1, i).getContents() ) );
//            System.out.print( "["+ ce.getDescricaoAlimento() +"]" );
            
            ce.setUmidade(Util.linkTacoVazio( (String) sheet.getCell(2, i).getContents() ) );
//            System.out.print( "["+ ce.getUmidade() +"]" );
            
            ce.setEnergia(Util.linkTacoVazio( (String) sheet.getCell(3, i).getContents() ) +" (kcal)" );
            ce.setEnergia( ce.getEnergia() + " / " +
                    Util.linkTacoVazio( (String) sheet.getCell(4, i).getContents() ) +" (kj)" );
//            System.out.print( "["+ ce.getEnergia() +"]" );
            
            ce.setProteina(Util.linkTacoVazio( (String) sheet.getCell(5, i).getContents() ) );
//            System.out.print( "["+ ce.getProteina() +"]" );
            
            ce.setLipideos(Util.linkTacoVazio( (String) sheet.getCell(6, i).getContents() ) );
//            System.out.print( "["+ ce.getLipideos() +"]" );
            
            ce.setColesterol(Util.linkTacoVazio( (String) sheet.getCell(7, i).getContents() ) );
//            System.out.print( "["+ ce.getColesterol() +"]" );
            
            ce.setCarboidrato(Util.linkTacoVazio( (String) sheet.getCell(8, i).getContents() ) );
//            System.out.print( "["+ ce.getCarboidrato() +"]" );
            
            ce.setFibraAlimentar(Util.linkTacoVazio( (String) sheet.getCell(9, i).getContents() ) );
//            System.out.print( "["+ ce.getFibraAlimentar() +"]" );
            
            ce.setCinzas(Util.linkTacoVazio( (String) sheet.getCell(10, i).getContents() ) );
//            System.out.print( "["+ ce.getCinzas() +"]" );
            
            ce.setCalcio(Util.linkTacoVazio( (String) sheet.getCell(11, i).getContents() ) );
//            System.out.print( "["+ ce.getCalcio() +"]" );
            
            ce.setMagnesio(Util.linkTacoVazio( (String) sheet.getCell(12, i).getContents() ) );
//            System.out.print( "["+ ce.getMagnesio() +"]" );
            
            ce.setManganes(Util.linkTacoVazio( (String) sheet.getCell(14, i).getContents() ) );
//            System.out.print( "["+ ce.getManganes() +"]" );
            
            ce.setFosforo(Util.linkTacoVazio( (String) sheet.getCell(15, i).getContents() ) );
//            System.out.print( "["+ ce.getFosforo() +"]" );
            
            ce.setFerro(Util.linkTacoVazio( (String) sheet.getCell(16, i).getContents() ) );
//            System.out.print( "["+ ce.getFerro() +"]" );
            
            ce.setSodio(Util.linkTacoVazio( (String) sheet.getCell(17, i).getContents() ) );
//            System.out.print( "["+ ce.getSodio() +"]" );
            
            ce.setPotassio(Util.linkTacoVazio( (String) sheet.getCell(18, i).getContents() ) );
//            System.out.print( "["+ ce.getPotassio() +"]" );
            
            ce.setCobre(Util.linkTacoVazio( (String) sheet.getCell(19, i).getContents() ) );
//            System.out.print( "["+ ce.getCobre() +"]" );
            
            ce.setZinco(Util.linkTacoVazio( (String) sheet.getCell(20, i).getContents() ) );
//            System.out.print( "["+ ce.getZinco() +"]" );
            
            ce.setRetinol(Util.linkTacoVazio( (String) sheet.getCell(21, i).getContents() ) );
//            System.out.print( "["+ ce.getRetinol() +"]" );
            
            ce.setRe(Util.linkTacoVazio( (String) sheet.getCell(22, i).getContents() ) );
//            System.out.print( "["+ ce.getRe() +"]" );
            
            ce.setRae(Util.linkTacoVazio( (String) sheet.getCell(23, i).getContents() ) );
//            System.out.print( "["+ ce.getRae() +"]" );
            
            ce.setTiamina(Util.linkTacoVazio( (String) sheet.getCell(24, i).getContents() ) );
//            System.out.print( "["+ ce.getTiamina() +"]" );
            
            ce.setRiboflavina(Util.linkTacoVazio( (String) sheet.getCell(25, i).getContents() ) );
//            System.out.print( "["+ ce.getRiboflavina() +"]" );
            
            ce.setPiridoxina(Util.linkTacoVazio( (String) sheet.getCell(26, i).getContents() ) );
//            System.out.print( "["+ ce.getPiridoxina() +"]" );
            
            ce.setNiacina(Util.linkTacoVazio( (String) sheet.getCell(27, i).getContents() ) );
//            System.out.print( "["+ ce.getNiacina() +"]" );
            
            ce.setVitaminaC(Util.linkTacoVazio( (String) sheet.getCell(28, i).getContents() ) );
//            System.out.println( "["+ ce.getVitaminaC() +"]" );
            
            dao.insert( ce );
            
            // pulando conteudo desnecessario! :-)
            if ( i == 34 ) 
                i += 3;
            if ( i == 69 ) {
                i += 5;
                tmpGrupo = VERDURAS;
            }
            if ( i == 104 ) 
                i += 3;
            if ( i == 139 ) 
                i += 3;
            if ( i == 174 ) 
                i += 3;
            if ( i == 182 ) {
                i++;
                tmpGrupo = FRUTAS;
            }
            if ( i == 209 ) 
                i += 3;
            if ( i == 244 ) 
                i += 3;
            if ( i == 279 ) 
                i += 3;
            if ( i == 289 ) {
                i += 2;
                tmpGrupo = GORDURAS;
            }
            if ( i == 305 ) {
                i += 3;
                tmpGrupo = PESCADOS;
            }
            if ( i == 314 ) 
                i += 3;
            if ( i == 349 ) 
                i += 3;
            if ( i == 364 ) {
                i += 2;
                tmpGrupo = CARNES;
            }
            if ( i == 384 ) 
                i += 3;
            if ( i == 419 ) 
                i += 3;
            if ( i == 454 ) 
                i += 3;
            if ( i == 489 ) 
                i += 3;
            if ( i == 501 ) {
                i += 2;
                tmpGrupo = LEITE;
            }
            if ( i == 523 ) 
                i += 3;
            if ( i == 530 ) {
                i += 2;
                tmpGrupo = BEBIDAS;
            }
            if ( i == 546 ) {
                i += 2;
                tmpGrupo = OVOS;
            }
            if ( i == 555 ) {
                i += 1;
                tmpGrupo = ACUCARADOS;
            }
            if ( i == 558 ) 
                i += 3;
            if ( i == 579 ) {
                i += 2;
                tmpGrupo = MISCELANEAS;
            }
            if ( i == 590 ) {
                i += 2;
                tmpGrupo = INDUSTRIALIZADOS;
            }
            if ( i == 593 ) 
                i += 3;
            if ( i == 600 ) { 
                i += 3;
                tmpGrupo = PREPARADOS;
            }
            if ( i == 628 ) 
                i += 3;
            if ( i == 638 ) { 
                i += 2;
                tmpGrupo = LEGUMINOSAS;
            }
            if ( i == 661 ) 
                i += 3;
            if ( i == 673 ) {
                i += 2;
                tmpGrupo = NOZES;
            }
            if ( i == 686 ) {
                break;
            }
            
        } // for
        entityManager.getTransaction().commit();
    }
    
    /**
     * Tratando a tabela centesimal de acidos graxos
     * @param book 
     */
    public static void tratarTabelaComposicaoAcidos(Workbook book) {
        Sheet sheet = book.getSheet(1);
        
        int linhas = sheet.getRows();
        String tmpGrupo = CEREAIS_DERIVADOS;
        
         @Cleanup
            final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ComposicaoAcidosDAO dao = new ComposicaoAcidosDAO(entityManager);
        
        // Limpando sujeira
        for (ComposicaoAcidos obj : dao.selectAll()) {
            dao.delete(obj);
        }
        
        for (int i = 3; i < linhas; i++) {
            ComposicaoAcidos ca = new ComposicaoAcidos();
            
            if ( identificarGrupo("acidos", (String) sheet.getCell(0, i).getContents() ) ) {
                tmpGrupo = (String) sheet.getCell(0, i).getContents();
                continue;
            } 

            ca.setGrupo( tmpGrupo );
            
            ca.setNumeroAlimento( Util.linkTacoVazio( (String) sheet.getCell(0, i).getContents() ) );
//            System.out.println( "["+ ca.getNumeroAlimento() +"]" );
            
            ca.setDescricaoAlimento( Util.linkTacoVazio( (String) sheet.getCell(1, i).getContents() ) );
//            System.out.print( "["+ ca.getDescricaoAlimento() +"]" );
            
            ca.setSaturados( Util.linkTacoVazio( (String) sheet.getCell(2, i).getContents() ) );
//            System.out.print( "["+ ca.getSaturados() +"]" );
            
            ca.setMonoInsaturados(Util.linkTacoVazio( (String) sheet.getCell(3, i).getContents() ) );
//            System.out.print( "["+ ca.getMonoInsaturados() +"]" );
            
            ca.setPoliInsaturados(Util.linkTacoVazio( (String) sheet.getCell(4, i).getContents() ) );
//            System.out.print( "["+ ca.getPoliInsaturados() +"]" );
            
            ca.setDoze(Util.linkTacoVazio( (String) sheet.getCell(5, i).getContents() ) );
//            System.out.print( "["+ ca.getDoze() +"]" );
            
            ca.setQuatorze(Util.linkTacoVazio( (String) sheet.getCell(6, i).getContents() ) );
//            System.out.print( "["+ ca.getQuatorze() +"]" );
            
            ca.setDezesseis(Util.linkTacoVazio( (String) sheet.getCell(7, i).getContents() ) );
//            System.out.print( "["+ ca.getDezesseis() +"]" );
            
            ca.setDezoito(Util.linkTacoVazio( (String) sheet.getCell(8, i).getContents() ) );
//            System.out.print( "["+ ca.getDezoito() +"]" );
            
            ca.setVinte(Util.linkTacoVazio( (String) sheet.getCell(9, i).getContents() ) );
//            System.out.print( "["+ ca.getVinte() +"]" );
            
            ca.setVinteDois(Util.linkTacoVazio( (String) sheet.getCell(10, i).getContents() ) );
//            System.out.print( "["+ ca.getVinteDois() +"]" );
            
            ca.setVinteQuatro(Util.linkTacoVazio( (String) sheet.getCell(11, i).getContents() ) );
//            System.out.print( "["+ ca.getVinteQuatro() +"]" );
            
            ca.setQuatorzeUm(Util.linkTacoVazio( (String) sheet.getCell(13, i).getContents() ) );
//            System.out.print( "["+ ca.getQuatorzeUm() +"]" );
            
            ca.setDezesseisUm(Util.linkTacoVazio( (String) sheet.getCell(14, i).getContents() ) );
//            System.out.print( "["+ ca.getDezesseisUm() +"]" );
            
            ca.setDezoitoUm(Util.linkTacoVazio( (String) sheet.getCell(15, i).getContents() ) );
//            System.out.print( "["+ ca.getDezoitoUm() +"]" );
            
            ca.setVinteUm(Util.linkTacoVazio( (String) sheet.getCell(16, i).getContents() ) );
//            System.out.print( "["+ ca.getVinteUm() +"]" );
            
            ca.setDezoitoDois(Util.linkTacoVazio( (String) sheet.getCell(17, i).getContents() ) );
//            System.out.print( "["+ ca.getDezoitoDois() +"]" );
            
            ca.setDezoitoTres(Util.linkTacoVazio( (String) sheet.getCell(18, i).getContents() ) );
//            System.out.print( "["+ ca.getDezoitoTres() +"]" );
            
            ca.setVinteQuatroSegundo(Util.linkTacoVazio( (String) sheet.getCell(19, i).getContents() ) );
//            System.out.print( "["+ ca.getVinteQuatroSegundo() +"]" );
            
            ca.setVinteCinco(Util.linkTacoVazio( (String) sheet.getCell(20, i).getContents() ) );
//            System.out.print( "["+ ca.getVinteCinco() +"]" );
            
            ca.setVinteDoisCinco(Util.linkTacoVazio( (String) sheet.getCell(21, i).getContents() ) );
//            System.out.print( "["+ ca.getVinteDoisCinco() +"]" );
            
            ca.setVinteDoisSeis(Util.linkTacoVazio( (String) sheet.getCell(22, i).getContents() ) );
//            System.out.print( "["+ ca.getVinteDoisSeis() +"]" );
            
            ca.setDezoitoUmT(Util.linkTacoVazio( (String) sheet.getCell(23, i).getContents() ) );
//            System.out.print( "["+ ca.getDezoitoUmT() +"]" );
            
            ca.setDezoitoDoisT(Util.linkTacoVazio( (String) sheet.getCell(24, i).getContents() ) );
//            System.out.println( "["+ ca.getDezoitoDoisT() +"]" );

            dao.insert( ca );
            
            // pulando conteudo desnecessario! :-)
            if ( i == 34 ) 
                i += 3;
            if ( i == 63 ) {
                i += 1;
                tmpGrupo = VERDURAS;
            }
            if ( i == 69 ) 
                i += 3;
            if ( i == 104 ) 
                i += 3;
            if ( i == 111 ) 
                i += 2;
            if ( i == 138 ) {
                i += 5;
                tmpGrupo = GORDURAS;
            }
            if ( i == 156 ) {
                i += 2;
                tmpGrupo = PESCADOS;
            }
            if ( i == 174 ) 
                i += 3;
            if ( i == 209 ) 
                i += 3;
            if ( i == 214 ) {
                i += 2;
                tmpGrupo = CARNES;
            }
            if ( i == 244 ) {
                i += 3;
            }
            
            if ( i == 279 ) 
                i += 3;
            if ( i == 314 ) 
                i += 3;
            if ( i == 346 ) {
                i += 5;
                tmpGrupo = LEITE;
            }
            if ( i == 372 ) {
                i += 2;
                tmpGrupo = OVOS;
            }
            if ( i == 380 ) {
                i += 5;
                tmpGrupo = ACUCARADOS;
            }
            if ( i == 395 ) {
                i += 2;
                tmpGrupo = MISCELANEAS;
            }
            if ( i == 400 ) {
                i += 2;
                tmpGrupo = INDUSTRIALIZADOS;
            }
            if ( i == 407 ) {
                i += 2;
                tmpGrupo = PREPARADOS;
            }
            if ( i == 416 ) 
                i += 3;
            if ( i == 444 ) {
                i += 2;
                tmpGrupo = LEGUMINOSAS;
            }
            if ( i == 451 ) {
                i += 3;
            }
            if ( i == 478 ) {
                i += 2;
                tmpGrupo = NOZES;
            }
            if ( i == 486 ) 
                i += 3;
            if ( i == 493 ) {
                break;
            }
        } // for
        entityManager.getTransaction().commit();
    }
    
    /**
     * Tratando a tabela centesimal de aminoacidos...
     * @param book 
     */
    public static void tratarTabelaComposicaoAminoacidos(Workbook book) {
        Sheet sheet = book.getSheet(2);
        
        int linhas = sheet.getRows();
        String tmpGrupo = "";
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ComposicaoAminoacidosDAO dao = new ComposicaoAminoacidosDAO(entityManager);
        
        // Limpando sujeira
        for (ComposicaoAminoacidos obj : dao.selectAll()) {
            dao.delete(obj);
        }
        
        for (int i = 3; i < linhas; i++) {
            ComposicaoAminoacidos ca = new ComposicaoAminoacidos();
            
            if ( identificarGrupo("acidos", (String) sheet.getCell(0, i).getContents() ) ) {
                tmpGrupo = (String) sheet.getCell(0, i).getContents();
                continue;
            } 

            ca.setGrupo( tmpGrupo );
            
            ca.setNumeroAlimento( Util.linkTacoVazio( (String) sheet.getCell(0, i).getContents() ) );
//            System.out.println( "["+ ca.getNumeroAlimento() +"]" );
            
            ca.setDescricaoAlimento( Util.linkTacoVazio( (String) sheet.getCell(1, i).getContents() ) );
//            System.out.print( "["+ ca.getDescricaoAlimento() +"]" );
            
            ca.setTriptofano( Util.linkTacoVazio( (String) sheet.getCell(2, i).getContents() ) );
//            System.out.print( "["+ ca.getTriptofano() +"]" );
            
            ca.setTreonina( Util.linkTacoVazio( (String) sheet.getCell(3, i).getContents() ) );
//            System.out.print( "["+ ca.getTreonina() +"]" );
            
            ca.setIsoleucina( Util.linkTacoVazio( (String) sheet.getCell(4, i).getContents() ) );
//            System.out.print( "["+ ca.getIsoleucina() +"]" );
            
            ca.setLeucina( Util.linkTacoVazio( (String) sheet.getCell(5, i).getContents() ) );
//            System.out.print( "["+ ca.getLeucina() +"]" );
            
            ca.setLisina( Util.linkTacoVazio( (String) sheet.getCell(6, i).getContents() ) );
//            System.out.print( "["+ ca.getLisina() +"]" );
            
            ca.setMetionina( Util.linkTacoVazio( (String) sheet.getCell(7, i).getContents() ) );
//            System.out.print( "["+ ca.getMetionina() +"]" );
            
            ca.setCistina( Util.linkTacoVazio( (String) sheet.getCell(8, i).getContents() ) );
//            System.out.print( "["+ ca.getCistina() +"]" );
            
            ca.setFenilalanina( Util.linkTacoVazio( (String) sheet.getCell(9, i).getContents() ) );
//            System.out.print( "["+ ca.getFenilalanina() +"]" );
            
            ca.setTirosina( Util.linkTacoVazio( (String) sheet.getCell(10, i).getContents() ) );
//            System.out.print( "["+ ca.getTirosina() +"]" );
            
            ca.setValina( Util.linkTacoVazio( (String) sheet.getCell(12, i).getContents() ) );
//            System.out.print( "["+ ca.getValina() +"]" );
            
            ca.setArginina( Util.linkTacoVazio( (String) sheet.getCell(13, i).getContents() ) );
//            System.out.print( "["+ ca.getArginina() +"]" );
            
            ca.setHistidina( Util.linkTacoVazio( (String) sheet.getCell(14, i).getContents() ) );
//            System.out.print( "["+ ca.getHistidina() +"]" );
            
            ca.setAlanina( Util.linkTacoVazio( (String) sheet.getCell(15, i).getContents() ) );
//            System.out.print( "["+ ca.getAlanina() +"]" );
            
            ca.setAcidoAspartico( Util.linkTacoVazio( (String) sheet.getCell(16, i).getContents() ) );
//            System.out.print( "["+ ca.getAcidoAspartico() +"]" );
            
            ca.setAcidoGlutamico( Util.linkTacoVazio( (String) sheet.getCell(17, i).getContents() ) );
//            System.out.print( "["+ ca.getAcidoGlutamico() +"]" );
            
            ca.setGlicina( Util.linkTacoVazio( (String) sheet.getCell(18, i).getContents() ) );
//            System.out.print( "["+ ca.getGlicina() +"]" );
            
            ca.setProlina( Util.linkTacoVazio( (String) sheet.getCell(19, i).getContents() ) );
//            System.out.print( "["+ ca.getProlina() +"]" );
            
            ca.setSerina( Util.linkTacoVazio( (String) sheet.getCell(20, i).getContents() ) );
//            System.out.println( "["+ ca.getSerina() +"]" );
            
            dao.insert( ca );
            
            // pulando conteudo desnecessario! :-)
            if ( i == 28 ) 
                break;
        } // for
        entityManager.getTransaction().commit();
    }
    
    /**
     * Inserindo as notas nas tabelas específicas...
     */
    public static void inserirNota() {
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TagnamesDAO dao = new TagnamesDAO(entityManager);
        ComposicaoElementosDAO ceDao = new ComposicaoElementosDAO(entityManager);
        
        Tagnames tag = new Tagnames();
        tag.setNutriente( "RE (equivalente de retinol)" );
        tag = dao.findByStringField("nutriente", tag.getNutriente(), true, 0, 1).get(0);
        tag.setNutriente(Util.montarLink(tag.getNutriente(), "1"));
        dao.update( tag );
        
        tag = new Tagnames();
        tag.setNutriente( "RAE (equivalente de atividade de retinol)" );
        tag = dao.findByStringField("nutriente", tag.getNutriente(), true, 0, 1).get(0);
        tag.setNutriente(Util.montarLink(tag.getNutriente(), "2"));
        dao.update( tag );
        
        ComposicaoElementos ce = new ComposicaoElementos();
        ce.setNumeroAlimento("29");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "6"));
        ce.setRae( Util.montarLink( ce.getRae(), "6"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("33");     
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("43");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("44");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("45");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("65");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("66");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("67");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("75");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("76");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("78");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("79");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("84");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("96");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("104");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("109");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("110");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("115");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("116");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("119");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("143");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("144");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("145");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("149");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("155");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("156");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("157");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("158");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("159");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("160");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("169");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("170");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("185");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("186");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("187");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("188");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("200");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("217");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("225");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("226");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("228");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("232");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("235");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("236");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("240");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("241");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        ce = new ComposicaoElementos();
        ce.setNumeroAlimento("247");
        ce = ceDao.findByStringField("numeroAlimento", ce.getNumeroAlimento(), true, 0, 1).get(0);
        ce.setRe( Util.montarLink( ce.getRe(), "7"));
        ce.setRae( Util.montarLink( ce.getRae(), "7"));
        ceDao.update( ce );
        
        entityManager.getTransaction().commit();
    }
}
