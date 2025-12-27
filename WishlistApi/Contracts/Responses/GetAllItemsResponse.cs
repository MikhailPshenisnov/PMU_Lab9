using WishlistApi.Models;

namespace WishlistApi.Contracts.Responses;

public record GetAllItemsResponse(
    List<ItemModel> Items
);