
package com.synergytech.tms.api;

import com.synergytech.tms.model.User;
import com.synergytech.tms.repository.UserRepository;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectRestApi {
    
    @Inject
    private UserRepository userRepository;
    
    @GET
    public Response getAllProjects() {
        try{          
            List<User> users = userRepository.findAll();
            return RestResponse.responseBuilder("true", "200","Project retrieved successfully", users.toString());
        }catch(Exception e){
            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
        }      
    }
    
//    @GET
//    @Path("/{id}")
//    public Response getBudgetById(@PathParam("id") Long id) {
//        try {
//            Budget budget = budgetRepository.findById(id);
//            if (budget == null) {
//                return RestResponse.responseBuilder("true", "200", "Budget found", budget.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Budget not found", null);
//            }
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//    
//    @POST
//    public Response createBudget(Budget budget) {
//        try {
//            budgetRepository.save(budget);
//            return RestResponse.responseBuilder("true", "201", "Budget created successfully", budget.toString());
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "400", "Failed to create budget", e.getMessage());
//        }
//    }
//    
//    @PUT
//    @Path("/{id}")
//    public Response updateBudget(@PathParam("id") Long id, Budget budget) {
//        try {
//            Budget existingBudget = budgetRepository.findById(id);
//            if (existingBudget != null) {
//                if (budget.getRemainingAmount() == null) {
//                    budget.setRemainingAmount(budget.getBudgetLimit());
//                }   
//                
//                budget.setId(id);
//                budgetRepository.update(budget);
//               return RestResponse.responseBuilder("true", "200", "Budget updated successfully", budget.toString());
//            } else {
//                return RestResponse.responseBuilder("false", "404", "Budget not found", null);
//            }
//
//        } catch (Exception e) {
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
//    
//    @DELETE
//    @Path("/{id}")
//    public Response deleteBudget(@PathParam("id")Long id){
//        try{
//            Budget budget = budgetRepository.findById(id);
//            if(budget != null){
//                budgetRepository.delete(budget);
//                return RestResponse.responseBuilder("true", "204", "Budget deleted successfully", null);          
//            }else{
//                return RestResponse.responseBuilder("false", "404", "Budget not found", null);  
//            }               
//        }catch(Exception e){
//            return RestResponse.responseBuilder("false", "500", "An error occurred", e.getMessage());
//        }
//    }
 
}
