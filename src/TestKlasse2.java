import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import tgm.list.Role;
import tgm.list.User;
import tgm.list.UserDatabase;

public class TestKlasse2 {
	User user1;
	
	@BeforeEach
	public void admin() {
		user1 = new User("test", "TesT1234!", Role.ADMIN, null);
	}
	
	@Test
	@DisplayName("String-Array mit Benutzername wird zurückgegeben")
	public void isAddUserDatabasePossible() {
		user1 = new User("test", "TesT1234!", Role.ADMIN, null);
		UserDatabase udb1 = new UserDatabase(user1);
		String[] expected = {"test"};
		assertArrayEquals(expected, udb1.getAllUsernames());
	}
	
	@Test	
	public void authorisedUserIsAbleToADDNewUser() {
		User userA = new User("test", "TesT1234!", Role.ADMIN, null);
		User userM = new User("test2", "TesT1234", Role.MANAGER, userA);
		UserDatabase udb = new UserDatabase(userA);
		
		udb.login("test", "TesT1234!");
		udb.addUser(userM);
		udb.login("test2", "TesT1234");
		
		//udb.addUser("test", "TesT1234!", Role.MANAGER, userA);	
		
		udb.addUser("user1", "password12", Role.MANAGER, userA);
		udb.addUser("user2", "password1122", Role.MANAGER, userM);
		
		
		String[] user = {"test","test2", "user1", "user2"};
		
		assertArrayEquals(user, udb.getAllUsernames());
	}
	
	@Test
	public void notSignedInUserIsNotAbleToADDUser() {
		User userA = new User("test", "TesT1234!", Role.ADMIN, null);
		User userW = new User("test2", "TesT1234", Role.WORKER, userA);
		User userW2 = new User("test3", "TesT1234", Role.WORKER, userA);
		UserDatabase udb = new UserDatabase(userA);
		
		udb.addUser("user1", "password12", Role.MANAGER, userA);
		
		
		
		udb.login("test2", "TesT1234!");
		udb.addUser(userW2);
		udb.login("test2", "TesT1234");
				
	}
	
	@Test
	public void userObjectsAreAbleToREADOUT() {
		User userA = new User("test", "TesT1234!", Role.ADMIN, null);
		User userW = new User("test2", "TesT1234", Role.WORKER, userA);
		User userM = new User("test3", "TesT1234", Role.MANAGER, userA);
		UserDatabase udb = new UserDatabase(userA);
		
		udb.login("test", "TesT1234!");
		
		ArrayList<User> userSet = new ArrayList<>(); // kein Set weil andere Reihenfolge beim Speichern
		userSet.add(userA);
		userSet.add(userW);
		userSet.add(userM);
		
		udb.addUser(userW);
		udb.addUser(userM);
				
		assertIterableEquals(userSet, udb);
		
		
	}
	
	@Test
	public void userObjectsAreAbleToREADOUTSame() {
		User userA = new User("test", "TesT1234!", Role.ADMIN, null);
		User userW = new User("test2", "TesT1234", Role.WORKER, userA);
		User userM = new User("test3", "TesT1234", Role.MANAGER, userA);
		UserDatabase udb = new UserDatabase(userA);
		
		udb.login("test", "TesT1234!");
		
		ArrayList<User> userSet = new ArrayList<>(); // kein Set weil andere Reihenfolge beim Speichern
		userSet.add(userA);
		userSet.add(userW);
		userSet.add(userM);
		
		udb.addUser(userW);
		udb.addUser(userM);
		
		int counter = 0;
		for (User u1: udb) {
			assertSame(userSet.get(counter), u1);
			counter++;
		}
		
		
	}
	
	
	@Test
	public void adminNotAbleToDeleteHimself() {
		User userA1 = new User("test", "TesT1234!", Role.ADMIN, null);
		User userA2 = new User("test2", "TesT1234!", Role.ADMIN, null);
		UserDatabase udb = new UserDatabase(userA1);
		udb.login("test", "TesT1234!");
		udb.addUser(userA2);
		udb.login("test2", "TesT1234!");
		udb.deleteUser("test");
		
	}
	
	@Test
	public void usersArePutOUTsorted() {
		User userA1 = new User("Boris", "TesT1234!", Role.ADMIN, null);
		User userM1 = new User("Alfred", "TesT1234!", Role.MANAGER, userA1);
		User userM2 = new User("Zeilinger", "TesT1234!", Role.MANAGER, userA1);
		User userM3 = new User("Jakob", "TesT1234!", Role.MANAGER, userA1);
		User userW1 = new User("Jan", "TesT1234!", Role.WORKER, userA1);
		User userW2 = new User("Benjamin", "TesT1234!", Role.WORKER, userA1);
		
		UserDatabase udb = new UserDatabase(userA1);
		udb.login("Boris", "TesT1234!");
		
		udb.addUser(userM1);
		udb.addUser(userM2);
		udb.addUser(userM3);
		udb.addUser(userW1);
		udb.addUser(userW2);
		
		udb.getAllUsernames();
		String[] expected = {"Alfred", "Benjamin", "Boris", "Jakob", "Jan", "Zeilinger"}; //ArrayList -> sortieren
		assertArrayEquals(expected, udb.getAllUsernames());
	}
}
