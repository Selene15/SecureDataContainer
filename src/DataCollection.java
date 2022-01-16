import java.util.Vector;
//Overview: Tipo modificabile che rappresente uno specifico dato caratterizzato da una lista di utenti condivisi
//IR(c)= c.data!=null && c.ds!=null && (for all i| 0<=i<c.ds.size() => c.ds.get(i)!=null) 
//AF(c)= <c.data,{<c.ds.get(i)> | 0<= i < c.ds.size()}>


public class DataCollection<E> {
	private E data;
	private Vector<String> ds;
	
	//EFFECTS: inizializzo this al record con data e creo un vettore di stringhe per gli utenti condivisi
	public DataCollection(E data){
		this.data=data;
		this.ds=new Vector<String>();
	}
	

	//EFFECTS: restituisce il dato
	public E getDato() {
		return data;
	}
	
	//EFFECTS: restituisce il vettore di stringhe degli utenti condivisi
	public Vector<String> getDataS(){
		return ds;
	}
}
