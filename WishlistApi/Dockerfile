FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
WORKDIR /src

COPY ["WishlistApi.csproj", "./"]
RUN dotnet restore "WishlistApi.csproj"

COPY . .
RUN dotnet build "WishlistApi.csproj" -c Release -o /app/build

RUN dotnet publish "WishlistApi.csproj" -c Release -o /app/publish /p:UseAppHost=false

FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS final
WORKDIR /app
COPY --from=build /app/publish .

ENTRYPOINT ["dotnet", "WishlistApi.dll"]