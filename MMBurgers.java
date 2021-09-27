import java.io.*;
import java.lang.*;
import java.util.*;

class Node2 {
  int key, height, burger, t1, que, t2, t3;
  String state;
  Node2 left, right;
  Node2(int d, int b,  int t, String s, int q, int initial_t, int final_t ){
    key = d;
    height = 1;
    burger = b;
    t1 = t;
    state = s;
    que = q;
    t2 = initial_t;
    t3 = final_t; 
  }
}

class Node {
    int key;
    Queue<Node1> q = new LinkedList<>();
    }

class Node1 {
    int id;
    int patty;
    int timetaken;
    }

public class MMBurgers implements MMBurgersInterface {
    static Node2 root;
    static int K;
    static int M;
    static int time=0;
    static Node[] heap;
    static int size=1;
    static int grill = 0;
    static int order = 0;
    static int query = 0;
    static Queue<Node1>[] queues;
    static Queue<Node1> burgerqueue = new LinkedList<>();
    static int b[][];
    static float avg = 0;
    static float customers = 0;

    int height(Node2 N) {
        if (N == null)
            return 0;
        return N.height;
    }

    Node2 rotateRight(Node2 x){
        Node2 y = x.left;
        Node2 z = y.right;
        y.right = x;
        x.left = z;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    Node2 rotateLeft(Node2 x){
        Node2 y = x.right;
        Node2 z = y.left;
        y.left = x;
        x.right = z;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    int getBalance(Node2 N){
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    Node2 insert1(Node2 node, int key, int b, int t, String s, int q, int initial_t, int final_t){
        if (node == null)
            return (new Node2(key, b, t, s, q, initial_t, final_t));
        if (key < node.key)
            node.left = insert1(node.left, key, b, t, s, q,initial_t, final_t);
        else if (key > node.key)
            node.right = insert1(node.right, key, b, t, s, q,initial_t, final_t);
        else
            return node;
        node.height = 1 + Math.max(height(node.left),
                            height(node.right));
        int balance = getBalance(node);
        if (balance > 1 && key < node.left.key)
            return rotateRight(node);
        if (balance > 1 && key > node.left.key) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && key < node.right.key) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        if (balance < -1 && key > node.right.key)
            return rotateLeft(node);
        return node;
    }

    Node2 find(int key) {
        Node2 current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            if(current.key<key){
                current = current.right;
            }
            else{
                current = current.left;
            }
        }
        return current;
    }

    static Node newNode(int key, Queue<Node1> q)
    {
        Node temp = new Node();
        temp.key = key;
        temp.q = q;
        return temp;
    }

    static Node1 newNode1(int id, int patty, int ti)
    {
        Node1 temp = new Node1();
        temp.id = id;
        temp.patty = patty;
        temp.timetaken = ti;
        return temp;
    }

    public boolean isEmpty(){
        if(query>0){
            return false;}
        else{
            return true;}
        } 
    
    public void insert(Node x) {
        int idx = size;
        size++;
        heap[idx] = x;
    }

    

    public void swap(int a, int b) {
        Node temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
        }

    public void display(){
        for(int i=1;i<=K;i++){
            System.out.print(heap[i].key+" ");
            System.out.print(heap[i].q.size()+" ");
        }
        System.out.println("");
    }

    public void min_heapify (Node Arr[ ] , int i, int N)
    {
        MMBurgers obj = new MMBurgers();
        int left  = 2*i;
        int right = (2*i)+1;
        int smallest = i;
        if(left <= N && Arr[left].q.size() < Arr[i].q.size()){
                smallest = left;
            }
        if(left<=N && Arr[left].q.size() == Arr[i].q.size()){
            int y = Math.min(Arr[left].key, Arr[i].key);
            if(y==Arr[left].key)
                smallest = left;}
        if(right <= N && Arr[right].q.size() < Arr[i].q.size()){
            int y = Math.min(Arr[smallest].key, Arr[right].key);
            if(y == Arr[right].key)
                smallest = right;
        }
        else if(right<=N && Arr[right].q.size() == Arr[i].q.size()){
            int y = Math.min(Arr[right].key, Arr[i].key);
            if(y==Arr[right].key)
                smallest = right;}
        if(smallest != i)
        {
            swap(i, smallest);
            min_heapify (heap, smallest,N);
        } 
    }

    public void setK(int k) throws IllegalNumberException{
        MMBurgers obj = new MMBurgers();
        if(k>0){
            K = k;
            heap = new Node[K+1];
            queues = new Queue[K];
            for(int i=1;i<K+1;i++){
                queues[i-1] = new LinkedList<>();
            }
            for(int i=1;i<K+1;i++){
                Node root2 = newNode(i, queues[i-1]);
                obj.insert(root2);
                }
            }
        else
            throw new IllegalNumberException("Not valid number.");  
    }   
    
    public void setM(int m) throws IllegalNumberException{
        if(m>0){
            M = m;
            b = new int[M][2];
            for(int j=0;j<M;j++){
                b[j][0]=0;
                b[j][1]=0;
            }
        }
        else
            throw new IllegalNumberException("Not valid number.");

    } 

    static void billing(int t){
            MMBurgers obj = new MMBurgers();
            for(int j=K;j>0;j--){
                if(queues[j-1].size()!=0){
                queues[j-1].peek().timetaken = queues[j-1].peek().timetaken+1;
                if(queues[j-1].peek().timetaken==j){
                    Node1 x = queues[j-1].remove();
                    Node2 z= obj.find(x.id);
                    z.state = "B";
                    order = order + z.burger;
                    burgerqueue.add(x);
                    }
                }
            }
    }

    static void burgerline(int t){
        MMBurgers obj = new MMBurgers();
        for(int j=0;j<M;j++){
            if(b[j][0]!=0){
                b[j][1]=b[j][1]+1;
                if(b[j][1]==10){
                    Node2 x = obj.find(b[j][0]);
                    x.burger--;
                    if(x.burger==0)
                    {
                        x.t3 = t;
                        avg = avg+1+x.t3-x.t2;
                        query--;
                    }
                    b[j][1]=0;
                    b[j][0]=0;
                    grill--;
                }
            }
        }
        while(grill!=M&&burgerqueue.size()>0){
            int p = burgerqueue.peek().patty;
            int a = M-grill;
            if(p<=a){
                grill=grill+p;
                order=order-p;
                for(int j=0;j<M;j++){
                    if(p>0){
                        if(b[j][0]==0){
                            b[j][0]=burgerqueue.peek().id;
                            b[j][1]=0;
                            p--;
                        }
                    }
                }
                burgerqueue.remove();
            }
            else{
                grill=grill+a;
                order=order-a;
                burgerqueue.peek().patty = burgerqueue.peek().patty-a;
                for(int j=0;j<M;j++){
                    if(a>0){
                        if(b[j][0]==0){
                            b[j][0]=burgerqueue.peek().id;
                            b[j][1]=0;
                            a--;
                        }
                    }
                }
            }
        }
    
   }

    public void advanceTime(int t) throws IllegalNumberException{
        MMBurgers obj = new MMBurgers();
        if(t>=time){
            for(int i=time+1;i<=t;i++){
                obj.billing(i);
                obj.burgerline(i);
                for( int j = K/2 ; j >= 1 ; j--){
                    obj.min_heapify(heap , j , K);
                }
            }
            time = t;
        }
        else
            throw new IllegalNumberException("Illegal time");
        } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        MMBurgers obj = new MMBurgers();
        if(numb>0&&t>=time){
            obj.advanceTime(t);
            for( int i = K/2 ; i >= 1 ; i--){
                obj.min_heapify(heap , i , K);
            }
            Node y = heap[1];
            Node1 w = newNode1(id,numb,0);
            obj.root = obj.insert1(root, id, numb, t, "A", y.key, t, 0);
            query++;
            heap[1].q.add(w);
            customers++;}
        else
            throw new IllegalNumberException("No");
        } 

