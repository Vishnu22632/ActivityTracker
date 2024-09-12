package com.synergytech.tms.api;

import com.synergytech.tms.model.User;
import com.synergytech.tms.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserRestApi {

    @Inject
    private UserRepository userRepository;

//   1. Getting all users
    
    @GET // indicates this method will handle GET requests
    @Produces(MediaType.APPLICATION_JSON) // The response type is JSON
    public Response getAllUsers() {
        try {
            List<User> users = userRepository.findAll(); //Fetch all users from the database
            return Response.ok(users).build(); // Return a 200 OK response with the list of users

        } catch (Exception e) {
            // Handle any errors and return a server error response
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retriving users").build();
        }

    }
    
    
    
    
//    2. Get a user by Id
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long id ){
        Optional<User> userOpt = userRepository.findById(id);
        
        if(userOpt.isPresent()){
            return Response.ok(userOpt.get()).build(); // 200 OK
        }else{
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found").build(); // 404 Not Found
        }
       
    }
    
    
//    3. Create a new user
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user){
        try {
            userRepository.create(user);
        return Response.status(Response.Status.CREATED).entity(user).build(); // 201 created
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error Creating user").build(); // 500 Internal Server Error
        }
        
    }
    
    
    // 4. Update an existing user
    
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id")Long id, User updatedUser){
       Optional<User> userOpt =userRepository.findById(id);
       
       if(userOpt.isPresent()){
           User user=userOpt.get();
           user.setFullName(updatedUser.getFullName());
           user.setEmail(updatedUser.getEmail());
           user.setPassword(updatedUser.getPassword());
           user.setUserRole(updatedUser.getUserRole());
           user.setAddress(updatedUser.getAddress());
           
           userRepository.update(user);
           
           return Response.ok(user).build(); //200 Ok
       }else{
           return Response.status(Response.Status.NOT_FOUND)
                   .entity("User not found").build(); //404 Not Found 
       }
       
       
    }
    
    
    // 5. Delete a user by ID
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id")Long id){
       Optional<User> userOpt =userRepository.findById(id);
       
       if(userOpt.isPresent()){
           userRepository.delete(id);
           return Response.ok("Successfully User deleted").build();
       }else{
           return Response.status(Response.Status.NOT_FOUND).entity("User not found").build(); // 404 Not found
       }
       
    }
    
    // 6. Find a user by email and password 
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByEmailAndPassword(User loginUser){
        User user= userRepository.findUserByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword());
        
        if(user!=null){
            return Response.ok(user).build(); // 200 OK
        }
        
        else{
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid email or password").build(); // 401 Unauthorized User
        }
        
    }
    
    
    

}
