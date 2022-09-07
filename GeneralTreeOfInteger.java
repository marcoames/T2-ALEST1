
import java.util.LinkedList;

public class GeneralTreeOfInteger {

    // Classe interna Node
    private class Node {
        // Atributos da classe Node
        public Node father;
        public Integer element;
        public String name;
        public LinkedList<Node> subtrees;

        // Métodos da classe Node
        public Node(String name, Integer element) {
            father = null;
            this.name = name;
            this.element = element;
            subtrees = new LinkedList<>();
        }
        private void addSubtree(Node n) {
            n.father = this;
            subtrees.add(n);
        }
        private boolean removeSubtree(Node n) {
            n.father = null;
            return subtrees.remove(n);
        }
        public Node getSubtree(int i) {
            if ((i < 0) || (i >= subtrees.size())) {
                throw new IndexOutOfBoundsException();
            }
            return subtrees.get(i);
        }
        public int getSubtreesSize() {
            return subtrees.size();
        }
    }
    
    // Atributos da classe GeneralTreeOfInteger
    private Node root;
    private int count;

    // Metodos da classe GeneralTreeOfInteger
    
    /**
     * Metodo construtor.
     */
    public GeneralTreeOfInteger() {
        root = null;
        count = 0;
    }
    
    /**
     * Retorna o numero total de elementos da arvore.
     * @return count
     */
    public int size() {
        return count;
    }
    
    /** Procura por "elem" a partir de "n" seguindo um
     * caminhamento pre-fixado. Retorna a referencia
     * para o nodo no qual "elem" esta armazenado.
     * Se não encontrar "elem", ele retorna NULL.
     */
    private Node searchNodeRef(String name, Node n) {
        if (n == null)
            return null;
        
        // visita a raiz
        if(name.equals(n.name) ) { // se elem esta no nodo n
            return n; // retorna a referencia para n
        }
        // visita os filhos
        else {
            Node aux = null;
            for (int i = 0; i < n.getSubtreesSize() && aux == null; i++) {
                aux = searchNodeRef(name, n.getSubtree(i));
            }
            return aux;
        }
    }