    public int customerState(int id, int t) throws IllegalNumberException{
         MMBurgers obj = new MMBurgers();
         if(t>=time){
            int sta = 0;
            obj.advanceTime(t);
            Node2 x = obj.find(id);
            if(x!=null){
            String y = x.state;
            if(y=="A")
                sta = x.que;
            else if(y=="B"&&x.burger>0)
                sta = K+1;
            else
                sta = K+2;
         }
            return sta;
         }
         else
            throw new IllegalNumberException("No");
    } 

    public int griddleState(int t) throws IllegalNumberException{
        MMBurgers obj = new MMBurgers();
         if(t>=time){
            obj.advanceTime(t);
            return grill;
         }
         else
            throw new IllegalNumberException("NO");
        } 

    public int griddleWait(int t) throws IllegalNumberException{
        MMBurgers obj = new MMBurgers();
         if(t>=time){
            obj.advanceTime(t);
            return order;
         }
         else
            throw new IllegalNumberException("NO");
        } 

    public int customerWaitTime(int id) throws IllegalNumberException{
            MMBurgers obj = new MMBurgers();
            Node2 x = obj.find(id);
            if(x!=null){
                int y = x.t3-x.t2+1;
                return y;
            }
            else
                throw new IllegalNumberException("NO");
        } 

    public float avgWaitTime(){
        float x = avg/customers;
        return x;
        } 

    
}
