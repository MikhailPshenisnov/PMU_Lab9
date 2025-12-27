using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using WishlistApi.Database.Entities;

namespace WishlistApi.Database.Configurations;

public class UserConfiguration : IEntityTypeConfiguration<User>
{
    public void Configure(EntityTypeBuilder<User> builder)
    {
        builder.HasKey(u => u.Id);

        builder.Property(u => u.Login).IsRequired().HasMaxLength(32);
        builder.Property(u => u.Password).IsRequired().HasMaxLength(32);

        builder.HasIndex(u => u.Login).IsUnique();
    }
}