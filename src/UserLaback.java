import tgm.list.Role;
import tgm.list.User;
import tgm.list.exception.NotEnoughPrivilegesException;
import tgm.list.exception.PasswordTooWeakException;

public class UserLaback extends User{

	public UserLaback(String username, String password, Role role, User authorativeUser)
			throws NotEnoughPrivilegesException, PasswordTooWeakException {
		super(username, password, role, authorativeUser);
	}
	User u1 = new User("jlaback", "123jasd", Role.ADMIN, null);
	
	@Override
	public void checkPasswordStrength() {
		
	}
}
