package ets.schedule.sessions;

import ets.schedule.enums.ProfileRole;

public class UserSession {
    private Long userId;
    private ProfileRole profileRole;

    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ProfileRole getProfileRole() {
        return profileRole;
    }
    
    public void setProfileRole(ProfileRole profileRole) {
        this.profileRole = profileRole;
    }
}
