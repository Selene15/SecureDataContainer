public class MainTestH {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SecureDataContainer<String> coll= new MyHashSecureDataContainer<String>();
		
		try {
			//aggiungo utente1 alla collezione
			coll.createUser("utente1", "p1");
			System.out.println("Ho inserito utente1");
			//aggiungo utente2 alla collezione
			coll.createUser("utente2", "p2");
			System.out.println("Ho inserito utente2");
			//aggiungo utente3 alla collezione
			coll.createUser("utente3", "p3");
			System.out.println("Ho inserito utente3");
			//provo ad aggiungere utente3 ma con esito negativo perchè già presente
			coll.createUser("utente3", "p3");
			System.out.println("Ho inserito utente3");
			
		}
		catch (DuplicateIdException e) {System.err.println("Id già presente");}
		
		try {
			//inserisco data1,data2,data3 alla collezione dell'utente1
			System.out.println("esito inserzione:" + coll.put("utente1", "p1", "data1"));
			System.out.println("esito inserzione:" + coll.put("utente1", "p1", "data2"));
			System.out.println("esito inserzione:" + coll.put("utente1", "p1", "data3"));
			//inserisco data1,data4 alla collezione dell'utente3
			System.out.println("esito inserzione:" + coll.put("utente3", "p3", "data1"));
			System.out.println("esito inserzione:" + coll.put("utente3", "p3", "data4"));
			//provo ad inserire un'altro dato all'utente 3 ma ottengo accesso negato perchè la passwiord non corrisponde all'utente3
			System.out.println("esito inserzione:" + coll.put("utente3", "p7", "data6"));
			
		}
		
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		
		try {
			//provo ad inserire un dato null all'utente 3 ma ottengo "parametri nulli"
			System.out.println("esito inserzione:" + coll.put("utente2", "p2", null));
			}
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		
		
		//richiedo numero di elementi di u1, u2,u3
		try {
			System.out.println("il numero di elementi di utente1: " +(coll.getSize("utente1", "p1")));
			System.out.println("il numero di elementi di utente2: " +(coll.getSize("utente2", "p2")));
			System.out.println("il numero di elementi di utente3: " +(coll.getSize("utente3", "p3")));
		}
		catch (IllegalOwnerException e1) {	System.err.println("Accesso Negato");}
		catch (NullPointerException e2) { System.err.println("Parametri nulli");	}
		

		
		try {
			//condivido il dato d3 di u1 con u2 
			coll.share("utente1", "p1", "utente2", "data3");
			System.out.println("condivisione riuscita");
			//condivido il dato d4 di u3 con u2
			coll.share("utente3", "p3", "utente2", "data4");
			System.out.println("condivisione riuscita");
			//condivido un dato che non è presente allora: dato non presente
			coll.share("utente1", "p1", "utente3", "data5");
			System.out.println("condivisione riuscita");
			
		}
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		catch (NoDataException e3) {System.err.println("Dato non presente");}
		catch(NoOtherException e4) {System.err.println("Utente con cui condividere il dato non è presente");}
		catch (BadSharedException e1) {System.err.println("Utente con cui condividere il dato uguale all'utente che ha effettuato l'accesso");	}
		
		
		try {
		//condivido un dato a un utente che non è presente allora: utente con cui condividere il dato non presente
		coll.share("utente1", "p1", "utente5", "data3");
		System.out.println("condivisione riuscita");
		
		}
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		catch (NoDataException e3) {System.err.println("Dato non presente");}
		catch(NoOtherException e4) {System.err.println("Utente con cui condividere il dato non è presente");}
		catch (BadSharedException e1) {System.err.println("Utente con cui condividere il dato uguale all'utente che ha effettuato l'accesso");	}
		
		try {
			//condivido un dato a un utente che è uguale all'utente che ha effettuato l'accesso
			coll.share("utente1", "p1", "utente1", "data3");
			System.out.println("condivisione riuscita");
			
			}
			catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
			catch (NullPointerException e2) {System.err.println("Parametri nulli");}
			catch (NoDataException e3) {System.err.println("Dato non presente");}
			catch(NoOtherException e4) {System.err.println("Utente con cui condividere il dato non è presente");}
			catch (BadSharedException e1) {System.err.println("Utente con cui condividere il dato uguale all'utente che ha effettuato l'accesso");	}
			
		
		
		
		try {
			//copio un dato che è condiviso dallo stesso utente e lo aggiungo nei suoi dati personali
			coll.copy("utente2", "p2", "data3");
			System.out.println("Copia effettuata");
			//copio un dato che non è presente ne nei dati personali ne negli utenti condivisi allora: dato non presente
			coll.copy("utente2", "p2", "data1");
			System.out.println("Copia effettuata");
		}
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		catch (NoDataException e3) {System.err.println("Dato non presente");}
		catch (DataDuplicateException e3) {System.err.println("Dato già presente");}
		
		try {
			
			// copio un dato che è già presente nei dati personali allora: Dato già presente 
			coll.copy("utente1", "p1", "data2");
			System.out.println("Copia effettuata");
		}
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		catch (NoDataException e3) {System.err.println("Dato non presente");}
		catch (DataDuplicateException e3) {System.err.println("Dato già presente");}
		
		try {
			//richiedo numero di elementi di  u2: 1
			System.out.println("il numero di elementi di utente2: " +(coll.getSize("utente2", "p2")));
	
		}
		catch (IllegalOwnerException e1) {	System.err.println("Accesso Negato");}
		catch (NullPointerException e2) { System.err.println("Parametri nulli");	}
		
		try {
			//richiedo la copia del valore del dato3 dell'utente2
			System.out.println("la copia di data3 è: "+ coll.get("utente2", "p2", "data3"));
			//richiedo la copia del valore del dato4 dell'utente2
			System.out.println("la copia di data4 è: "+ coll.get("utente2", "p2", "data4"));
			//richiedo la copia del valore del dato7 dell'utente2, ma il dato non è presente allora restituisco null
			System.out.println("la copia di data7 è: "+ coll.get("utente2", "p2", "data7"));

		}
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		
		
		
		try {
			//rimuovo il dato3 dell'utente1
			System.out.println("il dato rimosso all'utente1 è "+ coll.remove("utente1", "p1", "data3"));
			//rimuovo il dato8 dell'utente1 ma il dato non è presente quindi restitusco null
			System.out.println("il dato rimosso all'utente1 è "+ coll.remove("utente1", "p1", "data8"));
		}
		catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
		catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		
		//restituisco i dati presente nella collezione di utente1
		try {
			Iteratore<String> it=coll.getIterator("utente1","p1");
			while(it.hasNext()) {
				DataCollection<String> a=it.next();
				System.out.println("It utente1:" + a.getDato());
			}
		
		
			}
			catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
			catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		
		//restituisco i dati presente nella collezione di utente2
		try {
			Iteratore<String> it=coll.getIterator("utente2","p2");
			while(it.hasNext()) {
				DataCollection<String> a=it.next();
				System.out.println("It utente2:" + a.getDato());
			}
		
		
			}
			catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
			catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		
		//restituisco i dati presente nella collezione di utente3
		try {
			Iteratore<String> it=coll.getIterator("utente3","p3");
			while(it.hasNext()) {
				DataCollection<String> a=it.next();
				System.out.println("It utente3:" + a.getDato());
			}
		
		
			}
			catch (IllegalOwnerException e1) {System.err.println("Accesso Negato");	}
			catch (NullPointerException e2) {System.err.println("Parametri nulli");}
		//restituisco il numero degli utenti registrati: 3
		System.out.println("il numero degli utenti registrati è: " + coll.size());
	}
}
