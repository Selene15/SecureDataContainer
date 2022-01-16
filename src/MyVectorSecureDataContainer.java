import java.util.Vector;
/*Overview:Tipo di dato modificabile che estende l'interfaccia SecureDataContainer e utilizza un vettore 
* per rappresentare un insieme di utenti a cui associa un certo numero di elementi di tipoE
* AF(c): {<c.users.get(i).getName(), c.users.get(i).getPassw(), c.users.get(i).getVdata()< t.c 0<=i<=c.dim}
* IR(c): c.dim>=0 && c.dim=c.users.size() &&  c.users!= && (for all i | 0<=i<=c.dim => c.users.get(i)=!null)
* && (for all i,j | 0<=i<j<=c.dim => c.users.get(i).getName!= c.users.get(j).getName) && c.users.getVdata!=null
 */

public class MyVectorSecureDataContainer <E extends Comparable<E>> implements SecureDataContainer<E>{
	
	private Vector<User<E>> users;
	private int dim;
	//EFFECTS:inizializza la collezione con un vettore di User vuoto
	public  MyVectorSecureDataContainer() {
		this.users=new Vector<User<E>>();
		dim=0;
	}
	
	
	public void createUser(String id, String passw) throws NullPointerException, DuplicateIdException{
		if(id==null||passw==null) throw new NullPointerException();
		
		int i=trovaNome(id);
		if (i!=-1 )throw new DuplicateIdException();
		users.add(new User<E>(id,passw));
		dim++;
	}
	
	
	public int getSize(String owner, String passw) throws NullPointerException, IllegalOwnerException {
		if(owner==null||passw==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else{
			User<E> ut=users.get(ind);
				return ut.getVdata().size();
			}
	} 
		

	public boolean put (String owner, String passw, E data) throws NullPointerException, IllegalOwnerException {
		if (owner==null||passw==null||data==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException(); {
			User<E> ut=users.get(ind);
			if(ut.getDatoInd(data)==-1) {
				ut.addDato(data);
				return true;
			}
			else return false;
		}
		
	}
	

	public E get(String owner, String passw, E data) throws NullPointerException, IllegalOwnerException{
		if (owner==null || passw==null || data==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else{
			
			User<E> ut1=users.get(ind);
			if(ut1.getDatoInd(data)!=-1) {
				return data;
			}
			else {
				for(int i=0;i<users.size();i++) {
					User<E> ut2=users.get(i);
					if(ut1.getName()!=ut2.getName()) {
						if(ut2.getDatoInd(data)!=-1) {
							if(ut2.cercaUtCond(data, owner));
								return data;
						}
					}
				}
				return null;
			}
		
		}
	}
		
	
	
	public E remove(String owner, String passw, E data) throws NullPointerException, IllegalOwnerException {
		if (owner==null || passw==null || data==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else{
			User<E> ut=users.get(ind);
			//if(ut.getDatoInd(data)!=-1) {
				int rim=ut.getDatoInd(data);
				if (rim!=-1) {
					ut.getVdata().remove(rim);
					return data;
				}
				else return null;
		}
}

	public void copy (String owner, String passw, E data) throws NullPointerException, IllegalOwnerException, NoDataException, DataDuplicateException{
		if (owner==null || passw==null || data==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else{
			User<E> ut=users.get(ind);
			if(ut.getDatoInd(data)!=-1) throw new DataDuplicateException();
			else{
				for(int i=0;i<users.size();i++) {
					User<E> ut2=users.get(i);
					if(ut.getName()!=ut2.getName()) {
						// cerco dato nei dati personali di ut2
							if(ut2.cercaUtCond(data, owner)) {
								ut.addDato(data);
								return;
							}
								
						}
						
					
				}
				throw new NoDataException();
			}
		}
	}
	
	
	public void share(String owner, String passw, String other, E data) throws NullPointerException, IllegalOwnerException, NoOtherException, NoDataException, BadSharedException {
		if (owner==null || passw==null || data==null || other==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else{
			User<E> ut=users.get(ind);
			if(ut.getDatoInd(data)!=-1) {
				if(!(other.equals(owner))) {
					int k=trovaNome(other);
					if (k==-1) throw new NoOtherException();
					else {
						ut.addOwner(data,other);
					}
				}
				else throw new BadSharedException();
				
				
			}
				
			else throw new NoDataException();	
				
		}
}
	
	public Iteratore<E> getIterator(String owner, String passw) throws NullPointerException, IllegalOwnerException{
		if(owner==null || passw==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if(ind==-1) throw new IllegalOwnerException();
		else {
			User<E> ut=users.get(ind);
			return new Iteratore<E>(ut.getVdata());
		}
	}
	
	public int size() {
		return dim;
	}
	
	
	//EFFECTS:restituisce l'indice della posizione nel vettore se owner e password corrispondono a un utente presente in user, altrimenti restituisce -1
	private int isRegister (String owner, String passw) {
		for(int i=0;i<users.size();i++) {
			User<E> ut=users.get(i);
			if (ut.getName().equals(owner) && ut.getPassword().equals(passw))
				return i;
		}
		return -1;
		
	}
	
	//EFFECTS: restituisce l'indice della posizione nel vettore se owner corrisponde al nome di un utente presente nella collezione, altrimenti restituisce -1
	private int trovaNome(String owner) {
		for(int i=0;i<users.size();i++) {
			User<E> ut=users.get(i);
			if (ut.getName().equals(owner))
					return i;
		}
		return -1;
	}
	
	
	
	
	
		
}
	

		
	


