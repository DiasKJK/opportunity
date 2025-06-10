package com.diplom.diplom.configuration;

import com.diplom.diplom.features.authentication.model.AuthenticationUser;
import com.diplom.diplom.features.authentication.model.Role;
import com.diplom.diplom.features.authentication.repository.AuthenticationUserRepository;
import com.diplom.diplom.features.authentication.utils.Encoder;
import com.diplom.diplom.features.feed.model.Post;
import com.diplom.diplom.features.feed.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Configuration
public class LoadDatabaseConfiguration {
    private final Encoder encoder;

    public LoadDatabaseConfiguration(Encoder encoder) {
        this.encoder = encoder;
    }

    @Bean
    public CommandLineRunner initDatabase(AuthenticationUserRepository authenticationUserRepository, PostRepository postRepository) {
        return args -> {
            // Админ
            AuthenticationUser admin = new AuthenticationUser("dias@example.com", encoder.encode("dias"));
            admin.setRole(Role.ADMIN);
            admin.setFirstName("Dias");
            admin.setLastName("Adminov");
            admin.setGroup1("ADMIN-GROUP");
            admin.setStatus("Managing");
            admin.setAboutMe("System administrator");

            authenticationUserRepository.save(admin);

            List<AuthenticationUser> users = createUsers();
            authenticationUserRepository.saveAll(users);

            users.add(admin);

            createPosts(postRepository, users);
        };
    }

    private List<AuthenticationUser> createUsers() {
        List<AuthenticationUser> users = new ArrayList<>();

        users.add(createUser("john.doe@example.com", "password123", "John", "Doe",
                "IT-2132", "Employed", "Computer Science", "Computer Science 3 year", "+77011234567", "Kazakhstan",
                "I am a passionate backend developer."));

        users.add(createUser("jane.smith@example.com", "password123", "Jane", "Smith",
                "SE-2132", "Looking", "Software Engineering", "Software Engineering 2 year",
                "+77007007070", "Kazakhstan",
                "Front-end enthusiast and React lover."));

        return users;
    }

    private AuthenticationUser createUser(String email, String password, String firstName,
                                          String lastName, String groupName, String status,
                                          String group,
                                          String education,
                                          String contacts, String location, String aboutMe) {

        AuthenticationUser user = new AuthenticationUser(email, encoder.encode(password));
        user.setRole(Role.USER);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGroup1(groupName);
        user.setStatus(status);
        user.setGroup1(group);

        user.setEducation(education);
        user.setContacts(contacts);
        user.setLocation(location);

        user.setAboutMe(aboutMe);
//        user.setFavorites(new ArrayList<>());
//        user.setAppliedTo(new ArrayList<>());

        user.setProfilePicture("https://ui-avatars.com/api/?name=" + firstName + "+" + lastName + "&background=random");

        return user;
    }

    private void createPosts(PostRepository postRepository, List<AuthenticationUser> users) {
        Random random = new Random();
        for (int j = 1; j <= 10; j++) {
            Post post = new Post("Innovation in tech is moving faster than ever!", users.get(random.nextInt(users.size())));
            post.setLikes(generateLikes(users, j, random));
            if (j == 1) {
                post.setPicture("https://plus.unsplash.com/premium_photo-1675848495392-6b9a3b962df0?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
            }
            postRepository.save(post);
        }
    }

    private HashSet<AuthenticationUser> generateLikes(List<AuthenticationUser> users, int postNumber, Random random) {
        HashSet<AuthenticationUser> likes = new HashSet<>();

        int likesCount = (postNumber == 1) ? 3 : switch (postNumber % 5) {
            case 2, 3 -> 2;
            default -> 1;
        };

        while (likes.size() < likesCount) {
            likes.add(users.get(random.nextInt(users.size())));
        }

        return likes;
    }
}
