namespace WishlistApi.Contracts.Requests;

public record LoginUserRequest(
    string Login,
    string Password
);