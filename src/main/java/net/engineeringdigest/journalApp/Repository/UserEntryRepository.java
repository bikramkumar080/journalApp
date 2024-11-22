package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntryRepository extends JpaRepository<User, String> {
    User findByUsername(String userName);
}
