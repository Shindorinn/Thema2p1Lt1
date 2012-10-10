package logic;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
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
	
	private ArrayList<Measurement> measurements;
	private DBPortal db;
	
	public ParserCorrector(StringBuffer input){
		this.input = input;
		this.thread = new Thread(this);
		this.builder = new SAXBuilder();
		this.measurements = new ArrayList<Measurement>();
		this.db = new DBPortal();
				
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
		Element weatherData = xmlDoc.getRootElement();
		
		System.out.println(weatherData.getName());
		
		List<Element> measurements = weatherData.getChildren();
		
		
		
		// Start van for-loop
		for(int i = 0; i < measurements.size() ; i++ ) {
		
			List<Element> elements = measurements.get(i).getChildren();
			
			int stn = 0;
			String date = "";
			String time = "";
			float temp = Float.MIN_VALUE;
			float dewp = Float.MIN_VALUE;
			float stp = Float.MIN_VALUE;
			float slp = Float.MIN_VALUE;
			float visib = Float.MIN_VALUE;
			float wdsp = Float.MIN_VALUE;
			float prcp = Float.MIN_VALUE;
			float sndp = Float.MIN_VALUE;
			byte frshtt = Byte.MIN_VALUE;
			float cldc = Float.MIN_VALUE;
			short wnddir = Short.MIN_VALUE;
			
			for(Element e : elements){
				System.out.println("Name : " + e.getName());
				System.out.println("Value :" + e.getValue());
				
				if(!e.getValue().equals("")){
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
			}
			
			this.measurements.add( new Measurement(stn, date, time, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir));
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
		
		for(Measurement m: measurements){
			ArrayList<Measurement> fromDB = this.db.getLastWeatherData(m.getStn(), m.getTime());
			if(!fromDB.isEmpty()){
				Measurement extrapolation = this.extrapolate(fromDB);
				checkMeasurement(m, extrapolation);
			} else {
				System.out.println("Pumping raw data!!");
			}
		}
	}

	private void save() {
		System.out.println("ParserCorrector : Saving.");
		
		for(Measurement m : measurements){
			this.db.saveWeatherData(m);
		}
	}

	private Measurement extrapolate(ArrayList<Measurement> fromDB){
		
		float temp = 0;
		float dewp = 0;
		float stp = 0;
		float slp = 0;
		float visib = 0;
		float wdsp = 0;
		float prcp = 0;
		float sndp = 0;
		float cldc = 0;
		short wnddir = 0;
		
		for(int i = 0; i < fromDB.size() - 1 ; i++ ){
			Measurement first = fromDB.get(i);
			Measurement second = fromDB.get(i + 1);
			
			temp += second.getTemp() - first.getTemp();
			dewp += second.getDewp() - first.getDewp();
			stp += second.getStp() - first.getStp();
			slp += second.getSlp() - first.getSlp();
			visib += second.getVisib() - first.getVisib();
			wdsp += second.getWdsp() - first.getWdsp();
			prcp += second.getPrcp() - first.getPrcp();
			sndp += second.getSndp() - first.getSndp();
			cldc += second.getCldc() - first.getCldc();
			wnddir += second.getWnddir() - first.getWnddir();
		}
		
		int n = fromDB.size() -1;
		if(n !=0 ){
			temp = fromDB.get(0).getTemp() + temp/n;
			dewp = fromDB.get(0).getTemp() + dewp/n;
			stp = fromDB.get(0).getStp() + stp/n;
			slp = fromDB.get(0).getSlp() + slp/n;
			visib = fromDB.get(0).getVisib() + visib/n;
			wdsp = fromDB.get(0).getWdsp() + wdsp/n;
			prcp = fromDB.get(0).getPrcp() + prcp/n;
			sndp = fromDB.get(0).getSndp() + sndp/n;
			cldc = fromDB.get(0).getCldc() + cldc/n;
			wnddir = (short) (fromDB.get(0).getWnddir() + wnddir/n);
			
			return new Measurement(-1, null, null, temp, dewp, stp, slp, visib, wdsp, prcp, sndp, (byte) 0, cldc, wnddir);
		} else {
			return fromDB.get(0);
		}
	}


	private Measurement checkMeasurement(Measurement toCheck, Measurement fromDB){
		
		// Checking if the temperature is present and whether this is plausible, else change it.
		if(toCheck.getTemp() == Float.MIN_VALUE){
			toCheck.setTemp(fromDB.getTemp());
		} else if (toCheck.getTemp() < 0.8f * fromDB.getTemp()){ // Is the value too low?
			toCheck.setTemp(0.8f * fromDB.getTemp()); 
		} else if (toCheck.getTemp() > 1.2f * fromDB.getTemp()){ // Is the value too high?
			toCheck.setTemp(1.2f * fromDB.getTemp());
		}
		
		
		// Checking if the other values are present
		if(toCheck.getDewp() == Float.MIN_VALUE){
			toCheck.setDewp(fromDB.getDewp());
		}
		if(toCheck.getStp() == Float.MIN_VALUE){
			toCheck.setStp(fromDB.getStp());
		}
		if(toCheck.getSlp() == Float.MIN_VALUE){
			toCheck.setSlp(fromDB.getSlp());
		}
		if(toCheck.getVisib() == Float.MIN_VALUE){
			toCheck.setVisib(fromDB.getVisib());
		}
		if(toCheck.getWdsp() == Float.MIN_VALUE){
			toCheck.setWdsp(fromDB.getWdsp());
		}
		if(toCheck.getPrcp() == Float.MIN_VALUE){
			toCheck.setPrcp(fromDB.getPrcp());
		}
		if(toCheck.getSndp() == Float.MIN_VALUE){
			toCheck.setSndp(fromDB.getSndp());
		}
		if(toCheck.getFrshtt() == Byte.MIN_VALUE){
			toCheck.setFrshtt(fromDB.getFrshtt());
		}
		if(toCheck.getCldc() == Float.MIN_VALUE){
			toCheck.setCldc(fromDB.getCldc());
		}
		if(toCheck.getWnddir() == Float.MIN_VALUE){
			toCheck.setWnddir(fromDB.getWnddir());
		}
		
		return toCheck;
	}
}
