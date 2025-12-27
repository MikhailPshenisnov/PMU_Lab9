using WishlistApi.Interfaces.Repositories;
using WishlistApi.Interfaces.Services;
using WishlistApi.Models;

namespace WishlistApi.Services;

public class ItemsService(IItemsRepository itemsRepository) : IItemsService
{
    public async Task<Guid?> CreateItem(ItemModel item, CancellationToken ct)
    {
        try
        {
            var createdItemId = await itemsRepository.CreateItem(item, ct);

            return createdItemId;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return null;
        }
    }

    public async Task<List<ItemModel>> GetAllItems(CancellationToken ct)
    {
        try
        {
            var items = await itemsRepository.GetAllItems(ct);

            return items;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return [];
        }
    }

    public async Task<ItemModel?> GetItemById(Guid itemId, CancellationToken ct)
    {
        try
        {
            var allItems = await itemsRepository.GetAllItems(ct);

            var item = allItems.FirstOrDefault(i => i.Id == itemId);

            if (item == null)
                throw new Exception("Unknown item id");

            return item;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return null;
        }
    }

    public async Task<Guid?> UpdateItem(Guid itemId, ItemModel newItem, CancellationToken ct)
    {
        try
        {
            var updatedItemId = await itemsRepository.UpdateItem(itemId, newItem, ct);

            return updatedItemId;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return null;
        }
    }

    public async Task<Guid?> DeleteItem(Guid itemId, CancellationToken ct)
    {
        try
        {
            var deletedItemId = await itemsRepository.DeleteItem(itemId, ct);

            return deletedItemId;
        }
        catch (Exception e)
        {
            Console.WriteLine(e);
            return null;
        }
    }
}