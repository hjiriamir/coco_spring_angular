package tn.esprit.coco.serviceImp;

import tn.esprit.coco.dto.request.ProfileUpdateRequest;
import tn.esprit.coco.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getAllUsers();

    void deleteUser(Long userId, String requestingUserEmail);

    User updateUser(Long id, User userDetails);
    User getUserByEmail(String email);

    User updateUserProfile(String email, ProfileUpdateRequest profileUpdateRequest);

}
