import java.util.Iterator;
import java.util.Vector;
import java.util.ConcurrentModificationException;

public class Iteratore<E> implements Iterator<DataCollection<E>> {

   
    int index;
    private Vector<DataCollection<E>> v1;
    private Vector<DataCollection<E>> v2;
    
 
    
    public Iteratore(Vector<DataCollection<E>> v){
    	v1=new Vector<DataCollection<E>>();
    	for(DataCollection<E> elem: v)
    		v1.add(elem);
    	this.v2=v;
    	index=0;
    	
    	
    	
    }
    
    public boolean hasNext(){
    	// per evitare (piu' o meno) modifiche concorrenti
    	if (!v2.equals(v1)) throw new ConcurrentModificationException();
    	return index < v1.size();
    }

    public DataCollection<E> next(){
    	// per evitare (piu' o meno) modifiche concorrenti
    	if (!v2.equals(v1)) throw new ConcurrentModificationException();
        return v1.elementAt(index++);
    }

    public  void remove(){
        throw new UnsupportedOperationException("remove");
    }
} 
