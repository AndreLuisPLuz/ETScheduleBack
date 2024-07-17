package ets.schedule.data.responses.user;

import java.util.Date;

import ets.schedule.data.system.PasswordVerification;
import ets.schedule.models.Users;

public sealed interface UserUpdateResponse {
    public final class Ok implements UserUpdateResponse {
        private String username;
        private String fullName;
        private Date birthDate;

        public Ok(
                String username,
                String fullName,
                Date birthDate
        ) {
            this.username = username;
            this.fullName = fullName;
            this.birthDate = birthDate;
        }

        public static UserUpdateResponse.Ok buildFromEntity(Users entity) {
            return new Ok(
                entity.getUsername(),
                entity.getFullName(),
                entity.getBirthDate()
            );
        }

        public String getUsername() {
            return username;
        }

        public String getFullName() {
            return fullName;
        }

        public Date getBirthDate() {
            return birthDate;
        }
    }

    public final class Error implements UserUpdateResponse {
        private PasswordVerification verification;

        public Error(PasswordVerification verification) {
            this.verification = verification;
        }

        public PasswordVerification getVerification() {
            return verification;
        }
    }
}
