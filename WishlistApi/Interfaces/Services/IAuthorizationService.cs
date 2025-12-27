namespace WishlistApi.Interfaces.Services;

public interface IAuthorizationService
{
    Task<Guid?> RegisterUser(string login, string password, CancellationToken ct);
    Task<Guid?> LoginUser(string login, string password, CancellationToken ct);
}