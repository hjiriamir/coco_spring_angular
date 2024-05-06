package tn.esprit.coco.service;

import com.twilio.base.Page;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.coco.dto.GenderStatsDTO;
import tn.esprit.coco.dto.UserRoleStatsDTO;
import tn.esprit.coco.dto.request.ProfileUpdateRequest;

import tn.esprit.coco.dto.request.SignupRequest;
import tn.esprit.coco.dto.response.MessageResponse;
import tn.esprit.coco.entity.*;

import tn.esprit.coco.entity.ERole;
import tn.esprit.coco.entity.OrderProduct;
import tn.esprit.coco.entity.Role;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.OrderProductRepository;

import tn.esprit.coco.repository.RoleRepository;
import tn.esprit.coco.repository.UserRepository;
import tn.esprit.coco.serviceImp.IUserService;

import java.awt.print.Pageable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;



   /* @Autowired
    private OrderProductRepository orderProductRepository;*/


    ///////// list of users  (admin) ///////////////////+
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /*OrderProduct order = new OrderProduct();
    order.setAmount(0L);
    order.setTotalAmount(0L);
    order.setDiscount(0L);
    order.setUser(0L);
    order.setOrderStatus(OrderStatus.Pending);
    orderProductRepository.save(order);*/



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

///////////////////////////////////////////////////////////


           /////////////////////////update (user) //////////////////////////

    @Override
    public User updateUserProfile(String email, ProfileUpdateRequest profileUpdateRequest) {
        System.out.println("Update request received: " + profileUpdateRequest);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        // Update fields
        user.setUsername(profileUpdateRequest.getUsername());
        user.setAddress(profileUpdateRequest.getAddress());
        user.setDateOfBirth(profileUpdateRequest.getDateOfBirth());
        user.setPictureUrl(profileUpdateRequest.getPictureUrl());
        user.setPhoneNumber(profileUpdateRequest.getPhoneNumber());
        user.setProfilePicture(profileUpdateRequest.getProfilePicture());

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
    @Transactional
    public void updateUserRole(String roleName) {
        Role role = roleRepository.findByName(ERole.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        User currentUser = userDetailsService.getCurrentUser();
        currentUser.getRoles().clear();  // Clear existing roles
        currentUser.getRoles().add(role); // Add new role
        userRepository.save(currentUser);
    }



    public List<User> searchUsers(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.searchByTerm(searchTerm);
        }
    }

    public List<GenderStatsDTO> getGenderStatistics() {
        List<Object[]> results = userRepository.countUsersByGender();
        List<GenderStatsDTO> genderStats = results.stream()
                .map(result -> new GenderStatsDTO((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
        return genderStats;
    }

    /*public List<UserRoleStatsDTO> getUserRoleStatistics() {
        List<Object[]> results = userRepository.countUsersByRole();
        List<UserRoleStatsDTO> roleStats = results.stream()
                .map(result -> new UserRoleStatsDTO((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
        return roleStats;
    }*/
    public List<UserRoleStatsDTO> getUserRoleStatistics() {
        List<Object[]> results = userRepository.countUsersByRole();
        return results.stream()
                .map(result -> new UserRoleStatsDTO((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }


    public HashMap<String, String> forgetPassword(String email) {
        HashMap<String, String> message = new HashMap<>();

        User userexisting = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        userexisting.setPasswordResetToken(token);

        String resetLink = "http://localhost:4200/resetpassword/" + token;

        String mailContent = String.format(
                "Dear %s,\n\n" +
                        "You are receiving this email because a request has been made to reset the password for your account.\n\n" +
                        "To reset your password, please click on the following link:\n"
                        + resetLink + "\n\n"+
                        "If you did not request this password reset, please ignore this email.\n\n" +
                        "Thank you,\n" +
                        "Co&Co esprit",
                userexisting.getUsername(),
                resetLink
        );

        emailService.sendEmail(userexisting.getEmail(), "Reset Password", mailContent);

        userRepository.save(userexisting);
        message.put("user", "User found and email is sent");
        return message;
    }



    public HashMap<String,String> resetPassword(@PathVariable String passwordResetToken, String newPassword){
        User userexisting = userRepository.findByPasswordResetToken(passwordResetToken).orElseThrow(() -> new RuntimeException("User not found"));
        HashMap message = new HashMap();
        if (userexisting != null) {
            userexisting.setId(userexisting.getId());
            userexisting.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userexisting.setPasswordResetToken(null);
            userRepository.save(userexisting);
            message.put("resetpassword","succès");
            return message;
        }else
        {
            message.put("resetpassword","Échoué ");
            return message;
        }
    }


    public Long getFavoriteListId(Authentication authentication) {
        return null;
    }

    public FavoriteList getFavoriteListByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoriteList();
    }

////////////////////////////////












////////////sysy///////////




    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

















    ////////////////////////////////////arg3elha bech tnadhemha
    /*private Set<Role> determineRoles(SignupRequest signUpRequest) {
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        String domain = signUpRequest.getEmail().substring(signUpRequest.getEmail().indexOf("@") + 1);

        if ("esprit.tn".equals(domain)) {
            if ("admin@esprit.tn".equalsIgnoreCase(signUpRequest.getEmail()) || "admin2@esprit.tn".equalsIgnoreCase(signUpRequest.getEmail())) {
                Role adminRole = roleRepository.findByName(ERole.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role ADMIN not found."));
                roles.add(adminRole);
            } else if (strRoles == null || strRoles.isEmpty()) {
                Role userRole = roleRepository.findByName(ERole.USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role USER not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role.toUpperCase()) {
                        case "USER":
                            Role userRole = roleRepository.findByName(ERole.USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role USER not found."));
                            roles.add(userRole);
                            break;
                        case "DRIVER":
                            Role driverRole = roleRepository.findByName(ERole.DRIVER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role DRIVER not found."));
                            roles.add(driverRole);
                            break;
                        case "PASSENGER":
                            Role passengerRole = roleRepository.findByName(ERole.PASSENGER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role PASSENGER not found."));
                            roles.add(passengerRole);
                            break;
                        case "HOST":
                            Role hostRole = roleRepository.findByName(ERole.HOST)
                                    .orElseThrow(() -> new RuntimeException("Error: Role HOST not found."));
                            roles.add(hostRole);
                            break;
                        case "ROOMSEEKER":
                            Role roomSeekerRole = roleRepository.findByName(ERole.ROOMSEEKER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role ROOMSEEKER not found."));
                            roles.add(roomSeekerRole);
                            break;
                        default:

                            throw new RuntimeException("Error: Invalid role specified.");
                    }
                });
            }
        } else {
            Role externalUserRole = roleRepository.findByName(ERole.EXTERNAL_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role EXTERNAL_USER not found."));
            roles.add(externalUserRole);
        }



        return roles;
    }
    ///////////////////////////////////

    private void sendWelcomeEmail(User user) {
        String welcomeMessage = String.format(
                "Hey there, %s! 🌟\n\n" +
                        "A huge, warm welcome to the CoCo Esprit family! We're absolutely buzzing with excitement to have you join us. It’s time to embark on a spectacular journey filled with endless possibilities, and it all starts now.\n\n" +
                        "🚀 Kick-off Your Adventure:\n" +
                        "- Discover and connect! There’s a whole world out there in CoCo Esprit waiting for you.\n" +
                        "- 💌 Got a question or a cool idea? Jump into our forums or drop us a message. We love hearing from our community!\n\n" +
                        "🌈 Stay in the Loop:\n" +
                        "Follow our social media channels to get the latest scoops, tips, and sparkles from CoCo Esprit. Let’s keep the conversation glowing!\n\n" +
                        "We’re so thrilled to welcome you aboard. Let’s make some magic happen together! ✨\n\n" +
                        "With all the excitement,\n" +
                        "Your pals at CoCo Esprit 💖",
                user.getUsername());
        emailService.sendEmail(user.getEmail(), "Welcome to the CoCo Esprit Adventure!", welcomeMessage);
    }
    ////////////////////////////////
    public User registerUser(SignupRequest signUpRequest) {
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        User user = createUser(signUpRequest);
        user.setRoles(roles);
        userRepository.save(user);
        sendWelcomeEmail(user);
        return user;
    }

    private User createUser(SignupRequest signUpRequest) {
        return User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .gender(Gender.valueOf(signUpRequest.getGender().toUpperCase()))
                .address(signUpRequest.getAddress())
                .dateOfBirth(signUpRequest.getDateOfBirth())
                .pictureUrl(signUpRequest.getPictureUrl())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .build();
    }*/

}
