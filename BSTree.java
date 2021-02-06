// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    //
    private BSTree getRoot(){                          //initial_verified
        if(this==null){
            return null;
        }
        if(this.parent==null){
            return this.right;
        }
        BSTree node=this;
        while(node.parent.parent!=null){
            node=node.parent;
        }
        return node;
    }


    public BSTree Insert(int address,int size,int key)
    {
        BSTree temp=new BSTree(address,size,key);
        BSTree node=this.getRoot();
        if(node==null){
            this.right=temp;
            temp.parent=this;
            return temp;
        }
        while(node!=null){
            if(key==node.key){
                if(address<=node.address){
                    if(node.left==null){
                        node.left=temp;
                        temp.parent=node;
                        return temp;
                    }
                    node=node.left;
                }
                else{
                    if(node.right==null){
                        node.right=temp;
                        temp.parent=node;
                        return temp;
                    }
                    node=node.right;
                }
            }
            else if(key<node.key){
                if(node.left==null){
                    node.left=temp;
                    temp.parent=node;
                    return temp;
                }
                node=node.left;
            }
            else{
                if(node.right==null){
                    node.right=temp;
                    temp.parent=node;
                    return temp;
                }
                node=node.right;
            }
        }
        return null;      

    }

    private void DeleteCase1(BSTree node){                           //leaf case
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
    private void DeleteCase2(BSTree node){                          //if one child
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

    private void swapval(BSTree n1, BSTree n2){
        int temp_a=n1.address; int temp_s=n1.size; int temp_k=n1.key;
        n1.address=n2.address; n1.key=n2.key; n1.size=n2.size;
        n2.address=temp_a; n2.key=temp_k; n2.size=temp_s;
        return;
    }

    public boolean Delete(Dictionary e)                                 //initial_verified
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
        BSTree node=this.getRoot();
        if(node==null){
            return false;
        }
        while(node!=null){
            if(node.key==e.key && node.address==e.address && node.size==e.size){
                if(node.left==null && node.right==null){
                    DeleteCase1(node);
                }
                else{
                    if(node.left!=null && node.right!=null){
                        BSTree suc=node.right;
                        while(suc.left!=null){
                            suc=suc.left;
                        }
                        node.key=suc.key;node.address=suc.address;node.size=suc.size;
                        if(suc.left==null && suc.right==null){
                            DeleteCase1(suc);
                        }                    
                        else{
                            DeleteCase2(suc);
                        }
                    }
                    else{
                        DeleteCase2(node);
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

    public BSTree Find(int key, boolean exact)
    {   

        BSTree node=this.getRoot();
        if(node==null){
            return null;
        }
        if(exact){
            BSTree ans=null;
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
            BSTree ans=null;
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

    public BSTree getFirst()                      //initial_verified
    { 
        if(this==null){
            return null;   
        }
        BSTree current=this.getRoot();
        if(current==null){
            return null;
        }
        while(current.left!=null){
            current=current.left;
        }
        return current;
    }

    public BSTree getNext()                       //verified
    {   
        if(this==null || this.parent==null){
            return null;
        }
        if(this.right==null){
            BSTree p = this.parent;
            BSTree curr=this;
            while (p.parent != null && p.right==curr) { 
                curr = p; 
                p = p.parent; 
            }
            if(p.parent==null){
                return null;
            }
            return p;
        }
        BSTree current=this.right;
        while(current.left!=null){
            current=current.left;
        } 
        return current;
    }

    private boolean checkhead(BSTree node){
        BSTree current=node;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.key==-1 && current.size==-1 && current.address==-1 && current.left==null){
            return true;
        }
        return false;
    }

    private boolean checkBST(BSTree node){
        BSTree current=node;
        while(current.parent!=null){
            current=current.parent;
        }
        if(current.right==null){
            return true;
        }
        return check2(current.right) && checkBSTrec(current.right,Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE);
    }

    private boolean checkBSTrec(BSTree node, int min_key, int max_key, int min_address, int max_address) 
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
        return (checkBSTrec(node.left, min_key, node.key, min_address, node.address-1) && checkBSTrec(node.right, node.key, max_key, node.address+1, max_address)); 
    }

    private boolean check1(BSTree node){
        BSTree slow_p=node, fast_p=node;
        while (slow_p!=null && fast_p!=null && fast_p.parent!=null) { 
            slow_p = slow_p.parent; 
            fast_p = fast_p.parent.parent; 
            if (slow_p == fast_p) { 
                return false;
            } 
        }
        return true;
    }

    private boolean check2(BSTree node){
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

    public boolean sanity()
    { 
        if(!check1(this) && !checkBST(this) && !checkhead(this)){
            return false;
        }
        return true;
    }

}