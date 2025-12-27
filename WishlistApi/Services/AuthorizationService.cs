using WishlistApi.Interfaces.Services;
using WishlistApi.Models;

namespace WishlistApi.Services;

public class AuthorizationService(IUsersService usersService) : IAuthorizationService
{
    public async Task<Guid?> RegisterUser(string login, string password, CancellationToken ct)
    {
        var existedUser = (await usersService.GetAllUsers(ct))
            .FirstOrDefault(u => u.Login == login);

        if (existedUser is not null) return null;

        var createdUserId = await usersService
            .CreateUser(new UserModel { Id = Guid.NewGuid(), Login = login, Password = password }, ct);

        return createdUserId;
    }

    public async Task<Guid?> LoginUser(string login, string password, CancellationToken ct)
    {
        var existedUser = (await usersService.GetAllUsers(ct))
            .FirstOrDefault(u => u.Login == login);

        if (existedUser is null) return null;

        if (existedUser.Password != password) return null;
        
        return existedUser.Id;
    }
}