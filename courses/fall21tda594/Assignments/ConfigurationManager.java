import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Reading of property file runtime.properties inside /bin folder
 * Providing information about current configuration.
 * @author Tobias Schwarz
 *
 */
public class ConfigurationManager {
	private List<String>propertiesList = new ArrayList<String>();
	private static ConfigurationManager single_instance = null; 
	
	private ConfigurationManager() {}
	public static ConfigurationManager getInstance() {
		if (single_instance == null) {
			single_instance = new ConfigurationManager();
		}
		return single_instance;
	}

	
	
	/**
	 * @return true on successful reading ; false on any error
	 */    
	private boolean readPropertyFile() {
		InputStream stream = this.getClass().getResourceAsStream("runtime.properties");
	    if(stream == null) {
	    	System.out.println(stream == null);
	    	System.out.println("Error: File runtime.properties not in expected place.");
	    	return false;
	    } 
	    
	    try { 
	    	// For-loop over all lines
	    	for(int x=0; x<Integer.MAX_VALUE; x++) { 
	    		String s = "";
				// For-loop over current line
				for (int y = 0; y < Integer.MAX_VALUE; y++) {
					char c = (char) stream.read();
					// In case of new line leave for-loop
					if ((int) c == 10) {
						break;
					}
					// In case of end of file re-write data for better usage and leave
					if ((int) c == 65535) {
						// adding last element to list
						propertiesList.add(s);
						System.out.println(propertiesList.toString());
						return true;
					}
					s += c;
				}
				propertiesList.add(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return false;
	}
	
	/**
	 * Checks config file if given parameter is active (true). In case requestedParameter is available, but disabled (false) a false will be returned.
	 * @param requestParameter - searched parameter name
	 * @return True when parameter is present and active ; false else
	 */
	public boolean getProperty(String requestParameter) {
		return getProperty(requestParameter, true);
	}
	
	/**
	 * 
	 * @param requestParameter - searched parameter name
	 * @param silent - true to suppress info prints
	 * @return True when parameter is present and active ; false else
	 */
	public boolean getProperty(String requestParameter, boolean silent) {

		if(!silent) {System.out.println("Looking for value " +requestParameter);}
		
		if(propertiesList.isEmpty() == true) {
			System.out.print("Reading property file: ");
			readPropertyFile();
		}
		
		// Interpreting of line content
	    for(String data: propertiesList) {
	    	// checking for value +"=" to avoid wrong interpretations, e.g. search "M1" would be found as well in "M11"
	    	if(data.startsWith(requestParameter+"=")==true) {
//	    		System.out.println("Parameter found: " +data);
	    		//Read value after "=" sign which must be true or false
	    		String tmp = data.split("=")[1];
	    		if(tmp.matches("true")==true) {
	    			if(!silent) {System.out.println("Requested parameter " +requestParameter +" equals \"true\" found");}
	    			return true;
	    		} else if (tmp.matches("false")==true) {
	    			if(!silent) {System.out.println("Requested parameter " +requestParameter +" equals \"false\" found");} 
	    			return false;
	    		} else {
	    			System.out.println("Error: Unknown value: " +tmp);
	    		}
	    	}
	    }

	    //requested parameter not found in configuration file - print error and return
	    System.out.println("RequestedParameter \"" +requestParameter +"\" is not part of the property file.");
		return false;
		
	}
	
	/**
	 * Function checks if for configuration property with starting string at least one item is present and active
	 * @param requestString - starting string of search property
	 */
	public boolean checkPropertyKindActive(String requestString) {
		boolean silent = false;
		
		if(!silent) {System.out.println("Looking for value " +requestString);}
		
		if(propertiesList.isEmpty() == true) {
			System.out.print("Reading property file: ");
			readPropertyFile();
		}
		
		// Interpreting of line content
	    for(String data: propertiesList) {
	    	if(data.startsWith(requestString)==true) {
	    		if(!silent) {System.out.println("Parameter found: " +data);}
	    		//Read value after "=" sign which must be true or false
	    		String tmp = data.split("=")[1];
	    		if(tmp.matches("true")==true) {
	    			if(!silent) {System.out.println("Requested parameter " +requestString +" equals \"true\" found");}
	    			return true;
	    		} else if (tmp.matches("false")==true) {
	    			if(!silent) {System.out.println("Requested parameter " +requestString +" equals \"false\" found");} 
	    			return false;
	    		} else {
	    			System.out.println("Error: Unknown value: " +tmp);
	    		}
	    	} /*else {
	    		if(!silent) {System.out.println("Checked: \"" +data +"\" - not searched element");}
	    	} */
	    }

	    //requested parameter not found in configuration file - print error and return
	    System.out.println("RequestedParameter \"" +requestString +"\" is not part of the property file.");
		return false;
	}
}