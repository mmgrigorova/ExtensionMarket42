package services.users;

import com.antman.extensionmarket42.Role;
import com.antman.extensionmarket42.models.User;
import com.antman.extensionmarket42.models.UserProfile;
import com.antman.extensionmarket42.models.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserDataSetup {
    public static User setupUser(String firstName, String lastName, String email, String password){
        UserProfile profile = new UserProfile();
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setEmail(email);

        UserRole role = new UserRole();
        role.setRole(String.valueOf(Role.DEV));

        List<UserRole> roles = new ArrayList<>();
        roles.add(role);

        User user = new User(email, password, profile);
        user.setEnabled(1);
        user.setUserRoles(roles);

        return user;
    }
}
