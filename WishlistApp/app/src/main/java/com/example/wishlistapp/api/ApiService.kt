import com.example.wishlistapp.api.models.requests.*
import com.example.wishlistapp.api.models.responses.*
import retrofit2.http.*
import retrofit2.Response

interface ApiService {

    // AUTH
    @POST("api/v1/Authorization/RegisterUser")
    suspend fun registerUser(
        @Body request: RegisterUserRequest
    ): Response<RegisterUserResponse>

    @POST("api/v1/Authorization/LoginUser")
    suspend fun loginUser(
        @Body request: LoginUserRequest
    ): Response<LoginUserResponse>

    // USERS
    @GET("api/v1/Users/GetAllUsers")
    suspend fun getAllUsers(): Response<GetAllUsersResponse>

    @GET("api/v1/Users/GetUser/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): Response<GetUserResponse>

    @POST("api/v1/Users/CreateUser")
    suspend fun createUser(
        @Body body: CreateUserRequest
    ): Response<CreateUserResponse>

    // ITEMS
    @GET("api/v1/Items/GetAllItems")
    suspend fun getAllItems(): Response<GetAllItemsResponse>

    @GET("api/v1/Items/GetItem/{itemId}")
    suspend fun getItem(
        @Path("itemId") itemId: String
    ): Response<GetItemResponse>

    @POST("api/v1/Items/CreateItem")
    suspend fun createItem(
        @Body body: CreateItemRequest
    ): Response<CreateItemResponse>

    @PUT("api/v1/Items/UpdateItem/{itemId}")
    suspend fun updateItem(
        @Path("itemId") itemId: String,
        @Body body: UpdateItemRequest
    ): Response<UpdateItemResponse>

    @DELETE("api/v1/Items/DeleteItem/{itemId}")
    suspend fun deleteItem(
        @Path("itemId") itemId: String
    ): Response<DeleteItemResponse>
}
