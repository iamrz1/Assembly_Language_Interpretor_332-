package com.rezoan.assembler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class SecondPass {
	HashMap<String, String> mriMap;
	HashMap<String, String> nonmriMap;
	HashMap<String, String> adsMap;
	DataMapper dm;
	PrintWriter writer;

	public SecondPass(DataMapper dm) {
		this.dm=dm;
		mriMap=new HashMap<String, String> ();
		nonmriMap=new HashMap<String, String> ();
		adsMap=new HashMap<String, String> ();
		
		try {
			writer = new PrintWriter("output.txt", "UTF-8");
			readFromTable();
			readFromInput();
			
		} catch (Exception e){
			
		}
		
	}
	
	private void readFromTable() throws IOException{
		BufferedReader mriFile = new BufferedReader (new FileReader ("mri.txt"));
		BufferedReader nonmriFile = new BufferedReader (new FileReader ("nonmri.txt"));
		BufferedReader adsFile = new BufferedReader (new FileReader ("adsTable.txt"));
		
		String nextLine="",adsSymbol="";

		while ( (nextLine = mriFile.readLine()) != null){
			String[] x = nextLine.split(" ");
			mriMap.put(x[0], x[1]);
		}
		while ( (nextLine = nonmriFile.readLine()) != null){
			String[] x = nextLine.split(" ");
			nonmriMap.put(x[0], x[1]);
		}
		while ( (nextLine = adsFile.readLine()) != null){
			
			adsSymbol=nextLine.substring(0, 2);
			nextLine=adsFile.readLine();
			String[] x = nextLine.split(" ");
			if (x[0].contains("(LC)")){	
				adsMap.put(adsSymbol.toUpperCase(), x[1]);
			}
			else{
				
				adsSymbol=adsSymbol+x[0];
				nextLine=adsFile.readLine();
				String[] lc  = nextLine.split(" ");
				adsMap.put(adsSymbol.toUpperCase(), lc[1]);
				
			}
		}
		mriFile.close();
		nonmriFile.close();
		adsFile.close();
/*		System.out.println(mriMap.toString());
		System.out.println(nonmriMap.toString());
		System.out.println(adsMap.toString());*/
		
	}
	private void readFromInput() throws Exception{
		int LCinInt=0;
		String LCinHexString = null;
		@SuppressWarnings("resource")
		BufferedReader inputFile = new BufferedReader (new FileReader ("input.txt"));
		String currentLine="";

		while ( (currentLine = inputFile.readLine().toUpperCase()) != null){
			
			String currentWord=currentLine.substring(0,3);
			if (currentWord.contains("ORG")){
				LCinHexString=currentLine.substring(4);
				LCinInt=Integer.parseInt(LCinHexString,16);
			}
			else if (currentWord.contains("END")){
				@SuppressWarnings("rawtypes")
				Set keys = dm.tm.keySet();
				for (Iterator<?> i = keys.iterator(); i.hasNext();) {
				     String key = (String) i.next();
				     String value = (String) dm.tm.get(key);
				     System.out.println(key + " : " + value);
				     writer.println(key + " : " + value);
				}

				writer.close();
				System.exit(0);
			}
			else {
				int i=0,I=0;
				if (currentLine.contains(" I")) I=1;
				String[] x = currentLine.split(" ");
				String binCode="";
				if(x[0].contains(",")){
					i=1;
				}
				
				if(mriMap.containsKey(x[i+0])){
					String opcode=mriMap.get(x[i+0]);
					String ads=adsMap.get(x[i+1]+",");
					ads=ads.substring(4);
					
					binCode=I+opcode+ads;
					dm.makeEntry(LCinHexString, binCode);
					
				}
				else if(nonmriMap.containsKey(x[i+0])){
					
					binCode=nonmriMap.get(x[i+0]);
					dm.makeEntry(LCinHexString, binCode);
				}
				
				LCinInt++;
				
			}
				
			LCinHexString=Integer.toHexString(LCinInt);
		}
		
			
	}
	

}