    /**
     * Adiciona elem como filho de father
     * @param name nome do filho.
     * @param elem elemento a ser adicionado na arvore.
     * @param namePai nome do pai.
     * @return true se encontrou father e adicionou elem na arvore,
     * false caso contrario.
     */
    public boolean add(String name, Integer elem, String namePai) {
        // Primeiro cria o nodo
        Node n = new Node(name,elem);
        
        // Verifica se eh para inserir como raiz
        if (namePai == null) { // se a arvore nao estiver vazia
            if (root != null) {
                n.addSubtree(root); 
                root.father = n;
            }
            root = n;
            count++;
            return true;
        }
        else {
            // inserir "elem" como filho de "elemPai"
            // Procura por elemPai
            Node aux = searchNodeRef(namePai, root);
            if (aux != null) {    // se achou elemPai
                aux.addSubtree(n);  // aux esta apontando para o nodo
                                    // no qual elemPai esta armazenado
                n.father = aux;
                count++;
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se elem esta ou não na arvore.
     * @param name a ser procurado
     * @return true se achar elem, e false caso contrario.
     */
    public boolean contains (String name) {
        Node aux = this.searchNodeRef(name, root);
        if (aux == null)
            return false;
        else
            return true;
    }

    /**
     * Retorna uma lista com todos os elementos da árvore numa ordem de 
     * caminhamento em largura.
     * @return lista com os elementos da arvore na ordem do caminhamento em largura
     */
    public LinkedList<String> positionsWidth() {
        LinkedList<String> lista = new LinkedList<>();
        
        if (root != null) { // se a arvore nao estiver vazia
            Queue<Node> fila = new Queue<>(); // cria fila de nodos
            // Primeiro coloca a raiz na fila
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                // Retira da fila
                Node aux = fila.dequeue();
                // Coloca o elemento na lista
                lista.add(aux.name + "_" + aux.element);
                // Coloca os filhos na fila
                for (int i = 0; i < aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
            }
        }
        return lista;
    }

    /**
     *  Atualiza os elementos da árvore
     *  Caminhamento em largura para atualizar os dados da árvore
     */
    public void atualizaTerras() {
        if (root != null) { // se a arvore nao estiver vazia
            Queue<Node> fila = new Queue<>(); // cria fila de nodos
            // Primeiro coloca a raiz na fila
            fila.enqueue(root);
            while (!fila.isEmpty()) {
                // Retira da fila
                Node aux = fila.dequeue();
                // divide numero de terras do pai pelo número filhos
                int terrasDivididas = 0;
                int numFilhos = aux.getSubtreesSize();
                if (numFilhos != 0) {
                    terrasDivididas = aux.element / numFilhos;
                }
                // adiciona parte das terras do pai para o filho
                for (int i = 0; i < aux.getSubtreesSize(); i++) {
                    aux.getSubtree(i).element = aux.getSubtree(i).element + terrasDivididas;
                }
                // Coloca os filhos na fila
                for (int i = 0; i < aux.getSubtreesSize(); i++) {
                    fila.enqueue(aux.getSubtree(i));
                }
            }
        }
    }

    /**
     * Retorna uma lista com todos os elementos da árvore numa ordem de 
     * caminhamento pre-fixado.
     * @return lista com os elementos da árvore na ordem do caminhamento pre-fixado
     */    
    public LinkedList<Integer> positionsPre() {  
        LinkedList<Integer> lista = new LinkedList<>();
        positionsPreAux(root, lista);
        return lista;
    }  
    
    private void positionsPreAux(Node n, LinkedList<Integer> lista) {
        if (n != null) {
            // Visita a raiz
            lista.add(n.element);
            // Visita os filhos
            for (int i = 0; i < n.getSubtreesSize(); i++) {
                positionsPreAux(n.getSubtree(i), lista);
            }
        }
    }

    /**
     * Retorna uma lista com todos os elementos da árvore numa ordem de 
     * caminhamento pos-fixado.
     * @return lista com os elementos da árvore na ordem do caminhamento pos-fixado
     */     
    public LinkedList<Integer> positionsPos() {  
        LinkedList<Integer> lista = new LinkedList<>();
        positionsPosAux(root, lista);
        return lista;
    }  
    
    private void positionsPosAux(Node n, LinkedList<Integer> lista) {
        if (n != null) {
            // Visita os filhos
            for (int i = 0; i < n.getSubtreesSize(); i++) {
                positionsPosAux(n.getSubtree(i), lista);
            }
            // Visita a raiz
            lista.add(n.element);
        }
    }

    // Nodo de nível mais alto com maior valor.
    public String ultimoGuerreiro() {
        LinkedList<Node> lista = new LinkedList<>();
        ultimoGuerreiroAux(root, lista);

        // elemento do nivel mais alto com mais terras
        String nomeMaior = null;
        int maior = 0;

        for (Node n : lista) {
            if (n.element > maior) {
                maior = n.element;
                nomeMaior = n.name;
            }
        }
        return nomeMaior + " " + maior;
    }

    // Coloca nodos do nível mais alto em uma lista
    private void ultimoGuerreiroAux(Node n, LinkedList<Node> lista) {
        int maiorLevel = this.maiorNivel();
        Queue<Node> fila = new Queue<>(); // cria fila de nodos
        // Primeiro coloca a raiz na fila
        fila.enqueue(root);
        while (!fila.isEmpty()) {
            // Retira da fila
            Node aux = fila.dequeue();
            if (this.level(aux.name) == maiorLevel) {
                // Coloca o elemento na lista
                lista.add(aux);
            }
            // Coloca os filhos na fila
            for (int i = 0; i < aux.getSubtreesSize(); i++) {
                fila.enqueue(aux.getSubtree(i));
            }
        }
    }

    // Descobre o nivel mais alto da arvore
    public int maiorNivel() {
        int maior = 0;
        Queue<Node> fila = new Queue<>(); // cria fila de nodos
        // Primeiro coloca a raiz na fila
        fila.enqueue(root);
        while (!fila.isEmpty()) {
            // Retira da fila
            Node aux = fila.dequeue();
            if (this.level(aux.name) > maior) {
                // atualiza o maior
                maior = this.level(aux.name);
            }
            // Coloca os filhos na fila
            for (int i = 0; i < aux.getSubtreesSize(); i++) {
                fila.enqueue(aux.getSubtree(i));
            }
        }
        return maior;
    }

    /**
     * Retorna em que nível em que elem está armazenado.
     * @param name a ser buscado
     * @return nível no qual name esta, ou -1 se
     * nao encontrou element.
     */
    public int level(String name) {
        int level = -1;
        Node aux = this.searchNodeRef(name, root);
        while (aux != null) {
            aux = aux.father;
            level++;
        }
        return level;
    }

    /**
     * Remove o galho da árvore que tem element na raiz. A
     * remocao inclui o nodo que contem "element".
     * @param element elemento que sera removido junto com sua
     * subárvore.
     * @return true se achou element e removeu o galho, false
     * caso contrario.
     */
    public boolean removeBranch(String name, Integer element) {
        if (root == null)
            return false;

        if (element.equals(root.element)) {
            root = null;
            count = 0;
            return true;
        }

        Node aux = this.searchNodeRef(name, root);
        if (aux == null)
            return false;

        Node pai = aux.father;
        pai.removeSubtree(aux);
        aux.father = null;
        count = count - countNodes(aux);
        return true;
    }

    // Conta o número de nodos da subárvore cuja raiz eh passada por parametro
    private int countNodes(Node n) {
        if (n == null)
            return 0;

        int c = 0;
        for (int i = 0; i < n.getSubtreesSize(); i++) {
            c = c + countNodes(n.getSubtree(i));
        }

        return c + 1;
    }

    // Codigos abaixo geram saida para GraphViz
    public void geraNodosDOT(Node n) {
        System.out.println("node [shape = circle];\n");

        LinkedList<String> L = new LinkedList<>();
        L = positionsWidth();

        for (int i = 0; i< L.size(); i++ ) {
            // node1 [label = "1"]
            System.out.println("node" + L.get(i) + " [label = \"" +  L.get(i) + "\"]") ;
        }

    }

    public void geraConexoesDOT(Node n) {
        for (int i = 0; i < n.getSubtreesSize(); i++)
        {
            Node aux = n.getSubtree(i);
            System.out.println("node" + n.name + "_" + n.element + " -> " + "node" + aux.name + "_" + aux.element + ";");
            geraConexoesDOT(aux);
        }
    }

    // Gera uma saida no formato DOT
    // Esta saida pode ser visualizada no GraphViz
    // Versoes online do GraphViz pode ser encontradas em
    // http://www.webgraphviz.com/
    // http://viz-js.com/
    // https://dreampuf.github.io/GraphvizOnline
    public void geraDOT() {
        System.out.println("digraph g { \n");
        // node [style=filled];

        geraNodosDOT(root);

        geraConexoesDOT(root);
        System.out.println("}\n");
    }
}
