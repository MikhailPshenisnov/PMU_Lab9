namespace WishlistApi.Contracts.Requests;

public record CreateItemRequest(
    Guid UserId,
    string Title,
    double Price,
    string Link
);