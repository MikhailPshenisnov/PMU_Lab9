using WishlistApi.Models;

namespace WishlistApi.Interfaces.Repositories;

public interface IUsersRepository
{
    Task<Guid> CreateUser(UserModel user, CancellationToken ct);
    Task<List<UserModel>> GetAllUsers(CancellationToken ct);
    Task<Guid> UpdateUser(Guid userId, UserModel newUser, CancellationToken ct);
    Task<Guid> DeleteUser(Guid userId, CancellationToken ct);
}