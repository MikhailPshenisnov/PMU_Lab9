using WishlistApi.Models;

namespace WishlistApi.Interfaces.Repositories;

public interface IItemsRepository
{
    Task<Guid> CreateItem(ItemModel item, CancellationToken ct);
    Task<List<ItemModel>> GetAllItems(CancellationToken ct);
    Task<Guid> UpdateItem(Guid itemId, ItemModel newItem, CancellationToken ct);
    Task<Guid> DeleteItem(Guid itemId, CancellationToken ct);
}