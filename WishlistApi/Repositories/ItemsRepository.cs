using Microsoft.EntityFrameworkCore;
using WishlistApi.Database.Context;
using WishlistApi.Database.Entities;
using WishlistApi.Interfaces.Repositories;
using WishlistApi.Models;

namespace WishlistApi.Repositories;

public class ItemsRepository(ApplicationDbContext context) : IItemsRepository
{
    public async Task<Guid> CreateItem(ItemModel item, CancellationToken ct)
    {
        var itemEntity = new Item
        {
            UserId = item.UserId,
            Title = item.Title,
            Price = item.Price,
            Link = item.Link
        };

        await context.Items.AddAsync(itemEntity, ct);
        await context.SaveChangesAsync(ct);

        return itemEntity.Id;
    }

    public async Task<List<ItemModel>> GetAllItems(CancellationToken ct)
    {
        var itemEntities = await context.Items
            .AsNoTracking()
            .ToListAsync(ct);

        var items = itemEntities
            .Select(itemEntity => new ItemModel
            {
                Id = itemEntity.Id,
                UserId = itemEntity.UserId,
                Title = itemEntity.Title,
                Price = itemEntity.Price,
                Link = itemEntity.Link
            })
            .ToList();

        return items;
    }

    public async Task<Guid> UpdateItem(Guid itemId, ItemModel newItem, CancellationToken ct)
    {
        var oldItemEntity = await context.Items
            .AsNoTracking()
            .FirstOrDefaultAsync(i => i.Id == itemId, ct);

        if (oldItemEntity is null)
            throw new Exception("Unknown item id");

        await context.Items
            .Where(i => i.Id == itemId)
            .ExecuteUpdateAsync(x => x
                    .SetProperty(i => i.UserId, i => newItem.UserId)
                    .SetProperty(i => i.Title, i => newItem.Title)
                    .SetProperty(i => i.Price, i => newItem.Price)
                    .SetProperty(i => i.Link, i => newItem.Link),
                ct);

        return oldItemEntity.Id;
    }

    public async Task<Guid> DeleteItem(Guid itemId, CancellationToken ct)
    {
        var numDeleted = await context.Items
            .Where(i => i.Id == itemId)
            .ExecuteDeleteAsync(ct);

        if (numDeleted == 0)
            throw new Exception("Unknown item id");

        return itemId;
    }
}