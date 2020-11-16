package testsuite;

import changePassword.ChangePassword;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.junit.Test;
import org.junit.Assert.*;

public class UnitTest extends ChangePassword {
	Properties propCurProfile = readPropertiesFile("userProfiles.properties");
	String oldPassword = propCurProfile.getProperty("password");
	ChangePassword changePassword = new ChangePassword();

	
	@Test(timeout=10000, description="TC1: match password requirement", priority=1, group="good")
	public void UnitTest1() {
//		Properties tc = readPropertiesFile("testCase1.properties");
//		String newPassword = tc.getProperty("password");
		String newPassword = "Aloha@1Mahalo2$Ohana3";
		
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC2: over 18 alphanumeric characters and list of special characters", priority=1, group="good")
	public void UnitTest2() {
		String newPassword = "Aloha@1Solution$TrueBoy";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC3: no uppercase", priority=1, group="bad")
	public void UnitTest3() {
		String newPassword = "aloha@1solution2$true3";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC4: no lowercase", priority=1, group="bad")
	public void UnitTest4() {
		String newPassword = "ALOHA@1SOLUTION2$TRUE3";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC5: no numeric", priority=1, group="bad")
	public void UnitTest5() {
		String newPassword = "Aloha@Solution!True";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC6: no special character", priority=1, group="bad")
	public void UnitTest6() {
		String newPassword = "AlohaMahaloTrue123458";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC7: duplicate more than 4 characters", priority=1, group="bad")
	public void UnitTest7() {
		String newPassword = "AlohaMahaloOhana123458";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC8: more than 4 special characters", priority=1, group="bad")
	public void UnitTest8() {
		String newPassword = "AlohaMahaloOhana@#!$*123458";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
	
	@Test(timeout=10000, description="TC9: more than 50% is number", priority=1, group="bad")
	public void UnitTest9() {
		String newPassword = "Aloha122545452121@#!$*123456789";
		assertTrue(changePassword.ChangePassword(oldPassword, newPassword));
	}
}
