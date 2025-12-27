namespace WishlistApi.Contracts.Requests;

public record UpdateItemRequest(
    Guid UserId,
    string Title,
    double Price,
    string Link
);