namespace WishlistApi.Contracts.Requests;

public record RegisterUserRequest(
    string Login,
    string Password
);