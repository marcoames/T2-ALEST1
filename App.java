import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

public class App {
   public static void main(String[] args) {

       String caso = "caso06a.txt";
       Path path1 = Paths.get("casosDeTestes//" + caso);

       GeneralTreeOfInteger arvore = new GeneralTreeOfInteger();

       // le arquivo e constroi arvore
       try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
           String[] aux;

           // le 1 linha
           String s = reader.readLine();
           //System.out.println(Integer.parseInt(s));

           // cria variaveis
           int terrasPrimeiro = Integer.parseInt(s);
           int terrasFilho;

           String nomePai;
           String nomeFilho;

           // le 2º linha em diante
           String line = null;
           while ((line = reader.readLine()) != null) {
               aux = line.split(" ");

               // coloca os dados da linha em variaveis
               nomePai = aux[0];
               nomeFilho = aux[1];
               terrasFilho = Integer.parseInt(aux[2]);

               // adiciona raiz
               if (arvore.size() == 0) {
                   arvore.add(nomePai, terrasPrimeiro, null);
               }

               // adiciona elementos na arvore
               arvore.add(nomeFilho, terrasFilho, nomePai);

           }

       } catch (IOException e) {
           System.err.format("Erro na leitura do arquivo: ", e);
       }

       // divide terra dos pais entre filhos
       arvore.atualizaTerras();

       // arvore atualizada
       System.out.println("Código para visualizar no GraphViz ");
       arvore.geraDOT();

       // CASO DE TESTE
       System.out.println("Caso de Teste: " + caso);

       // RESULTADO DO TESTE
       System.out.println("Guerreiro no ultimo nível com mais terras: " + arvore.ultimoGuerreiro());
       
   }

}
