package ets.schedule.interfaces.services;

import ets.schedule.data.system.PasswordVerification;

public interface PasswordService {
    String applyCriptography(String rawPassword);
    Boolean matchPasswords(String rawPassword, String hashedPassword);
    PasswordVerification verifyPrerequisites(String rawPassword);
}
