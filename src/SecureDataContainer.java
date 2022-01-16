
public interface SecureDataContainer<E extends Comparable<E>> {

	/*OVERVIEW: collezione modificabile che rappresenta un insieme di utenti a cui ad ogni utente è associato un certo numero di elementi di tipo E. Ogni elemento può essere condiviso con più utenti.
	* TipicalElement:<User(i),Datas(i)> t.c 0<=i<n, 
	* ove User(i)=<Id,password> e Datas(i)={data(1),...,data(Datas(i).size)} t.c data(j) è di tipo E
	*/
	public void createUser(String Id, String passw) throws NullPointerException, DuplicateIdException;
	//REQUIRES: id!=null, passw!=null id non deve appartenere a <User(0).Id...User(n).Id>
	//THROWS: if id==null || passw==null sollevo un' eccezione uncheked NullPointerException,
	// if id appartiene a <User(0).Id...User(n).Id> sollevo un'eccezione cheked DuplicateIdException;
	//EFFECTS:aggiungo un nuovo utente alla collezione, associandogli la relativa password.
	//MODIFIES: this
	
	public int getSize(String Owner, String passw) throws NullPointerException, IllegalOwnerException;
	//REQUIRES: owner!=null, passw!=null, <owner,passw> deve appartenere a <User(0)...User(n)>
	//THROWS: if owner == null || passw==null sollevo un'ecezione uncheked NullPointerException
	//if <owner,passw> non appartiene a <User(0)...User(n)> sollevo un'ecezione cheked IllegalOwnerException
	//EFFECTS: restituisco il numero di elementi di un utente presente nella collezione	

	public boolean put(String Owner, String passw, E data) throws NullPointerException, IllegalOwnerException;
	//REQUIRES: owner!=null, passw!=null, data!=null, <owner,passw> deve appartenere a <User(0)...User(n)>
	//THROWS: if owner == null || passw==null sollevo un'ecezione uncheked NullPointerException
	//if <owner,passw> non appartiene a <User(0)...User(n)> sollevo un'ecezione cheked IllegalOwnerException
	//EFFECTS: se data non appartiene a datas(i) aggiungo il dato alla collezione dell'utente e restituisco true, altrimenti restituisco false
	//MODIFIES: this
	
	public E get(String Owner, String passw, E data) throws NullPointerException, IllegalOwnerException;
	//REQUIRES: owner!=null, passw!=null, data!=null, <owner,passw> deve appartenere a <User(0)...User(n)>
	//THROWS: if owner == null || passw==null sollevo un'ecezione uncheked NullPointerException
	//if <owner,passw> non appartiene a <User(0)...User(n)> sollevo un'ecezione cheked IllegalOwnerException
	//EFFECTS: restituisco una copia del dato se presente in Datas(i) ove i è l'iesimo utente, oppure se quel dato è stato condiviso con l'utente. Se non trovo il dato restituisco null.
				
	public E remove(String Owner, String passw, E data) throws NullPointerException, IllegalOwnerException;
	//REQUIRES: owner!=null, passw!=null, data!=null, <owner,passw> deve appartenere a <User(0)...User(n)>
	//THROWS: if owner == null || passw==null sollevo un'ecezione uncheked NullPointerException
	//if <owner,passw> non appartiene a <User(0)...User(n)> sollevo un'ecezione cheked IllegalOwnerException
	//EFFECTS: se il dato appartiene a Datas(i) ove i è l'iesimo utente, elimino il dato e lo restituisco, altrimenti restituisco null
	//MODIFIES:se il dato è presente modifico this;
	
	public void copy(String Owner, String passw, E data) throws NullPointerException, IllegalOwnerException, NoDataException, DataDuplicateException;
	//REQUIRES: owner!=null, passw!=null, data!=null,<owner,passw> deve appartenere a <User(0)...User(n)>, data non deve appartenere a Datas(i)
	//THROWS: if owner == null || passw==null sollevo un'ecezione uncheked NullPointerException
	//if <owner,passw> non appartiene a <User(0)...User(n)> sollevo un'ecezione cheked IllegalOwnerException
	//if data non appartiene a Datas(i) e non è stato condiviso con l'utente sollevo un'eccezione cheked NoDataException
	//if dato appartiene in Datas(i) ove i è l'iesimo utente sollevo un'eccezione cheked DataDuplicateException
	//EFFECTS: se l'utente è associato a quel dato come utente condiviso vado ad aggiungere il dato in Datas(i).
	//MODIFIES:this
	
	public void share(String Owner, String passw, String Other, E data) throws NullPointerException, IllegalOwnerException, NoOtherException, NoDataException, BadSharedException;
	//REQUIRES: owner!=null, passw!=null, data!=null, <owner,passw> deve appartenere a <User(0)...User(n)>, data deve appartenere a Datas(i), other deve appartenere a <User(0).Id...User(n).Id>
	//THROWS: if owner == null || passw==null sollevo un'ecezione uncheked NullPointerException
	//if owner==other sollevo un'eccezione cheked BadSharedException
	//if <owner,passw> non appartiene a <User(0)...User(n)> sollevo un'ecezione cheked IllegalOwnerException
	//if data non appartiene a Datas(i) sollevo un'eccezione cheked NoDataException
	//if other non appartiene a <User(0).Id...User(n).Id> sollevo un'eccezione cheked NoOtherException
	//EFFECTS: condivido con other il dato data
	//MODIFIES:this
	public Iteratore<E> getIterator(String Owner, String passw) throws NullPointerException, IllegalOwnerException;
	//REQUIRES: owner!=null, passw!=null, <owner,passw> deve appartenere a <User(0)...User(n)>
	//THROWS: if owner==null||passw==null||data==null lancio un'eccezione uncheked NullPointerException
	//if <owner,passw> non appartiene a <User(0)...User(n)> lancio un'eccezione cheked IllegalOwnerException
	//EFFECTS: restituisco un iteratore che genera tutti i dati dell'utente in ordine arbitrario
	public int size();	
	//EFFECTS:restituisce il numero di utenti presenti nella collezione
}
