package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.coco.dto.request.ProfileUpdateRequest;
import tn.esprit.coco.entity.ERole;
import tn.esprit.coco.entity.Role;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.RoleRepository;
import tn.esprit.coco.repository.UserRepository;
import tn.esprit.coco.serviceImp.IUserService;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    ///////// list of users  (admin) ///////////////////+
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    ///////////// delete  user (admin) /////////////+

    public void deleteUser(Long userId, String requestingUserEmail) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (requestingUserEmail.equals(user.getEmail()) || isAdmin(requestingUserEmail)) {
            userRepository.deleteById(userId);
        } else {
            throw new RuntimeException("You are not authorized to delete this user.");
        }
    }

    ///////////// update  user (admin) /////////////mazelt na9sa
    @Override
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));

        user.setUsername(userDetails.getUsername());
        user.setGender(userDetails.getGender());
        user.setAddress(userDetails.getAddress());
        user.setDateOfBirth(userDetails.getDateOfBirth());
        user.setPictureUrl(userDetails.getPictureUrl());

        return userRepository.save(user);
    }


    private boolean isAdmin(String email) {
        return userRepository.findByEmail(email)
                .map(User::getRoles)
                .orElse(Collections.emptySet())
                .stream()
                .anyMatch(role -> role.getName() == ERole.ADMIN);
    }


           //////////////////////show profile (user)  ///////////////////////////+
           @Override
           public User getUserByEmail(String email) {
               return userRepository.findByEmail(email)
                       .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
           }




           /////////////////////////update (user) //////////////////////////+
    @Override
    public User updateUserProfile(String email, ProfileUpdateRequest profileUpdateRequest) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        // Update user details
        user.setUsername(profileUpdateRequest.getUsername());
        user.setAddress(profileUpdateRequest.getAddress());
        user.setDateOfBirth(profileUpdateRequest.getDateOfBirth());
        user.setPictureUrl(profileUpdateRequest.getPictureUrl());

        //
        if (profileUpdateRequest.getRoles() != null && !profileUpdateRequest.getRoles().isEmpty()) {
            Set<Role> newRoles = profileUpdateRequest.getRoles().stream()
                    .map(roleName -> roleRepository.findByName(ERole.valueOf(roleName))
                            .orElseThrow(() -> new RuntimeException("Error: Role " + roleName + " not found.")))
                    .collect(Collectors.toSet());

            user.setRoles(newRoles);
        }

        return userRepository.save(user);
    }

}
