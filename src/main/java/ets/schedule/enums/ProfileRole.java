package ets.schedule.enums;

public enum ProfileRole {
    Admin("admin"),
    Instructor("instructor"),
    Student("student");
    
    private String role;

    ProfileRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static ProfileRole getRole(String role) {
        switch (role) {
            case "admin":
                return Admin;
            case "instructor":
                return Instructor;
            case "student":
                return Student;
            default:
                throw new IllegalArgumentException("Role given must be one of admin, instructor or student.");
        }
    }
}
