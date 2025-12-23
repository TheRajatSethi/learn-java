package com.rs.stocks.users;

import com.rs.stocks.models.UserModel;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.jetbrains.annotations.NotNull;

public class Main {
    public static void main(String[] args) {
        D.initializeDependencies();
        var app = Javalin.create(/*config*/);

        // Setting up routes.
        app.post("/user", new RegisterUserHandler(D.getJdbi()));
        app.post("/user/authenticate", new AuthenticateUserHandler(D.getJdbi()));

        app.start();
    }
}

@Data
class UserRequest {
    String email;
    String password;
}

@RequiredArgsConstructor
class RegisterUserHandler implements Handler {
    private final Jdbi jdbi;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {

        UserRequest userRequest;
        try {
            userRequest = ctx.bodyAsClass(UserRequest.class);
        } catch (Exception e) {
            System.out.println(ctx.body());
            ctx.status(HttpStatus.BAD_REQUEST);
            return;
        }

        var existingUser = this.jdbi.withExtension(UserDao.class, dao -> dao.getUserByEmail(userRequest.getEmail()));
        System.out.println(existingUser);

        if (existingUser.isPresent()) {
            ctx.status(HttpStatus.CONFLICT);
            ctx.result("User already exists");
            return;
        }

        var newUser = new UserModel(null, userRequest.getEmail(), userRequest.getPassword(), userRequest.getEmail(), null);
        Integer id = this.jdbi.withExtension(UserDao.class, dao -> dao.insertUser(newUser));
        newUser.setId(id);

        ctx.json(newUser);

    }
}

@RequiredArgsConstructor
class AuthenticateUserHandler implements Handler {
    private final Jdbi jdbi;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        System.out.println("in handle");
        System.out.println(this.jdbi);
    }
}

@RequiredArgsConstructor
class GetUserHandler implements Handler {
    private final Jdbi jdbi;

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        var email = ctx.queryParamAsClass("email", String.class).getOrDefault("");
        var email2 = ctx.pathParam("email");
    }
}