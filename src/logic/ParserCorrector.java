package logic;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import database.DBPortal;
import domain.Measurement;

public class ParserCorrector implements Runnable{

	private StringBuffer input;
	private Thread thread;
	
	private Document xmlDoc;
	private SAXBuilder builder;
	
	private Measurement measurement;
	
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
	
	/**
	 * Casustekst :
	 * 
	 * Indien ��n of meer meetwaarden ontbreken, worden ze door het systeem berekend door middel van extrapolatie van de dertig voorafgaande metingen. 
	 * Dit komt ongeveer in 1% van alle gevallen voor.
	 * 
	 * Een meetwaarde voor de temperatuur wordt als irre�el beschouwd indien
	 * ze 20% of meer groter is of kleiner is dan wat men kan verwachten op basis van extrapolatie van de dertig voorafgaande temperatuurmetingen. 
	 * 
	 * In dat geval wordt de ge�xtrapoleerde waarde � 20% voor de temperatuur opgeslagen. 
	 * Voor de andere meetwaarden wordt deze handelswijze niet toegepast.
	 * 
	 */

	private void correct() {
		// TODO Auto-generated method stub
		System.out.println("ParserCorrector : Correcting.");
		
		// De nodige diepte
		//System.out.println(weatherData.getChildren().get(0).getChildren().get(0).getName()); <-- STN
		
		/*
		String s = elements.get(0).getValue();
	
		System.out.println(s);
		*/
		
		
		Element weatherData = xmlDoc.getRootElement();
				
		System.out.println(weatherData.getName());
		
		List<Element> measurements = weatherData.getChildren();
		
		// Start van for-loop
		List<Element> elements = measurements.get(1).getChildren();
		
		int stn = 0;
		String date = "";
		String time = "";
		float temp = 0;
		float dewp = 0;
		float stp = 0;
		float slp = 0;
		float visib = 0;
		float wdsp = 0;
		float prcp = 0;
		float sndp = 0;
		byte frshtt = 0;
		float cldc = 0;
		short wnddir = 0;
		
		for(Element e : elements){
			System.out.println("Name : " + e.getName());
			System.out.println("Value :" + e.getValue());

			if(e.getName().equals("STN")){
				stn = new Integer(e.getValue()).intValue();
			}
			if(e.getName().equals("DATE")){
				date = e.getValue();
			}
			if(e.getName().equals("TIME")){
				time = e.getValue();
			}
			if(e.getName().equals("TEMP")){
				temp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("DEWP")){
				dewp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("STP")){
				stp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("SLP")){
				slp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("VISIB")){
				visib = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("WDSP")){
				wdsp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("PRCP")){
				prcp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("FRSHTT")){
				sndp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("CLDC")){
				frshtt = new Byte(e.getValue()).byteValue();
			}
			if(e.getName().equals("WNDDIR")){
				wnddir = new Short(e.getValue()).shortValue();
			}
		}
		
		this.measurement = new Measurement(stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir);
		
		
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
		
		new DBPortal(measurement).saveWeatherData();
		
		//this.db = new DBPortal(0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}



}
