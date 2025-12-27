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
public class UsersController(IUsersService usersService) : ControllerBase
{
    [HttpGet]
    [SwaggerOperation(OperationId = "GetAllUsers")]
    public async Task<ActionResult<GetAllUsersResponse>> GetAllUsers(CancellationToken ct)
    {
        var users = await usersService
            .GetAllUsers(ct);

        return Ok(new GetAllUsersResponse(users));
    }

    [HttpGet("{userId:guid}")]
    [SwaggerOperation(OperationId = "GetUserById")]
    public async Task<ActionResult<GetUserResponse>> GetUser(Guid userId, CancellationToken ct)
    {
        var user = await usersService
            .GetUserById(userId, ct);

        if (user == null) return NotFound();

        return Ok(new GetUserResponse(user));
    }

    [HttpPost]
    [SwaggerOperation(OperationId = "CreateUser")]
    public async Task<ActionResult<CreateUserResponse>> CreateUser([FromBody] CreateUserRequest request,
        CancellationToken ct)
    {
        var userToCreate = new UserModel
        {
            Id = Guid.NewGuid(),
            Login = request.Login,
            Password = request.Password
        };

        var createdUserId = await usersService
            .CreateUser(userToCreate, ct);

        if (createdUserId == null) return BadRequest();

        return Ok(new CreateUserResponse(createdUserId.Value));
    }
}