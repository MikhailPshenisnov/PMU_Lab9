using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using WishlistApi.Database.Entities;

namespace WishlistApi.Database.Configurations;

public class ItemConfiguration : IEntityTypeConfiguration<Item>
{
    public void Configure(EntityTypeBuilder<Item> builder)
    {
        builder.HasKey(i => i.Id);

        builder.Property(i => i.UserId).IsRequired();
        builder.Property(i => i.Title).IsRequired().HasMaxLength(128);
        builder.Property(i => i.Price).IsRequired();
        builder.Property(i => i.Link).IsRequired();

        builder.HasOne(i => i.User)
            .WithMany(u => u.Items)
            .HasForeignKey(i => i.UserId)
            .OnDelete(DeleteBehavior.Cascade);
    }
}