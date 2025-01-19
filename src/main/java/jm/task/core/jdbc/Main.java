package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Таня", "Пон", (byte)27);
        userService.saveUser("Леха", "Пон", (byte)30);
        userService.saveUser("Алина", "Сам", (byte)29);
        userService.saveUser("Катя", "Белк", (byte)40);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
