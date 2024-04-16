package tn.esprit.coco.serviceImp;

import tn.esprit.coco.dto.request.ProfileUpdateRequest;
import tn.esprit.coco.entity.Role;
import tn.esprit.coco.entity.User;

import java.util.List;
import java.util.Set;

public interface IUserService {
    List<User> getAllUsers();

    void deleteUser(Long userId, String requestingUserEmail);

    void updateUserRole(String email, Set<Role> newRoles);

    User getUserByEmail(String email);

    User updateUserProfile(String email, ProfileUpdateRequest profileUpdateRequest);
    boolean changePassword(String email, String oldPassword, String newPassword);
    List<User> searchUsers(String searchTerm);

}
