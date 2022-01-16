public class UserReg {
	//Overview: Tipo modificabile che rappresenta un Utente caratterizzato da nomeId e password
	//IR(c)=c.nomeId!= null && c.password!=null
	//AF(c)=<c.nomeId, c.password>
	
	private String nomeId;
	private String password;
	
	public UserReg(String nome, String passw) {
		if ((nome==null)||(passw==null)) throw new NullPointerException();
		this.nomeId=nome;
		this.password=passw;
	}
	
	//EFFECTS: restitisce il nome dell'utente
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

}


