/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.smarttaco.util;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.List;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

/**
 *
 * @author Jose Alves
 */
public class HelenaBarbosa {
    
    public static void tratarArquivo(String arquivo) {
        File file = new File(arquivo);
        try {
            FileInputStream fin = new FileInputStream(file);

            FileChannel chanel = fin.getChannel();

            ByteBuffer buff = ByteBuffer.allocate(1024);

            int bytes = chanel.read(buff);
            Charset charset = Charset.forName("UTF-8");

            StringBuilder conteudoArquivo = new StringBuilder();
            conteudoArquivo.delete(0, conteudoArquivo.length());

            while (bytes != -1) {
                buff.flip();
                while (buff.hasRemaining()) {
                    String s = charset.decode(buff).toString();
                    conteudoArquivo.append(s);
                }
                buff.clear();
                bytes = chanel.read(buff);
            }
            
            // Filtrando...
            ChuparDados.tratarTabelaNomesSistematicos( conteudoArquivo.toString() );
            ChuparDados.tratarTabelaTagnames( conteudoArquivo.toString() );
            ChuparDados.tratarTabelaCientificos( conteudoArquivo.toString() );
            ChuparDados.tratarTabelaLaboratorio( conteudoArquivo.toString() );
            
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    
    /**
     * pdf2txt
     *
     * @param pdf
     * @param paginas se for <code>null</code> realiza leitura completa.
     * @param txt
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void pdf2txt(final String pdf, List<Integer> paginas, final String txt) throws FileNotFoundException, IOException {
        PdfReader reader = new PdfReader(pdf);
        //System.out.println(reader.getInfo().toString());
        if (paginas != null) {
            reader.selectPages(paginas);
        }
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PrintWriter out = new PrintWriter(txt, "UTF-8");
        TextExtractionStrategy strategy;
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
            out.println(strategy.getResultantText());
        }
        out.flush();
        out.close();
        reader.close();
    }
    
    public static void executar(final String arquivo, List<Integer> paginas, final String pdf) {
        try {
            deletarArquivo(arquivo);
            pdf2txt(pdf, paginas, arquivo);
            HelenaBarbosa.tratarArquivo(arquivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tratando entrada em formato .xls
     */
    public static void executarExcel(final String arquivo) {
        try {
            WorkbookSettings set = new WorkbookSettings();
            set.setEncoding("ISO-8859-1");
            Workbook book = Workbook.getWorkbook( new File(arquivo), set );
            
            // filtrando...
//            ChuparDados.tratarTabelaComposicaoElementos(book);
            ChuparDados.tratarTabelaComposicaoAcidos(book);
//            ChuparDados.tratarTabelaComposicaoAminoacidos(book);
        } catch (IOException io) {
            io.printStackTrace();
        } catch (BiffException be){
            be.printStackTrace();
        }
    }
    
    public static void deletarArquivo(final String txt) {
        File f = new File(txt);
        if (f.isFile() && f.canRead()) {
            if (!(f.delete())) {
                throw new RuntimeException("O arquivo [" + txt + "] não pode ser deletado");
            }
        }
    }
}
