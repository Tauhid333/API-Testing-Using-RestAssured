import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner extends Setup {
    @Test(priority = 1, enabled = true)
    public void doLogin() throws IOException, ConfigurationException {
        UserController userController=new UserController();
        userController.doLogin("admin@roadtocareer.net","1234");
    }
    @Test(priority = 2)
    public void createCustomer1() throws IOException, ConfigurationException, ParseException {
        Faker faker=new Faker();
        UserController userController=new UserController();
        UserModel model=new UserModel();
        model.setName(faker.name().fullName());
        model.setEmail(faker.internet().emailAddress().toLowerCase());
        model.setPassword("P@ssword123");
        String phoneNumber="01502"+Utils.generateRandomId(100000,999999);
        model.setPhone_number(phoneNumber);
        model.setNid(String.valueOf(Utils.generateRandomId(100000000,999999999)));
        model.setRole("Customer");
        JsonPath jsonPath= userController.createUser(model);
        int userId= jsonPath.get("user.id");
        Utils.setEvnVar("userId",String.valueOf(userId));
        Utils.saveUsers(model);
    }
    @Test(priority = 3, enabled = true)
    public void agentLogin() throws IOException, ConfigurationException {
        UserController userController=new UserController();
        userController.agentLogin(prop.getProperty("agent_phone_number"), "P@ssword123");
    }


}