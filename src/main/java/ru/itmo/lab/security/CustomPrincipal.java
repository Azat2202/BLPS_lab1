package ru.itmo.lab.security;

import java.io.Serializable;
import java.security.Principal;

public class CustomPrincipal implements Principal, Serializable {
	private static final long serialVersionUID = 1L;
	private final String name;
	
	public CustomPrincipal(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public boolean equals(Object object) {
		boolean flag = false;
		if (object instanceof CustomPrincipal)
			flag = name.equals(((CustomPrincipal) object).getName());
		return flag;
	}
}