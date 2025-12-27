using WishlistApi.Models;

namespace WishlistApi.Interfaces.Services;

public interface IItemsService
{
    Task<Guid?> CreateItem(ItemModel item, CancellationToken ct);
    Task<List<ItemModel>> GetAllItems(CancellationToken ct);
    Task<ItemModel?> GetItemById(Guid itemId, CancellationToken ct);
    Task<Guid?> UpdateItem(Guid itemId, ItemModel newItem, CancellationToken ct);
    Task<Guid?> DeleteItem(Guid itemId, CancellationToken ct);
}