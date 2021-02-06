// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)                  //verified
    {
        //
        if(this.next==null){                                              //when insert is called at tailsentinel
            this.next=new A1List(-1,-1,-1);
            this.next.prev=this;
            this.key=key; this.address=address; this.size=size;
            return this;
        }
        A1List temp = new A1List(address,size,key);                       //inserting next to current
        this.next.prev=temp;
        temp.next=this.next;
        this.next=temp;
        temp.prev=this;
        return temp;
        //
    }

    public boolean Delete(Dictionary d)                                   //verified
    {
        //
        if(this.prev!=null && this.next!=null && this.key==d.key && this.address==d.address && this.size==d.size){     //if delete called on a and we have to delete a
            if(this.prev.prev==null && this.next.next==null){
                int temp_address=this.address; int temp_size=this.size; int temp_key=this.key;
                this.key=this.next.key; this.address=this.next.address; this.size=this.next.size;
                this.next.key=temp_key; this.next.address=temp_address; this.next.size=temp_size;
                this.next.prev=null;
                this.next=null;
            }
            else{
                if(this.next.next!=null){
                    int temp_address=this.address; int temp_size=this.size; int temp_key=this.key;
                    this.key=this.next.key; this.address=this.next.address; this.size=this.next.size;
                    this.next.key=temp_key; this.next.address=temp_address; this.next.size=temp_size;
                    A1List current_=this.next;
                    current_.prev.next=current_.next;
                    current_.next.prev=current_.prev;
                    current_.prev=null;
                    current_.next=null;
                }
                else{
                    int temp_address=this.address; int temp_size=this.size; int temp_key=this.key;
                    this.key=this.prev.key; this.address=this.prev.address; this.size=this.prev.size;
                    this.prev.key=temp_key; this.prev.address=temp_address; this.prev.size=temp_size;
                    A1List current_=this.prev;
                    current_.prev.next=current_.next;
                    current_.next.prev=current_.prev;
                    current_.prev=null;
                    current_.next=null;
                }
            }
            return true;
        }
        //
        A1List current=this;                                    //other cases
        if(current.next==null){
            current=current.prev;
        }
        while(current.prev!=null){
            if(current.key==d.key && current.address==d.address && current.size==d.size){
                current.prev.next=current.next;
                current.next.prev=current.prev;
                current.prev=null;
                current.next=null;
                return true;
            }
            current=current.prev;
        }
        A1List current1=this;
        if(current1.prev==null){
            current1=current1.next;
        }
        while(current1.next!=null){
            if(current1.key==d.key && current1.address==d.address && current1.size==d.size){
                current1.prev.next=current1.next;
                current1.next.prev=current1.prev;
                current1.prev=null;
                current1.next=null;
                return true;
            }
            current1=current1.next;
        }
        return false;
        //
    }

    public A1List Find(int k, boolean exact)                              //verified
    { 
        //
        A1List current=this.getFirst();
        if(current==null){
            return null;
        }
        else if(exact){
            while(current.next!=null){
                if(current.key==k){
                    return current;
                }
                current=current.next;
            }
            return null;
        }
        else{
            while(current.next!=null){
                if(current.key>=k){
                    return current;
                }
                current=current.next;
            }
            return null;
        }
        //
    }

    public A1List getFirst()                         //verified
    {
        //
        A1List current=this;
        while(current.prev!=null){
            current=current.prev;
        }
        if(current.next.next==null){
            return null;
        }
        return current.next;
        //
    }
    
    public A1List getNext()                         //verified
    {
        //
        if(this.next==null || this.next.next==null){
            return null;
        }
        return this.next;
        //
    }

    private boolean sanitycircular(A1List node){                       //check the list is circular or not
        A1List slow_p=node, fast_p=node;
        while (slow_p!=null && fast_p!=null && fast_p.next!=null) { 
            slow_p = slow_p.next; 
            fast_p = fast_p.next.next; 
            if (slow_p == fast_p) { 
                return true;
            } 
        }
        slow_p=node; fast_p=node;
        while (slow_p!= null && fast_p!= null && fast_p.prev!= null) { 
            slow_p = slow_p.prev; 
            fast_p = fast_p.prev.prev; 
            if (slow_p == fast_p) { 
                return true;
            } 
        }
        return false;
    }

    private boolean sanityHeadTail(A1List node){
        A1List current=this;
        while(current.prev!=null){
            if(current.prev.next!=current){
                return true;
            }
            current=current.prev;
        }
        if(current.key!=-1 ||current.address!=-1 || current.size!=-1){
            return false;
        }
        current=this;
        while(current.next!=null){
            if(current.next.prev!=current){
                return true;
            }
            current=current.next;
        }
        if(current.key!=-1 ||current.address!=-1 || current.size!=-1){
            return false;
        }
        return true;

    }

    public boolean sanity()
    {
        //
        if(this==null || sanitycircular(this) || !sanityHeadTail(this)){
            return false;
        }
        //
        return true;
    }

}


