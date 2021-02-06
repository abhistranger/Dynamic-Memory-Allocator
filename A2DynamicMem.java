// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 


    public int Allocate(int blockSize) {
        if(blockSize<=0){
            return -1;
        }
        Dictionary temp=freeBlk.Find(blockSize,false);
        if(temp==null){
            return -1;
        }
        else{
            int address=temp.address; int size=temp.size; int key=temp.key;
            freeBlk.Delete(temp);
            if(key>blockSize){
                freeBlk.Insert(address+blockSize,size-blockSize,size-blockSize);
                allocBlk.Insert(address,blockSize,address);
            }
            else{
                allocBlk.Insert(address,size,address);
            }
            //freeBlk.Delete(temp);
            return address;
        }
    }

    public int Free(int startAddr) {
        if(startAddr<0){
            return -1;
        }
        Dictionary temp=allocBlk.Find(startAddr,true);
        if(temp==null){
            return -1;
        }
        int address=temp.address; int size=temp.size; int key=temp.key;
        freeBlk.Insert(address,size,size);
        allocBlk.Delete(temp);
        return 0;
    }

    public void Defragment() {
        Dictionary freeBlk2;
        if(type==2){
            freeBlk2=new BSTree();
        }
        else{
            freeBlk2=new AVLTree();
        }
        Dictionary current=freeBlk;
        for(current=current.getFirst();current!=null;current=current.getNext()){
            freeBlk2.Insert(current.address,current.size,current.address);
        }
        Dictionary t=freeBlk2.getFirst();
        if(t==null){
            return;
        }
        while(t.getNext()!=null){
        //for(t=t.getFirst();t!=null;t=t.getNext()){
            if(t.key+t.size==t.getNext().key){
                int temp_address=t.key; int temp_size=t.size+t.getNext().size;
                Dictionary t1,t2;
                if(type==2){
                    t1= new BSTree(t.address,t.size,t.size);
                    t2= new BSTree(t.getNext().address,t.getNext().size,t.getNext().size);
                }
                else{
                    t1= new AVLTree(t.address,t.size,t.size);
                    t2= new AVLTree(t.getNext().address,t.getNext().size,t.getNext().size);
                }
                Dictionary t_= t.getNext();
                freeBlk.Delete(t1);
                freeBlk.Delete(t2);
                freeBlk2.Delete(t);
                freeBlk2.Delete(t_);
                freeBlk.Insert(temp_address,temp_size,temp_size);
                t=freeBlk2.Insert(temp_address,temp_size,temp_address);
            }
            else{
                t=t.getNext();
            }
        }
        current=freeBlk2;
        for(current=current.getFirst();current!=null;current=current.getNext()){
            freeBlk2.Delete(current);
        }
        return ;
    }
}