package com.khyuna0.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SACommand {
	
	public void execute (HttpServletRequest request, HttpServletResponse response);

	
}
