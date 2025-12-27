namespace WishlistApi.Models;

public class ItemModel
{
    public Guid Id { get; set; }
    public Guid UserId { get; set; }
    public string Title { get; set; } = string.Empty;
    public double Price { get; set; }
    public string Link { get; set; } = string.Empty;
}