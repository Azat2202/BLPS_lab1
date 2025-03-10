package ru.itmo.lab.models.enums;

import java.util.Set;

public enum Role {
	USER(Set.of(Privilege.VIEW_HOTELS, Privilege.BOOK_ROOM)),
	
	HOTEL_ADMIN(Set.of(Privilege.VIEW_RESERVATIONS, Privilege.BOOK_ROOM, Privilege.VIEW_HOTELS)),
	
	HOTEL_OWNER(Set.of(Privilege.CREATE_ROOM, Privilege.DELETE_ROOM, Privilege.BOOK_ROOM, Privilege.VIEW_HOTELS)),
	
	SYSTEM_ADMIN(Set.of(Privilege.CREATE_HOTEL, Privilege.CANCEL_BOOKING, Privilege.VIEW_RESERVATIONS, Privilege.CREATE_ROOM, Privilege.DELETE_ROOM, Privilege.BOOK_ROOM, Privilege.VIEW_HOTELS)),
	
	TRANSACTION_APPROVER(Set.of(Privilege.APPROVE_TRANSACTION));
	
	private final Set<Privilege> privileges;
	
	Role(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
}
