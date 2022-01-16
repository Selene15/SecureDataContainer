import java.util.Vector;
//Overview: Tipo modificabile che rappresenta un Utente caratterizzato da nomeId e password a cui è associato un insieme di dati

//IR(c): c.nomeId!=null && password!=null && c.dt!=null && (for all i| 0<=i<c.dt.size() => c.dt.get(i)!=null) 
//&& (for all i, j | 0<=i<j<c.dt.size() => c.dt.get(i).getDato()!= c.dt.get(j).getDato())
//AF(c): <c.nomeId, c.password, {c.dt.get(i).getDato(), c.dt.get(i).getDataS()> | 0<= i < c.dt.size()}>

public class User<E extends Comparable<E>> {
	private String nomeId;
	private String password;
	private Vector<DataCollection<E>> dt;


	//REQUIRES: nome!=null, passw!=null
	//THROWS: if nome==null || passw==null sollevo un'ecezione uncheked NullPointerException;
	//EFFECTS: inizializzo this al record con nomeId e password e creo il vettore vuoto di dati 
	public User(String nome, String passw) {
		if ((nome==null)||(passw==null)) throw new NullPointerException();
		this.nomeId=nome;
		this.password=passw;
		this.dt=new Vector<DataCollection<E>>();
	}
	
	//EFFECTS: restituisce il nome dell'utente
	public String getName() {
		return nomeId;
	}
	
	//EFFECTS: modifica la password di this
	//MODIFIES: this.password;
	public void setPassword(String passw) {
		this.password=passw;
	}
	
	//EFFECTS: restituisce la password dell'utente
	public String getPassword() {
		return password;
	}
	
	//EFFECTS:restituisce il vettore composto da nome del dato e l'insieme degli utenti condivisi
	public Vector<DataCollection<E>> getVdata(){
		return dt;
	}
	
	
	//EFFECTS: aggiunge il dato alla collezione 
	public void addDato(E data) {
		dt.add(new DataCollection<E>(data));
	}
	
	
	//EFFECTS: restituisco l'indice corrispondente al dato presente nel vettore, se il dato non è presente restituisco -1
	public int getDatoInd(E data) {
		for(int i=0;i<dt.size();i++) {
			DataCollection<E> d=dt.get(i);
			if(d.getDato().compareTo(data)==0)
				return i;
		}
		return -1;
	}
	//EFFECTS: restituisco true se riferito ad un certo utente identificato con l'indice i, other è presente nell'insieme di utenti condivisi, altirmenti restitusco false
	public boolean cercaUtCond(E data, String owner){
			for(int i=0;i<dt.size();i++) {
				DataCollection<E> d=dt.get(i);
				if(d.getDato().compareTo(data)==0){
					for(int k=0;k<d.getDataS().size();k++) {
						if(d.getDataS().get(k).equals(owner))
								return true;
					}
				}
			}
			return false;
			
		}
	
	//EFFECTS: aggiungo other alla lista degli utenti condivisi in riferimento a un dato identificato da ind
	public void addOwner(E data,String other) {
		for(int i=0;i<dt.size();i++) {
			DataCollection<E> d=dt.get(i);
			if(d.getDato().compareTo(data)==0) {
				d.getDataS().add(other);
			}
		}
		
		
	}
}

