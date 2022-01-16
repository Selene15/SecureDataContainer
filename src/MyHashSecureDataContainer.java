import java.util.Hashtable;
import java.util.Vector;
/*Overview:Tipo di dato modificabile che estende l'interfaccia SecureDataContainer e utilizza una tabella hash
* per rappresentare un insieme di utenti a cui associa un certo numero di elementi di tipoE
* AF(c): <c.UserReg, c.myhash> | c.UserReg=[UserReg.get(0)...c.UserReg.get(UserReg.size())] && c.myhash= f: String -> Vector<DataCollection>
* && for all String appartenente a c.myhash.key => f(String)=c.myhash.get(String)
* IR(c): c.dim>=0 && c.dim=c.UserReg.size() && (for all String appartenente a c.myhash.keys => Esiste i, 0<=i<=c.dim | c.UserReg.get(i).getName=String
* && for all String appartenente a c.myhash.keys => f(String)=c.myhash.get(String)
 */
public class MyHashSecureDataContainer <E extends Comparable<E>> implements SecureDataContainer<E>{
	
	
	private Vector<UserReg> us_reg;
	private Hashtable<String,Vector<DataCollection<E>> > myhash;
	private int dim;
	

	public MyHashSecureDataContainer() {
		
		this.us_reg=new Vector<UserReg>();
		this.myhash=new Hashtable<String,Vector<DataCollection<E>>>();
		dim=0;
	}
	
	

	public void createUser(String id, String passw) throws NullPointerException, DuplicateIdException{
		if(id==null||passw==null) throw new NullPointerException();
		if(myhash.containsKey(id)==true) throw new DuplicateIdException();
		else{
			us_reg.add(new UserReg(id,passw));
			myhash.put(id, new Vector<DataCollection<E>>());
			dim++;
		}
	}
	
	public int getSize(String owner, String passw) throws NullPointerException, IllegalOwnerException {
		if(owner==null||passw==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else{
				int size=myhash.get(owner).size();
				return size;
			}
	}
	
	public boolean put (String owner, String passw, E data) throws NullPointerException, IllegalOwnerException {
		if (owner==null||passw==null||data==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException(); {
			int el=cercaDatoI(owner,data); 
			if(el==-1) {
				myhash.get(owner).add(new DataCollection<E>(data));
				return true;
			}
			else return false;
		}
		
	}
	
	public E get(String owner, String passw, E data) throws NullPointerException, IllegalOwnerException{
		if (owner==null || passw==null || data==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else {
			int el=cercaDatoI(owner,data);
			if (el!=-1) {
				return myhash.get(owner).get(el).getDato();
			}
			else {
				for(int i=0;i<us_reg.size();i++) {
					String n=us_reg.get(i).getName();
					if(!(n.equals(owner))) {
						int k=cercaDatoI(n,data);
						if(k!=-1) {
							DataCollection<E> d=myhash.get(n).get(k);
							if(d.getDataS().contains(owner))
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
		else {
		
			int el=cercaDatoI(owner,data); 
			if (el!=-1) {
				E rim=myhash.get(owner).get(el).getDato();
				(myhash.get(owner)).remove(el);
				return rim;
			}
			else return null;
		}
	
	}	
	
	
	public void copy (String owner, String passw, E data) throws NullPointerException, IllegalOwnerException, NoDataException, DataDuplicateException{
		if (owner==null || passw==null || data==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else{
			int el=cercaDatoI(owner,data); 
			if (el!=-1) throw new DataDuplicateException();
			else{
				for(int i=0;i<us_reg.size();i++) {
					String n=us_reg.get(i).getName();
					if(!(n.equals(owner))) {
						int k=cercaDatoI(n,data);
						if(k!=-1) {
							DataCollection<E> d=myhash.get(n).get(k);
							if(d.getDataS().contains(owner))
								myhash.get(owner).add(new DataCollection<E>(data));
							else
								throw new NoDataException();
								
						}
					}
				}
			}
	
		}
	}
	
	
	public void share(String owner, String passw, String other, E data) throws NullPointerException, IllegalOwnerException, NoOtherException, NoDataException, BadSharedException {
		if (owner==null || passw==null || data==null || other==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if ((ind==-1)) throw new IllegalOwnerException();
		else {
			int el=cercaDatoI(owner,data); 
			if(el==-1) throw new NoDataException();
			if (el!=-1) {
				if(!(other.equals(owner))) {
					if(myhash.containsKey(other)==false)throw new NoOtherException();
					else {
						DataCollection<E> d=myhash.get(owner).get(el);
						d.getDataS().add(other);
					}
				}
				else throw new BadSharedException();
			}
		}
	
}
		
	
	
	public Iteratore<E> getIterator(String owner, String passw) throws NullPointerException, IllegalOwnerException{
		if(owner==null || passw==null) throw new NullPointerException();
		int ind=isRegister(owner,passw);
		if(ind==-1) throw new IllegalOwnerException();
		else {
			return new Iteratore<E>(myhash.get(owner));
		}
	}
	
	public int size() {
		return dim;
	}
	
	
	//EFFECTS: restitusice l'indice del dato presente nel vettore altirmenti restituisce -1
	private int cercaDatoI(String owner, E data) {
		for(int i=0;i<myhash.get(owner).size();i++) {
			DataCollection<E> d=(myhash.get(owner).get(i)); 
			if(d.getDato().compareTo(data)==0)
				return i;
		}
		return -1;

		
}
	
	//EFFECTS:restituisce l'indice della posizione nel vettore se owner e password corrispondono a un utente presente in user, altrimenti restituisce -1
	private int isRegister (String owner, String passw) {
		for(int i=0;i<us_reg.size();i++) {
			UserReg ut=us_reg.get(i);
			if (ut.getName().equals(owner) && ut.getPassword().equals(passw))
				return i;
		}
		return -1;
		
	}
	
	
	
	
	

	
	
	

}
