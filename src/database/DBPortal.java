package database;

public class DBPortal implements Runnable{
	
	private int stn;
	private String date;
	private String time;
	private float temp;
	private float dewp;
	private float stp;
	private float slp;
	private float visib;
	private float wdsp;
	private float prcp;
	private float sndp;
	private byte frshtt;
	private float cldc;
	private short wndir;
	
	public DBPortal(int stn, String date, String time, float temp, float dewp, 
					float stp, float slp, float visib, float wdsp, float prcp, 
								float sndp, byte frshtt, float cldc, short wndir){
		
		this.stn= stn;
		this.date= date;
		this.time= time;
		this.temp= temp;
		this.dewp= dewp;
		this.stp= stp;
		this.slp= slp;
		this.visib= visib;
		this.wdsp= wdsp;
		this.prcp= prcp;
		this.sndp= sndp;
		this.frshtt= frshtt;
		this.cldc= cldc;
		this.wndir= wndir;
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	

}
