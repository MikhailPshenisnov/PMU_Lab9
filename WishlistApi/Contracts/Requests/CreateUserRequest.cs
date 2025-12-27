namespace WishlistApi.Contracts.Requests;

public record CreateUserRequest(
    string Login,
    string Password
);