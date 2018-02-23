package com.rezoan.assembler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
//import defaultPackage.Mapper;


public class FirstPass{
	DataMapper dm;
	PrintWriter writer;
//	PrintWriter addressSymbolTable;
	public FirstPass( DataMapper dm) throws IOException{
		 this.dm=dm;

		 
		writer = new PrintWriter("adsTable.txt", "UTF-8");
//		addressSymbolTable= new PrintWriter("file.txt", "UTF-8");

	}

	public	void begin() throws IOException {
			@SuppressWarnings("resource")
			BufferedReader buf = new BufferedReader (new FileReader ("input.txt"));
			String nextLine="";
			int LCinInt=0;
			String LCinHexString;

			while ( (nextLine = buf.readLine()) != null){
				LCinHexString=Integer.toHexString(LCinInt);
				String[] x = nextLine.toUpperCase().split(" ");
				
				
				if (x[0].equals("ORG")){
					LCinHexString=x[1];
					LCinInt=Integer.parseInt( x[1],16) ;
				}
				else if (x[0].contains(",")) {
					dm.setAddress(LCinHexString);
					
					generateAddressSymbolTable(LCinHexString,x[0]);
					
					String numberSystem=x[1];
					if(numberSystem.equals("DEC") || numberSystem.equals("HEX"))
					generateValueInAddress(numberSystem,x[2]);
					
					LCinInt++;
				}
				else if(x[0].contains("DEC")||x[0].contains("HEX")){
					dm.setAddress(LCinHexString);
					String numberSystem=x[0];
					generateValueInAddress(numberSystem,x[1]);
					LCinInt++;
				}
				else if (x[0].equals("END")) {
					writer.close();
//					addressSymbolTable.close();
					@SuppressWarnings("unused")
					SecondPass sp= new SecondPass(dm);
				}
				else{
					LCinInt++;
				}		
			}

		}
	//This method takes LC in HEX, name of the Symbolic address & its content in String
	//It refers to addressSymbolToBinary to convert each character of symAD to 16 bit binary
	//It refers to generateLCtoBinary to convert hex LC to 16 bit binary
		private void generateAddressSymbolTable(String lc, String symAd) throws IOException {
			
			for(int i=0;i<symAd.length();i++){
				String adsbin;
				char adschar='0';
				writer.print(symAd.charAt(i));
				adschar=symAd.charAt(i);
				adsbin=addressSymbolToBinary(adschar);
				try{
					writer.print(symAd.charAt(++i)+" ");
					adschar=symAd.charAt(i);
					adsbin+=addressSymbolToBinary(adschar);
					}
				
				catch (Exception e){
					writer.print(" ");
					adsbin=adsbin+"00001101";
				}
				writer.println(adsbin);		
				
			}
			
//			addressSymbolTable.print(symAd+" ");
			
			writer.print("(LC) ");
			
			String lcbin=generateHexStringtoBinary(lc);
//			addressSymbolTable.println(lcbin);
			writer.println(lcbin);

		}
		//This method takes individual characters from addressSymbol
		//and convert them to 4 bit binary using convertHexToBin method
		private String addressSymbolToBinary(char symad){
			String bin="";
				int x=symad;
				String hex=Integer.toHexString(x);
				
				for (int j=0;j<hex.length();j++){
					bin = bin+ convertHexToBin(hex.charAt(j)+"");	
				}
			
			return bin;
		}
		

//Takes hex string as input and convert them to 16 bit binary using convertHexToBin		
		private String generateHexStringtoBinary(String lc){
			String bin="";

			for(int i=0;i<lc.length();i++){
				bin = bin + convertHexToBin(lc.charAt(i)+"");
			}
			
			switch (bin.length()){
			case 4:  bin="000000000000"+bin;	break;
			case 8:  bin="00000000"+bin;		break;
			case 12: bin="0000"+bin;			break;
			}
			
			return bin;
		}
		
		private void generateValueInAddress(String numberSystem, String number){
			if (number.contains("-")){
				int x=0;
				if(numberSystem.equals("DEC"))
					x=Integer.parseInt(number);
				else if(numberSystem.equals("HEX")){
					x=Integer.parseInt(number,16);
				}
				x=131072+x;
				number=Integer.toHexString(x).substring(1);
			}
			else {
				if (numberSystem.equals("DEC")){
					int x=Integer.parseInt(number);
					number=Integer.toHexString(x);
				}
					
			}

			
			String valueInAddress= generateHexStringtoBinary(number);
			dm.setBinCode(valueInAddress);
		}
			
		private String convertHexToBin(String x){
			//System.out.println("string received to convertHexToBin = " + x);
			String stBin = null;
			switch(x){
				case "0": stBin="0000";
					break;
				case "1": stBin="0001";
					break;
				case "2": stBin="0010";
					break;
				case "3": stBin="0011";
					break;
				case "4": stBin="0100";
					break;
				case "5": stBin="0101";
					break;
				case "6": stBin="0110";
					break;
				case "7": stBin="0111";
					break;
				case "8": stBin="1000";
					break;
				case "9": stBin="1001";
					break;

				case "A":
				case "a":stBin="1010";
					break;
				case "B":
				case "b":stBin="1011";
					break;
				case "C":
				case "c":stBin="1100";
					break;
				case "D":
				case "d":stBin="1101";
					break;
				case "E":
				case "e":stBin="1110";
					break;
				case "F":
				case "f":stBin="1111";
					break;
			}
			return stBin;
			
		}

		
}
