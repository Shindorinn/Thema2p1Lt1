package logic;

import java.io.IOException;
import java.io.StringReader;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import database.DBPortal;

public class ParserCorrector implements Runnable{

	private StringBuffer input;
	private Thread thread;
	
	private Document xmlDoc;
	private SAXBuilder builder;
	
	private DBPortal db;
	
	public ParserCorrector(StringBuffer input){
		this.input = input;
		this.thread = new Thread(this);
		this.builder = new SAXBuilder();
		
		this.db = new DBPortal(0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		
		thread.start();
	}


	@Override
	public void run() {
		parse();
		correct();
		save();
	}

	private void parse() {
		System.out.println("ParserCorrector : Parsing");
		try {
			xmlDoc = builder.build(new StringReader(input.toString()));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void correct() {
		// TODO Auto-generated method stub
		System.out.println("ParserCorrector : Correcting.");
	}
	

	private void save() {
		// TODO Auto-generated method stub
		System.out.println("ParserCorrector : Saving.");
	}



}
