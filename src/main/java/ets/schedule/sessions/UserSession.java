package ets.schedule.sessions;

import ets.schedule.enums.ProfileRole;

public class UserSession {
    private Long userId;
    private Long profileId;
    private ProfileRole profileRole;

    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public ProfileRole getProfileRole() {
        return profileRole;
    }
    
    public void setProfileRole(ProfileRole profileRole) {
        this.profileRole = profileRole;
    }
}
