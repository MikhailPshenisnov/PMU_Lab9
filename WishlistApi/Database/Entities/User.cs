namespace WishlistApi.Database.Entities;

public class User
{
    public Guid Id { get; set; } = Guid.NewGuid();
    public string Login { get; set; } = string.Empty;
    public string Password { get; set; } = string.Empty;
    public virtual ICollection<Item> Items { get; set; } = new List<Item>();
}