package it.polito.tdp.denvercrimes.db;

import it.polito.tdp.denvercrimes.model.Event;

public class TestDAO {

	public static void main(String[] args) {
		EventsDAO dao = new EventsDAO();
		for(Event e : dao.listAllEvents())
			System.out.println(e);
	}

}
