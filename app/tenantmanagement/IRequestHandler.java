package tenantmanagement;

//import bo.provisioning.Tenant;
//import bo.provisioning.UserPolicy;
//import dal.generics.SystemUserDA;
//import dto.common.SystemSignupResponseDTO;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.http.DefaultHttpRequestHandler;
import play.http.HttpRequestHandler;
import play.i18n.Messages;
import play.libs.F;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
//import services.v1.generics.TenantServices;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static play.mvc.Results.ok;

/**
 * Called for every request, sets the appropriate tenant id in context
 * Created by RP on 1/7/16.
 */
public class IRequestHandler  implements HttpRequestHandler {

    @Override
    public Action createAction(Http.Request request, Method actionMethod) {
    	Logger.info("inside create action");

        return new Action.Simple() {
            @Override
            public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                Map<String, Object> args = ctx.args;
                String uri = ctx.request().uri();
              //  TenantServices tenantServicesObj = new TenantServices();
              //  SystemSignupResponseDTO systemSignupResponseDTO = new SystemSignupResponseDTO();
          //      String tenant = null;

           //     String tenantIdentifier = request.getHeader("Tenant-Identifier");
                //For Tenant Controller Services
           //     if(uri.contains("/tenant")){
            //        tenantIdentifier = "111-000-111" ;
            //    }
                try {
          //      tenant = tenantServicesObj.fetchTenantFromTenantIdentifier(tenantIdentifier).getEncryptedDatabaseName();
                }catch (Exception ex){
                    Logger.error(ex.getMessage());
                    return F.Promise.pure(badRequest());
                }

             //  args.put("tenantId", tenant);
                try {
                    F.Promise<Result> result = delegate.call(ctx);
                    Http.Response response = ctx.response();
                    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Auth-Token, X-Auth-UserId, Tenant-Identifier");
                    response.setHeader("Access-Control-Allow-Credentials", "true");
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    response.setHeader("Access-Control-Expose-Headers", "X-Auth-Token, X-Auth-UserId, Tenant-Identifier");
                    return result;
                }catch(Exception ex){
                    Logger.error(ex.getMessage());
                    return F.Promise.pure(badRequest(ex.getMessage()));
                }
            }
        };
    }

    @Override
    public Action wrapAction(Action action) {
        return action;
    }

//    public UserPolicy testing(){
//        MultiTenantEvolutionsExecutor me = new MultiTenantEvolutionsExecutor(Play.application().configuration(), play.Environment.simple());

     //   EntityManager em0 = JPA.em("provisioningdb");
      //  UserPolicy up = new UserPolicy();
      //  up = em0.find(UserPolicy.class, 0L);
      //  em0.close();

      //  return up;
   // }
}


