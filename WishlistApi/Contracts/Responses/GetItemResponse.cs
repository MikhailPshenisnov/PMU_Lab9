using WishlistApi.Models;

namespace WishlistApi.Contracts.Responses;

public record GetItemResponse(
    ItemModel Item
);