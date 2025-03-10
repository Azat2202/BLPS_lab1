package ru.itmo.lab.models.enums;

import java.util.Set;

public enum Role {
	USER(Set.of(Privilege.VIEW_HOTELS, Privilege.BOOK_ROOM)),
	
	HOTEL_ADMIN(Set.of(Privilege.VIEW_RESERVATIONS)),
	
	HOTEL_OWNER(Set.of(Privilege.CREATE_ROOM, Privilege.DELETE_ROOM)),
	
	SYSTEM_ADMIN(Set.of(Privilege.CREATE_HOTEL, Privilege.CANCEL_BOOKING)),
	
	TRANSACTION_APPROVER(Set.of(Privilege.APPROVE_TRANSACTION));
	
	private final Set<Privilege> privileges;
	
	Role(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
}
