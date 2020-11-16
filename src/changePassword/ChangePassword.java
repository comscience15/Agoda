package changePassword;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class ChangePassword {
	private String oldPassword, newPassword;
	
	public ChangePassword() {}
	public ChangePassword(String oldPassword, String newPassword) {
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	public boolean ChangePassword(String oldPassword, String newPassword) {
		String currentUser = System.getProperty("username");
		String existingPassword = "";
		try {
			existingPassword = getExistingPW(currentUser);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(oldPassword.equals(existingPassword)) return false;
		if(isTooSimilarOldPassword(newPassword, existingPassword)) return false;
		List<String> errorList = new ArrayList<String>();
		return verifyPassword(newPassword, errorList); 
	}
	
	public Properties readPropertiesFile(String filename){
		FileInputStream fis;
		Properties prop = null;
		try {
			fis = new FileInputStream(filename);
			prop = new Properties();
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	private boolean isTooSimilarOldPassword(String newPassword, String existingPassword) {
		double existingPWLength = existingPassword.length(); 
		double newPWLength = newPassword.length();	
		int countSameChar = 0;
		if(existingPWLength >= newPWLength) {
			for(int i = 0; i < existingPWLength; i++) {
				// match more than 80% of existing password
				if(existingPWLength * 0.8 > countSameChar) return false;
				if(existingPassword.charAt(i) == newPassword.charAt(i)) countSameChar++;
			}
		} else {
			for(int i = 0; i < newPWLength; i++) {
				// match more than 80% of existing password
				if(existingPWLength * 0.8 > countSameChar) return false;
				if(existingPassword.charAt(i) == newPassword.charAt(i)) countSameChar++;
			}
		}
		return true;
 	}
	
	private String getExistingPW(String username) throws IOException {
		// dummies backend url to get user info
		URL url = new URL("https://sample.server.com/userA/info");
		String readLine = null;
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("userId", username);
		int responseCode = connection.getResponseCode();
		if(responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer response = new StringBuffer();
			while((readLine = br.readLine()) != null) {
				response.append(readLine);
			} 
			br.close();
			String res = response.toString();
			
			if(res == null) return null;
			JSONObject json = (JSONObject) JSONSerializer.toJSON(res);  
			return json.getString("Password");
		}
		return null; 
	}
	
	private boolean verifyPassword(String passwordInput, List<String> errorList) {
		Pattern specailCharPattern = Pattern.compile("!@#$&*", Pattern.CASE_INSENSITIVE);
	    Pattern UpperCasePattern = Pattern.compile("[A-Z ]");
	    Pattern lowerCasePattern = Pattern.compile("[a-z ]");
	    Pattern digitCasePattern = Pattern.compile("[0-9 ]");
	    errorList.clear();

	    boolean validPassword = true;


	    if (passwordInput.length() < 18) {
	        errorList.add("Password lenght must have at least 18 alphanumeric characters and special characters !@#$&*");
	        validPassword = false;
	    }
	    if (!specailCharPattern.matcher(passwordInput).find()) {
	        errorList.add("Password must have at least 1 special character!!");
	        if(isMoreThanFourRepeatedChars(passwordInput, specailCharPattern)) 
	        	errorList.add("They are more than 4 duplicate special characters!!"); 
	        validPassword = false;
	    }
	    if (!UpperCasePattern.matcher(passwordInput).find()) {
	        errorList.add("Password must have at least 1 uppercase character!!");
	        if(isMoreThanFourRepeatedChars(passwordInput, UpperCasePattern))
	        	errorList.add("They are more than 4 duplicate uppercase characters!!"); 
	        validPassword = false;
	    }
	    if (!lowerCasePattern.matcher(passwordInput).find()) {
	        errorList.add("Password must have at least 1 lowercase character!!");
	        if(isMoreThanFourRepeatedChars(passwordInput, lowerCasePattern))  
        		errorList.add("They are more than 4 duplicate lowercase characters!!"); 
	        validPassword = false;
	    }
	    if (!digitCasePattern.matcher(passwordInput).find()) {
	        errorList.add("Password must have at least 1 numeric character!!");
	        if(containsNumbMoreThanHalf(passwordInput))
	        	errorList.add("Password contains more than 50% numeric of al password characters!!");
	        validPassword = false;
	    }

	    return validPassword;
	}
	
	private boolean isMoreThanFourRepeatedChars(String passwordInput, Pattern pattern) {
		int countChar = 0;
    	for(char ch : passwordInput.toCharArray()) {
    		if(countChar > 4)  return true; 
    		if(pattern.matcher(ch + "").find())
    			countChar++;
    	}
    	return false;
	}
	
	private boolean containsNumbMoreThanHalf(String passwordInput) {
		Pattern digitCasePattern = Pattern.compile("[0-9 ]");
		int countNumOfPWInput = passwordInput.length();
		
		int countNum = 0;
		for(char ch : passwordInput.toCharArray()) {
    		if(countNum > countNumOfPWInput)  return true; 
    		if(digitCasePattern.matcher(ch + "").find())
    			countNum++;
    	}
    	return false;
	}
}
