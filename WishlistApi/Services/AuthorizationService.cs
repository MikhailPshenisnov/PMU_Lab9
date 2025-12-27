using WishlistApi.Interfaces.Services;
using WishlistApi.Models;

namespace WishlistApi.Services;

public class AuthorizationService(IUsersService usersService) : IAuthorizationService
{
    public async Task<bool> RegisterUser(string login, string password, CancellationToken ct)
    {
        var existedUser = (await usersService.GetAllUsers(ct))
            .FirstOrDefault(u => u.Login == login);

        if (existedUser is not null) return false;

        var createdUserId = await usersService
            .CreateUser(new UserModel { Id = Guid.NewGuid(), Login = login, Password = password }, ct);

        return createdUserId is not null;
    }

    public async Task<bool> LoginUser(string login, string password, CancellationToken ct)
    {
        var existedUser = (await usersService.GetAllUsers(ct))
            .FirstOrDefault(u => u.Login == login);

        if (existedUser is null) return false;

        return existedUser.Password == password;
    }
}