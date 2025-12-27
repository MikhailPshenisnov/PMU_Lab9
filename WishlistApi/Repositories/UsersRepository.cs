using Microsoft.EntityFrameworkCore;
using WishlistApi.Database.Context;
using WishlistApi.Database.Entities;
using WishlistApi.Interfaces.Repositories;
using WishlistApi.Models;

namespace WishlistApi.Repositories;

public class UsersRepository(ApplicationDbContext context) : IUsersRepository
{
    public async Task<Guid> CreateUser(UserModel user, CancellationToken ct)
    {
        var userEntity = new User
        {
            Login = user.Login,
            Password = user.Password
        };

        await context.Users.AddAsync(userEntity, ct);
        await context.SaveChangesAsync(ct);

        return userEntity.Id;
    }

    public async Task<List<UserModel>> GetAllUsers(CancellationToken ct)
    {
        var userEntities = await context.Users
            .AsNoTracking()
            .ToListAsync(ct);

        var users = userEntities
            .Select(userEntity => new UserModel
            {
                Id = userEntity.Id,
                Login = userEntity.Login,
                Password = userEntity.Password
            })
            .ToList();

        return users;
    }

    public async Task<Guid> UpdateUser(Guid userId, UserModel newUser, CancellationToken ct)
    {
        var oldUserEntity = await context.Users
            .AsNoTracking()
            .FirstOrDefaultAsync(u => u.Id == userId, ct);

        if (oldUserEntity is null)
            throw new Exception("Unknown user id");

        await context.Users
            .Where(u => u.Id == userId)
            .ExecuteUpdateAsync(x => x
                    .SetProperty(u => u.Login, u => newUser.Login)
                    .SetProperty(u => u.Password, u => newUser.Password),
                ct);

        return oldUserEntity.Id;
    }

    public async Task<Guid> DeleteUser(Guid userId, CancellationToken ct)
    {
        var numDeleted = await context.Users
            .Where(u => u.Id == userId)
            .ExecuteDeleteAsync(ct);

        if (numDeleted == 0)
            throw new Exception("Unknown user id");

        return userId;
    }
}