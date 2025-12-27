namespace WishlistApi.Interfaces.Services;

public interface IAuthorizationService
{
    Task<bool> RegisterUser(string login, string password, CancellationToken ct);
    Task<bool> LoginUser(string login, string password, CancellationToken ct);
}