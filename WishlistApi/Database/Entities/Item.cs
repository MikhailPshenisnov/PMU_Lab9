namespace WishlistApi.Database.Entities;

public class Item
{
    public Guid Id { get; set; } = Guid.NewGuid();
    public Guid UserId { get; set; }
    public string Title { get; set; } = string.Empty;
    public double Price { get; set; }
    public string Link { get; set; } = string.Empty;
    public virtual User User { get; set; } = null!;
}