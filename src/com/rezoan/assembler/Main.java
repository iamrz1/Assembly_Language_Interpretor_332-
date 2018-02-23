package com.rezoan.assembler;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		DataMapper dm= new DataMapper();
		FirstPass fp= new FirstPass(dm);

		fp.begin();
		
	}

}
