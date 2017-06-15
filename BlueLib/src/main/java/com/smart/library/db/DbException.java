package com.smart.library.db;

public class DbException extends Exception {
	private static final long serialVersionUID = 1396155837630180169L;

	public DbException(Exception e) {
		super(e);
	}
}
