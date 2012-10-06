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
	 * Indien één of meer meetwaarden ontbreken, worden ze door het systeem berekend door middel van extrapolatie van de dertig voorafgaande metingen. 
	 * Dit komt ongeveer in 1% van alle gevallen voor.
	 * 
	 * Een meetwaarde voor de temperatuur wordt als irreëel beschouwd indien
	 * ze 20% of meer groter is of kleiner is dan wat men kan verwachten op basis van extrapolatie van de dertig voorafgaande temperatuurmetingen. 
	 * 
	 * In dat geval wordt de geëxtrapoleerde waarde ± 20% voor de temperatuur opgeslagen. 
	 * Voor de andere meetwaarden wordt deze handelswijze niet toegepast.
	 * 
	 */

	private void correct() {
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
			if(e.getName().equals("SNDP")){
				sndp = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("FRSHTT")){
				// To parse the string into a byte,
				// apparently i have to first convert it into a int value
				// to the convert that into a byte... Strange
				
				String tempFrshtt = e.getValue();
				
				
				frshtt = Byte.parseByte( tempFrshtt, 2 );
			}
			if(e.getName().equals("CLDC")){
				cldc = new Float(e.getValue()).floatValue();
			}
			if(e.getName().equals("WNDDIR")){
				wnddir = new Short(e.getValue()).shortValue();
			}
		}
		
		this.measurement = new Measurement(stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir);
			
	}

	private void save() {
		// TODO Auto-generated method stub
		System.out.println("ParserCorrector : Saving.");
		
		new DBPortal(measurement).saveWeatherData();
	}



}
