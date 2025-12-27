using WishlistApi.Models;

namespace WishlistApi.Contracts.Responses;

public record GetUserResponse(
    UserModel User
);