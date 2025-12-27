using Microsoft.AspNetCore.Mvc;
using Swashbuckle.AspNetCore.Annotations;
using WishlistApi.Contracts.Requests;
using WishlistApi.Contracts.Responses;
using WishlistApi.Interfaces.Services;
using WishlistApi.Models;

namespace WishlistApi.Controllers.V1;

[ApiController]
[ApiVersion("1.0")]
[Route("api/v{version:apiVersion}/[controller]/[action]")]
public class ItemsController(IItemsService itemsService) : ControllerBase
{
    [HttpGet]
    [SwaggerOperation(OperationId = "GetAllItems")]
    public async Task<ActionResult<GetAllItemsResponse>> GetAllItems(CancellationToken ct)
    {
        var items = await itemsService
            .GetAllItems(ct);

        return Ok(new GetAllItemsResponse(items));
    }

    [HttpGet("{itemId:guid}")]
    [SwaggerOperation(OperationId = "GetItemById")]
    public async Task<ActionResult<GetItemResponse>> GetItem(Guid itemId, CancellationToken ct)
    {
        var item = await itemsService
            .GetItemById(itemId, ct);

        if (item == null) return NotFound();

        return Ok(new GetItemResponse(item));
    }

    [HttpPost]
    [SwaggerOperation(OperationId = "CreateItem")]
    public async Task<ActionResult<CreateItemResponse>> CreateItem([FromBody] CreateItemRequest request,
        CancellationToken ct)
    {
        var itemToCreate = new ItemModel
        {
            Id = Guid.NewGuid(),
            UserId = request.UserId,
            Title = request.Title,
            Price = request.Price,
            Link = request.Link
        };

        var createdItemId = await itemsService
            .CreateItem(itemToCreate, ct);

        if (createdItemId is null) return BadRequest();
        
        return Ok(new CreateItemResponse(createdItemId.Value));
    }

    [HttpPut("{itemId:guid}")]
    [SwaggerOperation(OperationId = "UpdateItem")]
    public async Task<ActionResult<UpdateItemResponse>> UpdateItem(Guid itemId, [FromBody] UpdateItemRequest request,
        CancellationToken ct)
    {
        var itemToUpdate = await itemsService
            .GetItemById(itemId, ct);

        if (itemToUpdate == null) return NotFound();

        var newItem = new ItemModel
        {
            Id = Guid.NewGuid(),
            UserId = request.UserId,
            Title = request.Title,
            Price = request.Price,
            Link = request.Link
        };

        var updatedItemId = await itemsService
            .UpdateItem(itemId, newItem, ct);

        if (updatedItemId == null) return BadRequest();

        return Ok(new UpdateItemResponse(updatedItemId.Value));
    }

    [HttpDelete("{itemId:guid}")]
    [SwaggerOperation(OperationId = "DeleteItem")]
    public async Task<ActionResult<DeleteItemResponse>> DeleteItem(Guid itemId, CancellationToken ct)
    {
        var itemToDelete = await itemsService
            .GetItemById(itemId, ct);

        if (itemToDelete == null) return NotFound();

        var deletedItemId = await itemsService
            .DeleteItem(itemId, ct);

        if (deletedItemId == null) return BadRequest();

        return Ok(new DeleteItemResponse(deletedItemId.Value));
    }
}