package astroclime.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

public class WeatherData {
	
	private static OpenWeatherMap OWM;
	
	private static OpenWeatherMap getOWM() {
		if (OWM == null) {
			OWM = new OpenWeatherMap("");
			OWM.setApiKey("5b9c6f952fad43199fd6b949f7f9bbb0");
		}
		
		return OWM;
	}
	
	public static CurrentWeather getCurrentWeather(String cityName, String countryCode) throws JSONException, IOException{
		CurrentWeather cwd = getOWM().currentWeatherByCityName(cityName, countryCode);
		return cwd;
	}
	
	public static float getCloudCover(CurrentWeather c) {
		if (c.hasCloudsInstance()) {
			return c.getCloudsInstance().getPercentageOfClouds();
		}else{
			return -1;
		}
	}
	
	public static int getTemperature(CurrentWeather c, boolean asCelcius) {
		
		float t = -1;
		
		if (c.hasMainInstance()) {
			t = c.getMainInstance().getTemperature();
		}
		
		if (asCelcius) {
			t = (t-32) * (0.5556f);
		}
		
		return Math.round(t);
	}
	
	public static float getVisibility(CurrentWeather c) {
		
		float v = 0;
		
		if (c.hasRawResponse()) {
			String[] responses = c.getRawResponse().split(",");
			
			for (int i = 0; i < responses.length; i++) {
				if (responses[i].contains("visibility")) {
					String[] split = responses[i].split(":");
					v = Float.parseFloat(split[1]);
					i = responses.length;
				}
			}
		}
		
		return v / 1000f;
		
	}
	
	public static float getHumidity(CurrentWeather c) {
		float h = -1;
		
		if (c.hasMainInstance() && c.getMainInstance().hasHumidity()) {
			h = c.getMainInstance().getHumidity();
		}
		
		return h;
	}
	
	public static float getRainfall(CurrentWeather c) {
		float r = -1;
		
		if (c.hasRainInstance() && c.getRainInstance().hasRain3h()) {
			r = c.getRainInstance().getRain3h();
			
		}
		
		return r;
	}
	
	public static String getWeather(CurrentWeather c) {
		String w = "NOTHING";
		
		w = c.getWeatherInstance(0).getWeatherName();
		
		return w;
	}
	
	public static void main(String args[]) {
		try {
			CurrentWeather cwd = getCurrentWeather("Cambridge","GB");
			
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}