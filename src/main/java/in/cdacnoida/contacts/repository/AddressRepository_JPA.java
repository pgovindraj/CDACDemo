package in.cdacnoida.contacts.repository;

import in.cdacnoida.contacts.entities.Address;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class AddressRepository_JPA {

	// DELETED CONSTRUCTOR
	// Instantiate an EntityManager
	// Delete init
	// find: delete try finally , does not throw
	// create and update is merged as save
	// unmarshall is not required.

	private final EntityManager em = Persistence.createEntityManagerFactory(
			"training").createEntityManager();

	
	//Autobox
	private Address find(long id) {
		//SQLGeneration, Unmarshalling,
		return em.find(Address.class, id);	
	}
	
	private void save(Address address){
		em.merge(address);
	}
	
	private void delete(Address address){
		em.remove(address);
	}

}
