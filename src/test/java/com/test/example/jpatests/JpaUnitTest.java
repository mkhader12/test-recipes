package com.test.example.jpatests;

import com.test.example.db.User;
import com.test.example.db.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaUnitTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    public void shouldFindNoDataInitially() {
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(6);
    }

    @Test
    public void shouldSaveUser() {
        User savedUser = userRepository.save(new User("Mohamed", "Khader"));
        assertThat(savedUser).hasFieldOrPropertyWithValue("firstName", "Mohamed");
        assertThat(savedUser).hasFieldOrPropertyWithValue("lastName", "Khader");
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(7);
    }

    @Test
    public void shouldFindAllPersistedUsers() {
        userRepository.deleteAll();

        Iterable<User> users = userRepository.findAll();
        assertThat(users).isEmpty();

        User usr1 = new User("FirstName#1", "Last Name#1");
        entityManager.persist(usr1);

        User usr2 = new User("FirstName#2", "Last Name#2");
        entityManager.persist(usr2);

        User usr3 = new User("FirstName#3", "Last Name#3");
        entityManager.persist(usr3);

        users = userRepository.findAll();
        assertThat(users).hasSize(3).contains(usr1, usr2, usr3);
    }
}
