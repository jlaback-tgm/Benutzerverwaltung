import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tgm.list.Role;
import tgm.list.User;
import tgm.list.exception.NotEnoughPrivilegesException;
import tgm.list.exception.PasswordTooWeakException;

public class Testklasse {
	
	@Test
//	@DisplayName("");
	void addBenutzer() {
		User user = new User("test", "TesT1234!", tgm.list.Role.MANAGER, null);
		assertEquals("test", user.getUsername());
		assertEquals("test", user.getAuthorativeUsername());
		assertEquals(tgm.list.Role.MANAGER, user.getRole());
		System.out.println(user.getUsername());
		System.out.println(user.getAuthorativeUsername());
		System.out.println(user.getRole());

	}
	
	@Test
	void verifyPassword() {
		User user = new User("test", "TesT1234!", tgm.list.Role.MANAGER, null);
		assertTrue(user.verifyPassword("TesT1234!"));
		assertFalse(user.verifyPassword("TesT1234"));
		System.out.println(user.verifyPassword("TesT1234!"));
		System.out.println(user.verifyPassword("qwedasd!"));
	
	}
	
	@Test
	void changePasswordFromUser() {
		User user = new User("test", "TesT1234!", tgm.list.Role.MANAGER, null);
		user.changePassword("ZHabc89X12345%!", "TesT1234!");
		assertTrue(user.verifyPassword("ZHabc89X12345"));
		System.out.println(user.verifyPassword("ZHabc89X12345"));
	}
	
	@Test
	void resetPasswort() {
		User user1 = new User("test1", "Tes234!", tgm.list.Role.ADMIN, null);
		User user2 = new User("test2", "Test1234!", tgm.list.Role.MANAGER, user1);
		
		user2.resetPassword("ZHabc89X12345%", user1.getUsername(), "Tes234!");
		assertTrue(user2.verifyPassword("ZHabc89X12345%"));
	}
	
	@Test
	void noLowPasswordAccepted() {
		assertThrows(PasswordTooWeakException.class, () -> new User("test", "abc", Role.MANAGER, null));
	}
	
	@Test
	void noAuthorativeUserWithRoleWORKER() {
		User user1 = new User("test1", "hallo", Role.WORKER, null);
		//User user2 = new User("test", "Test1234!", Role.VISITOR, user1);
		assertThrows(NotEnoughPrivilegesException.class, () -> new User("test", "Test1234!", Role.VISITOR, user1));
	}
	
}
