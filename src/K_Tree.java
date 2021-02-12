import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class K_Tree{
    private Node root;

    private class Node{
        private int key;
        private int lv;
        private Node child;
        private Node sibling;

        public Node(int key){
            this.key = key;
            this.child = this.sibling = null;
        }

        @Override
        public String toString() {
            return "" + this.key;
        }
    }

    public K_Tree(){
        this.root = null;
    }

    public K_Tree(int k){
        this.root = new Node(k);
    }


    /** this method adds a new Sibling to a current Node */
    public Node addSibling(Node curr, int key){
        assert(curr != root); // Impossible to add a new Sibling to Root
        if(curr == null)
            return null;
        while(curr.sibling != null){
            curr = curr.sibling;
        }
        return curr.sibling = new Node(key);
    }


    /** this method adds a new Child to a current Node */
    public Node addChild(Node curr, int key){
        if(curr == null)
            return null;
        
        if(curr.child != null)
            return addSibling(curr.child,key);
        else 
            return curr.child = new Node(key);
    }

    /** DFS using stack LIFO
     *  Time complexity O(n)
     */
    public void DFS(Node t){
        Stack s = new Stack<Node>();
        s.push(t);
        while(!s.isEmpty()){
            Node t1 = (Node)s.pop();
            System.out.println(t1);
            Node c = t1.child; 
            while(c != null){
                s.push(c);
                c = c.sibling;
            }
        } 
    }

    /** DFS : recursive way
     *  Time complexity O(n)
    */
    public void DFS_rec(Node t){
        System.out.println(t);
        Node c = t.child;
        while(c != null){
            DFS_rec(c);
            c = c.sibling;
        }
    }


    /** BFS using queue FIFO (queue is abstract, It requires a LinkedList
     *  Time complexity O(n)
     */
    public void BFS(Node t){
        Queue q = new LinkedList<Node>();
        q.add(t);
        while(!q.isEmpty()){
            Node t1 = (Node)q.remove();
            System.out.println(t1);
            Node c = t1.child; 
            while(c != null){
                q.add(c);
                c = c.sibling;
            }
        } 
    }


    /** Algoritmo che restituisce true se l'etichetta di ogni nodo che ha almeno un figlio è uguale alla somma delle 
     * etichette dei figli, false altrimenti
     */
    public boolean fatherChildSum(Node t){
        if(t.child == null)
            return true;
        else{
            boolean check = true;
            Node c = t.child;
            int s = 0;
            while(c != null){
                s = s + c.key;
                check = check && fatherChildSum(c);
                c = c.sibling;
            }
            return t.key == s && check;
        }

    }

    /** Algoritmo che aggiunge ad ogni foglia di T un nodo avente come chiave la somma di 
     * tutte le chiavi dalla radice alla foglia */
    public void sommaCammino(Node t, int k){
        k += t.key;
        if(t.child == null)
            t.child = new Node(k);
        else{
            Node c = t.child;
            while(c != null){
                sommaCammino(c, k);
                c = c.sibling;
            }
        }
    }

    /** Algoritmo che restituisce il numero di nodi dell'albero che si trovano a livello <= h (h = altezza) */
    public int numNodiProfondi(Node t, int h){
        if(t == null)
            return 0;
        else if(h == 0)
            return 1;
        else{
            int count = 1;
            Node c = t.child;
            while(c != null){
                count = count + numNodiProfondi(c, h-1); 
                c = c.sibling;
            }
            return count;
        } 
    }


    /** Algoritmo che ritorna l'altezza dell'albero */
    public int high(Node t){
        if(t.child == null)
            return 0;
        else{
            Node c = t.child;
            int lv = 0;
            while(c != null){
                lv = Math.max(lv,high(c)); // per ogni fratello calcola il massimo tra le altezze
                c = c.sibling;
            } return 1 + lv;
        }
    }

    /** Algoritmo che ritorna l'altezza minimale dell'albero */
    public int minAlt(Node t){
        if(t.child == null){
            return 0;
        }
        else{
            int lv = Integer.MAX_VALUE;
            Node c = t.child;
            while(c != null){
                lv = Math.min(lv, minAlt(c));
                c = c.sibling;
            } return 1 + lv;
        }
    }

    /** Algoritmo che ritorna il grado dell'albero (# max di figli di un nodo) */
    public int degree(Node t){
        if(t.child == null)
            return 0;
        else{
            int maxDeg = degree(t.child);
            int rootDeg = 1;
            Node c = t.child.sibling;
            while(c != null){
                rootDeg++;
                maxDeg = Math.max(maxDeg,degree(c));
                c = c.sibling;
            } return Math.max(maxDeg, rootDeg);
        }
    }

    public int degree_alt(Node t){
        if(t.child == null)
            return 0;
        else{
            int rootdeg = 0;
            int maxdeg = 0;
            Node c = t.child;
            while(c != null){
                rootdeg++;
                maxdeg = Math.max(maxdeg,degree_alt(c));
                c = c.sibling;
            } return Math.max(maxdeg,rootdeg);
        }
    }

    /** Algoritmo che ritorna true se tutti i nodi interni hanno almeno n figli, false altrimenti */
    public boolean figliInterni(Node t, int n){
        if(t.child == null)
            return true; // caso t è foglia
        else{
            int count = 0;
            boolean check = true;
            Node c = t.child;
            while(c != null && check == true){
                count++;
                check = check && figliInterni(c, n);
                c = c.sibling;
            }
            return check && count >= n;
        }
    }

    /** Algoritmo che ritorna la larghezza dell'Albero */
    public int larg(Node t){
        Queue q = new LinkedList<Node>();
        int width = 0;
        int level = 0;
        int vertexOnLevel = 0;
        t.lv = 0;
        q.add(t);
        while(!q.isEmpty()){
            Node t1 = (Node)q.remove();
            if(level == t1.lv)
                vertexOnLevel++;
            else{
                width = Math.max(width, vertexOnLevel);
                vertexOnLevel = 1;
                level = t1.lv;
            }
            Node c = t1.child;
            while(c!= null){
                c.lv = t1.lv+1;
                q.add(c);
                c = c.sibling;
            }
        } return Math.max(width,vertexOnLevel); // Lo devo rimettere perché nell'ultimo nodo non aggiorna il max se no
    }

    /* Algoritmo che calcola se un albero è completo, si ricorda che un albero è completo se per ogni
    * k <= high(k) il livello k ha esattamente 2^k nodi */

    public boolean complete(Node t){
        int card = card(t);
        int high = high(t);
        return card == Math.pow(2,high + 1) - 1;
    }

    public boolean BFS_complete(Node t){ //PRE : t != null
        Queue q = new LinkedList<Node>();
        int card = 1;
        int level = 0;
        t.lv = 0;
        Node c = t.child;

        while(c != null){
            c.lv = 1;
            q.add(c);
            c = c.sibling;
        }

        while(!q.isEmpty()){
            Node t1 = (Node)q.remove();
            if(level == t1.lv) {
                card++;
                Node p = t1.child;
                while (p != null) {
                    p.lv++;
                    q.add(p);
                    p = p.sibling;
                }
            } else { // level < t.lv (è finita la visita del livello)
                if (card < Math.pow(2, level))
                    return false;
                else {
                    level++;
                    card = 1;
                }
            }
        }
        return card == Math.pow(2,level);
    }

    public int maxSumBranch(Node t){
        if(t == null)
            return 0;
        else if(t.child == null)
            return t.key;
        else{
            int ms = maxSumBranch(t.child);
            Node s = t.child.sibling;
            while(s != null){
                ms = Math.max(ms, maxSumBranch(s));
                s = s.sibling;
            }
            return ms + t.key;
        }
    }
    /** Algoritmo che ritorna true se l'elemento con chiave k è presente all'interno dell'albero
     * false altrimenti */
    public boolean find(Node t, int k){
        if(t == null)
            return false;
        if(t.key == k)
            return true;
        else{
            Node c = t.child;
            boolean check = false;
            while(c != null && !check){
                check = find(c,k);
                c = c.sibling;
            }
            return check;
        }
    }

    public int card1(Node t){
        if(t == null)
            return 0;
        else if(t.child == null)
            return 1;
        else {
            Node c = t.child;
            int res = 0;
            while(c!= null){
                res = res + card1(c);
                c = c.sibling;
            }
            return 1 + res;
        }
    }


    public int card(Node t){
        if(t == null)
            return 0;
        /*else if(t.child == null) Questo caso è superfluo ma utile per capire cosa succede quando un
            return 1; nodo non ha figli */
        else{
            int count = 1;
            Node c = t.child;
            while(c!= null){
                count += card(c);
                c = c.sibling;
            } return count;
        }
    }

    public int card_iter(Node t){
        Stack<Node> s = new Stack<Node>();
        int card = 0;
        s.push(t);
        while(!s.isEmpty()){
            Node t1 = s.pop();
            card++;
            Node c = t1.child;
            while(c!= null){
                s.push(c);
                c = c.sibling;
            }
        } return card;
    }

/** Calcola il nodo con etichetta massima all'interno dell'albero*/
    public int max(Node t, int m){
        Queue q = new LinkedList<Node>();
        int max = t.key;
        q.add(t);
        while (!q.isEmpty()){
            Node t1 = (Node)q.remove();
            max = Math.max(max,t1.key);
            Node c = t1.child;
            while(c != null){
                q.add(c);
                c = c.sibling;
            }
        } return max;
    }

    /** Algoritmo che ritorna il numero di nodi del ramo più corto dell'albero
     * Suggerimento: si noti che è molto simile all'algoritmo dell'altezza */
    public int shortest(Node t){
        if(t.child == null)
            return 1;
        else{
            int m = Integer.MAX_VALUE;
            Node c = t.child;
            while(c != null){
                m = Math.min(m,shortest(c));
                c = c.sibling;
            }
            return m + 1; // cliché quando si calcola l'altezza
        }
    }

    /** Algoritmo che restituisce il numero di nodi che si trovano a livello <= h */
        public int NNP(Node t, int h){
            if(t == null)
                return 0;
            else if(h == 0)
                return 1 + NNP(t.sibling,h);
            else
                return 1 + NNP(t.child,h-1) + NNP(t.sibling,h);
        }

        public int NNNP(Node t, int h){
            if(t == null)
                return 0;
            else{
                int n = NNNP(t.sibling,h) + NNNP(t.child,h-1);
                if(h >= 0) // l >= h se l > h ----> h >= -1
                    return n+0;
                else return n+1;
            }
        }



    public static void main(String[] args) {
        K_Tree k = new K_Tree(10);


        Node n1 = k.addChild(k.root, 2);
        Node n2 = k.addChild(k.root, 5);
        Node n3 = k.addChild(k.root, 8);

        Node n4 = k.addChild(n1, 4);
        Node n5 = k.addChild(n1, 6);
        Node n6 = k.addChild(n3, 3);
        Node n7 = k.addChild(n3, 12);

        Node n8 = k.addChild(n4, 4);
        Node n9 = k.addChild(n4, 6);
        Node n10 = k.addChild(n7, 3);
        Node n11 = k.addChild(n4, 12);

        Node n12 = k.addChild(n11, 12);

        System.out.println("Visita in profondità / metodo ricorsivo:");
        k.BFS(k.root);



        //System.out.println("card:" + k.high(k.root));
        System.out.println("altezza del ramo più corto:" + k.minAlt(k.root));
        System.out.println("numero nodi nel ramo piu corto:" + k.shortest(k.root));
        System.out.println("card" + k.card(k.root));
        System.out.println("numero nodi profondi <= h" + k.NNP(k.root,1));
        System.out.println("numero nodi profondi >= h" + k.NNNP(k.root,2));


    }
}