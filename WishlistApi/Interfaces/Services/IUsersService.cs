using WishlistApi.Models;

namespace WishlistApi.Interfaces.Services;

public interface IUsersService
{
    Task<Guid?> CreateUser(UserModel user, CancellationToken ct);
    Task<List<UserModel>> GetAllUsers(CancellationToken ct);
    Task<UserModel?> GetUserById(Guid userId, CancellationToken ct);
}