package net.ictcampus.baemtli.user;

import net.ictcampus.baemtli.security.Permission;

import java.util.List;

public enum Role {
    Coach(List.of(
            Permission.TRAINEE_WRITE_ALL,
            Permission.TRAINEE_READ_ALL,
            Permission.TEAM_WRITE_ALL,
            Permission.TEAM_READ_ALL,
            Permission.CHORE_CATEGORY_WRITE_ALL,
            Permission.CHORE_CATEGORY_READ_ALL,
            Permission.MONTH_ASSIGNMENT_WRITE_ALL,
            Permission.MONTH_ASSIGNMENT_READ_ALL,
            Permission.DAY_ASSIGNMENT_READ_ALL
    )),
    Teamresponsible(List.of(
            Permission.TRAINEE_READ_TEAM,
            Permission.TEAM_READ_ALL,
            Permission.CHORE_CATEGORY_READ_ALL,
            Permission.MONTH_ASSIGNMENT_READ_ALL,
            Permission.DAY_ASSIGNMENT_WRITE_TEAM,
            Permission.DAY_ASSIGNMENT_READ_TEAM
    )),
    Teammember(List.of(
            Permission.TRAINEE_READ_TEAM,
            Permission.TEAM_READ_ALL,
            Permission.CHORE_CATEGORY_READ_ALL,
            Permission.MONTH_ASSIGNMENT_READ_ALL,
            Permission.DAY_ASSIGNMENT_READ_TEAM
    ));

    private final List<String> permissions;

    Role(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getPermissions() {
        return permissions;
    }
}
