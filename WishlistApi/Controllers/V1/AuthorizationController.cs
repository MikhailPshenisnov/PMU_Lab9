using Microsoft.AspNetCore.Mvc;
using Swashbuckle.AspNetCore.Annotations;
using WishlistApi.Contracts.Requests;
using WishlistApi.Contracts.Responses;
using IAuthorizationService = WishlistApi.Interfaces.Services.IAuthorizationService;


namespace WishlistApi.Controllers.V1;

[ApiController]
[ApiVersion("1.0")]
[Route("api/v{version:apiVersion}/[controller]/[action]")]
public class AuthorizationController(IAuthorizationService authorizationService) : ControllerBase
{
    [HttpPost]
    [SwaggerOperation(OperationId = "RegisterUser")]
    public async Task<ActionResult<RegisterUserResponse>> RegisterUser([FromBody] RegisterUserRequest request,
        CancellationToken ct)
    {
        var result = await authorizationService
            .RegisterUser(request.Login, request.Password, ct);

        return Ok(new RegisterUserResponse(result));
    }

    [HttpPost]
    [SwaggerOperation(OperationId = "LoginUser")]
    public async Task<ActionResult<LoginUserResponse>> LoginUser([FromBody] LoginUserRequest request,
        CancellationToken ct)
    {
        var result = await authorizationService
            .LoginUser(request.Login, request.Password, ct);

        return Ok(new LoginUserResponse(result));
    }
}