// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    

    private AVLTree getRoot(){                   
        if(this==null){
            return null;
        }
        if(this.parent==null){
            return this.right;
        }
        AVLTree node=this;
        while(node.parent.parent!=null){
            node=node.parent;
        }
        return node;
    }

    private int getHeight(AVLTree node) { 
        if(node == null){
            return 0;
        }
        return node.height; 
    }

    private int max(int a, int b){
        if(a>b){
            return a;
        }
        return b;
    }

    private void updateHeight(AVLTree node){
        AVLTree t=node;
        while(t.parent!=null){
            t.height=1+max(getHeight(t.left),getHeight(t.right));
            t=t.parent;
        }
        return;
    }

    public AVLTree Insert(int address, int size, int key) 
    { 
        
        AVLTree temp=new AVLTree(address,size,key);
        AVLTree node=this.getRoot();
        if(node==null){
            this.right=temp;
            temp.parent=this;
            temp.height=1;
            return temp;
        }
        while(node!=null){
            if(key==node.key){
                if(address<=node.address){
                    if(node.left==null){
                        node.left=temp;
                        temp.parent=node;
                        //temp.height=1;
                        updateHeight(temp);
                        checkbalance(temp);
                        return temp;
                    }
                    node=node.left;
                }
                else{
                    if(node.right==null){
                        node.right=temp;
                        temp.parent=node;
                        //temp.height=1;
                        updateHeight(temp);
                        checkbalance(temp);
                        return temp;
                    }
                    node=node.right;
                }
            }
            else if(key<node.key){
                if(node.left==null){
                    node.left=temp;
                    temp.parent=node;
                    //temp.height=1;
                    updateHeight(temp);
                    checkbalance(temp);
                    return temp;
                }
                node=node.left;
            }
            else{
                if(node.right==null){
                    node.right=temp;
                    temp.parent=node;
                    //temp.height=1;
                    updateHeight(temp);
                    checkbalance(temp);
                    return temp;
                }
                node=node.right;
            }
        }
        return null;
    }

    private void checkbalance(AVLTree node){
        //System.out.println("checkbalance");
        if(getHeight(node.left)-getHeight(node.right)>1 || getHeight(node.left)-getHeight(node.right)<-1){
            //System.out.println("checkbalance2");
            rebalance(node);
            //return;
        }
        if(node.parent.parent==null){
            return;
        }
        checkbalance(node.parent);
    }

    public void rebalance(AVLTree node){
        //System.out.println("rebalance");
        if(getHeight(node.left)>=getHeight(node.right)){
            if(getHeight(node.left.left)>=getHeight(node.left.right)){
                node=rightrotation(node);
                updateHeight(node);
            }
            else{
                node=leftrightrotation(node);
                updateHeight(node);
            }
        }
        else{
            if(getHeight(node.right.right)>=getHeight(node.right.left)){
                node=leftrotation(node);
                updateHeight(node);
            }
            else{
                node=rightleftrotation(node);
                updateHeight(node);
            }
        }
    }

    public AVLTree leftrotation(AVLTree node){
        //System.out.println("leftrotation");
        AVLTree temp=node.right;
        if(node.parent!=null && node.parent.left==node){
            node.parent.left=temp;
        }
        if(node.parent!=null && node.parent.right==node){
            node.parent.right=temp;
        }
        temp.parent=node.parent;
        node.right=temp.left;
        if(temp.left!=null){
            temp.left.parent=node;
        }
        temp.left=node;
        node.parent=temp;
        node.height=1+max(getHeight( node.left ),getHeight( node.right ) );
        temp.height=1+max(getHeight( temp.left ), getHeight( temp.right ) );
        return temp;
    }

    public AVLTree rightrotation(AVLTree node){
        AVLTree temp=node.left;
        if(node.parent!=null && node.parent.left==node){
            node.parent.left=temp;
        }
        if(node.parent!=null && node.parent.right==node){
            node.parent.right=temp;
        }
        temp.parent=node.parent;
        node.left=temp.right;
        if(temp.right!=null){
            temp.right.parent=node;
        }  
        temp.right=node;     
        node.parent=temp;
        node.height=1+max(getHeight( node.left ),getHeight( node.right ) );
        temp.height=1+max(getHeight( temp.left ), getHeight( temp.right ) );;
        return temp;
    }

    public AVLTree leftrightrotation(AVLTree node){
        node.left=leftrotation(node.left);
        return rightrotation(node);
    }

    public AVLTree rightleftrotation(AVLTree node){
        node.right=rightrotation(node.right);
        return leftrotation(node);
    }

    private void swapval(AVLTree n1, AVLTree n2){
        int temp_a=n1.address; int temp_s=n1.size; int temp_k=n1.key; //int temp_height=n1.height;
        n1.address=n2.address; n1.key=n2.key; n1.size=n2.size; //n1.height=n2.height;
        n2.address=temp_a; n2.key=temp_k; n2.size=temp_s; //n2.height=temp_height;
        return;
    }

    private void DeleteCase1(AVLTree node){                           //leaf case
        if(node.parent.left==node){
            node.parent.left=null;
            node.parent=null;
        }
        else{
            node.parent.right=null;
            node.parent=null;
        }
        return;
    }
    private void DeleteCase2(AVLTree node){                          //if one child
        if(node.parent.left==node){
            if(node.left==null){
                node.parent.left=node.right;
                node.right.parent=node.parent;
                node.parent=null;
                node.right=null;
            }
            else{
                node.parent.left=node.left;
                node.left.parent=node.parent;
                node.parent=null;
                node.left=null;
            }
        }
        else{
            if(node.left==null){
                node.parent.right=node.right;
                node.right.parent=node.parent;
                node.parent=null;
                node.right=null;
            }
            else{
                node.parent.right=node.left;
                node.left.parent=node.parent;
                node.parent=null;
                node.left=null;
            }
        }
    }

    public boolean Delete(Dictionary e)
    {
        if(this.key==e.key && this.address==e.address && this.size==e.size && (this.left==null||this.right==null)){
            if(this.left==null && this.right==null){
                if(this.parent.parent==null){
                    swapval(this,this.parent);
                    this.parent=null;
                }
                else{
                    swapval(this,this.parent);
                    this.Delete(this.parent);

                }
            }
            else{
                if(this.left!=null){
                    swapval(this,this.left);
                    this.Delete(this.left);
                }
                else{
                    swapval(this,this.right);
                    this.Delete(this.right);

                }
            }
            return true;
        }
        AVLTree node=this.getRoot();
        if(node==null){
            return false;
        }
        while(node!=null){
            if(node.key==e.key && node.address==e.address && node.size==e.size){
                if(node.left==null && node.right==null){
                    AVLTree t=node.parent;
                    DeleteCase1(node);
                    updateHeight(t);
                    if(t.parent!=null){
                        checkbalance(t);  
                    } 
                }
                else{
                    if(node.left!=null && node.right!=null){
                        AVLTree t=node.right;
                        while(t.left!=null){
                            t=t.left;
                        }
                        if(this.key==t.key && this.address==t.address && this.size==t.size){
                            t=node.left;
                            while(t.right!=null){
                                t=t.right;
                            }
                        }
                        node.key=t.key;node.address=t.address;node.size=t.size;
                        if(t.left==null && t.right==null){
                            AVLTree t_parent=t.parent;
                            DeleteCase1(t);
                            updateHeight(t_parent);
                            if(t_parent.parent!=null){
                                checkbalance(t_parent);  
                            }
                        }                    
                        else{
                            AVLTree t_parent=t.parent;
                            DeleteCase2(t);
                            updateHeight(t_parent);
                            if(t_parent.parent!=null){
                                checkbalance(t_parent);  
                            }
                        }
                    }
                    else{
                        AVLTree t=node.parent;
                        DeleteCase2(node);
                        updateHeight(t);
                        if(t.parent!=null){
                            checkbalance(t);  
                        }
                    }
                }
                return true;
            }
            else if(e.key==node.key){
                if(e.address>node.address){
                    node=node.right;           
                }
                else{
                    node=node.left;
                }
            }
            else if(e.key<node.key){
                node=node.left;
            }
            else{
                node=node.right;
            }
        }
        return false;
    }

    public AVLTree getFirst()                      //verified
    { 
        if(this==null){
            return null;   
        }
        AVLTree current=this.getRoot();
        if(current==null){
            return null;
        }
        while(current.left!=null){
            current=current.left;
        }
        return current;
    }

    public AVLTree getNext()                       //verified
    {   
        if(this==null || this.parent==null){
            return null;
        }
        if(this.right==null){
            AVLTree p = this.parent;
            AVLTree curr=this;
            while (p.parent != null && p.right==curr) { 
                curr = p; 
                p = p.parent; 
            }
            if(p.parent==null){
                return null;
            }
            return p;
        }
        AVLTree current=this.right;
        while(current.left!=null){
            current=current.left;
        } 
        return current;
    }

    public AVLTree Find(int key, boolean exact)
    {   

        AVLTree node=this.getRoot();
        if(node==null){
            return null;
        }
        if(exact){
            AVLTree ans=null;
            while(node!=null){
                if(node.key==key){
                    ans=node;
                    node=node.left;
                }
                else if(key<node.key){
                    node=node.left;
                }
                else{
                    node=node.right;
                }
            }
            return ans;
        }
        else{                                                     // check_again
            AVLTree ans=null;
            while(node!=null){
                if(node.key==key){
                    if(ans==null || ans.key>node.key){
                        ans=node;
                    }
                    else if(ans.key==node.key && ans.address>node.address){
                        ans=node;
                    }
                    node=node.left;
                }
                else if(key<node.key){
                    if(ans==null || ans.key>node.key){
                        ans=node;
                    }
                    else if(ans.key==node.key && ans.address>node.address){
                        ans=node;
                    }
                    node=node.left;
                }
                else{
                    node=node.right;
                }
            }
            return ans;
        }
    }

    private boolean checkhead(AVLTree node){
        AVLTree current=node;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.key==-1 && current.size==-1 && current.address==-1 && current.left==null){
            return true;
        }
        return false;
    }

    private boolean checkAVL(AVLTree node){
        AVLTree current=node;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return true;
        }
        return check2(current.right) && checkAVLrec(current.right,Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    private boolean checkAVLrec(AVLTree node, int min_key, int max_key, int min_address, int max_address) 
    {
        if (node == null){
            return true; 
        }
        if(node.key==min_key){
            if(node.address< min_address){
                return false;
            }
        }
        if(node.key==max_key){
            if(node.address>max_address){
                return false;
            }
        }
        if(node.key<min_key || node.key > max_key){
            return false;
        } 
        return (checkAVLrec(node.left, min_key, node.key, min_address, node.address-1) && checkAVLrec(node.right, node.key, max_key, node.address+1, max_address)); 
    }

    private boolean check1(AVLTree node){
        AVLTree slow_p=node, fast_p=node;
        while (slow_p!=null && fast_p!=null && fast_p.parent!=null) { 
            slow_p = slow_p.parent; 
            fast_p = fast_p.parent.parent; 
            if (slow_p == fast_p) { 
                return false;
            } 
        }
        return true;
    }

    private boolean check2(AVLTree node){
        if(node==null){
            return true;
        }
        else if(node.left==null && node.right==null){
            return true;
        }
        else{
            if(node.left!=null){
                if(node.left.parent!=node){
                    return false;
                }
            }
            if(node.right!=null){
                if(node.right.parent!=node){
                    return false;
                }
            }
        }
        return (check2(node.left) && check2(node.right));
    }

    private int checkheight(AVLTree root){
        if(root==null){
            return 0;
        }
        int left_height=checkheight(root.left);
        if(left_height==-1){
            return -1;
        }
        int right_height=checkheight(root.right);
        if(right_height==-1){
            return -1;
        }
        if((left_height-right_height)>1 || (left_height-right_height)<-1){
            return -1;
        }
        if(1+max(left_height,right_height)!=root.height){
            return -1;
        }
        else{
            return 1+max(left_height,right_height);
        }
    }

    private boolean isbalanced(AVLTree node){
        AVLTree current=node;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return true;
        }
        if(checkheight(current.right)==-1){
            return false;
        }
        return true;
    }

    public boolean sanity()
    { 
        if(!check1(this) || !checkAVL(this) || !checkhead(this) || !isbalanced(this)){
            return false;
        }
        return true;
    }
}


