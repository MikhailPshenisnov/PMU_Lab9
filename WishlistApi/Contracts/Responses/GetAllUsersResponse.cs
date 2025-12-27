using WishlistApi.Models;

namespace WishlistApi.Contracts.Responses;

public record GetAllUsersResponse(
    List<UserModel> Users
);