package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


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

    @Override
    public void updateUserRole(String email, Set<Role> newRoles) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        user.setRoles(newRoles);
        userRepository.save(user);
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

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent() && passwordEncoder.matches(oldPassword, userOptional.get().getPassword())) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }


}
