package logic;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
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
			System.err.println("ParserCorrector : JDOMException.");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("ParserCorrector : IOException.");
			e.printStackTrace();
		}
	}
	

	private void correct() {
		// TODO Auto-generated method stub
		System.out.println("ParserCorrector : Correcting.");
		
		Element weatherData = xmlDoc.getRootElement();
		
		System.out.println(weatherData.getName());
		
		List<Element> measurements = weatherData.getChildren();
		List<Element> elements = measurements.get(1).getChildren();
		System.out.println(elements.size());
		System.out.println(elements.get(0).getName());
		
		
		/*
		for(Element measurement : weatherData.getChildren()){
			List<Attribute> l = measurement.getAttributes();
			for(Attribute a : l){
				System.out.println(a.getName());
			}*/
			/*
			System.out.println("Stn : " + measurement.getAttributeValue("STN"));
			System.out.println("Date : " + measurement.getAttributeValue("date"));
			System.out.println("Time : " + measurement.getAttributeValue("time"));
			System.out.println("Temp : " + measurement.getAttributeValue("temp"));
			System.out.println("Dewp : " + measurement.getAttributeValue("dewp"));
			System.out.println("Stp : " + measurement.getAttributeValue("stp"));
			System.out.println("Visib : " + measurement.getAttributeValue("visib"));
			System.out.println("Wdsp : " + measurement.getAttributeValue("wdsp"));
			System.out.println("Prcp : " + measurement.getAttributeValue("prcp"));
			System.out.println("Sndp : " + measurement.getAttributeValue("sndp"));
			System.out.println("Frshtt : " + measurement.getAttributeValue("frshtt"));
			System.out.println("Cldc : " + measurement.getAttributeValue("cldc"));
			System.out.println("Wnddir : " + measurement.getAttributeValue("wnddir"));
			*/
			
		}
		/*
		Element root = antbuild.getRootElement();
		String deftarget = root.getAttributeValue("default", "all");
		for (Element target : root.getChildren("target")) {
    		if (deftarget.equals(element.getAttributeValue("name"))) {
        		System.out.println("The default target " + deftarget + 
                " has dependencies " + target.getAttributeValue("depends"));
    		}
		}
		 
		
	}*/
	

	private void save() {
		// TODO Auto-generated method stub
		System.out.println("ParserCorrector : Saving.");
		

		//this.db = new DBPortal(0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}



}
