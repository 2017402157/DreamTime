package com.dreamtime;

import com.jfinal.core.JFinal;

public class Start {
	public static void main(String[] args) {
		JFinal.start("WebContent", 8080, "/", 5);
	}
}
