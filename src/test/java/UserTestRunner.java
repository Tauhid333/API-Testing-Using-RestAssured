import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner extends Setup {
    @Test(priority = 1, enabled = true)
    public void doLogin() throws IOException, ConfigurationException {
        UserController userController=new UserController();
        userController.doLogin("admin@roadtocareer.net","1234");
    }
    @Test(priority = 2)
    public void createUser() throws IOException, ConfigurationException {
        Faker faker=new Faker();
        UserController userController=new UserController();
        UserModel model=new UserModel();
        UserModel model2 = new UserModel();
        UserModel model3 = new UserModel();

        model.setName(faker.name().fullName());
        model.setEmail(faker.internet().emailAddress().toLowerCase());
        model.setPassword("P@ssword123");
        String phoneNumber="01502"+Utils.generateRandomId(100000,999999);
        model.setPhone_number(phoneNumber);
        model.setNid(String.valueOf(Utils.generateRandomId(100000000,999999999)));
        model.setRole("Customer");

        model2.setName(faker.name().fullName());
        model2.setEmail(faker.internet().emailAddress().toLowerCase());
        model2.setPassword("P@ssword123");
        String phoneNumber2="01502"+Utils.generateRandomId(100000,999999);
        model2.setPhone_number(phoneNumber2);
        model2.setNid(String.valueOf(Utils.generateRandomId(100000000,999999999)));
        model2.setRole("Customer");

        model3.setName(faker.name().fullName());
        model3.setEmail(faker.internet().emailAddress().toLowerCase());
        model3.setPassword("P@ssword123");
        String phoneNumber3="01502"+Utils.generateRandomId(100000,999999);
        model3.setPhone_number(phoneNumber3);
        model3.setNid(String.valueOf(Utils.generateRandomId(100000000,999999999)));
        model3.setRole("Agent");

        JsonPath jsonPath= userController.createUser(model);
        JsonPath jsonPath1 = userController.createUser(model2);
        JsonPath jsonPath2 = userController.createUser(model3);

        int userId= jsonPath.get("user.id");
        String customer1 = jsonPath.get("user.phone_number");
        String customer2 = jsonPath1.get("user.phone_number");
        String agent     = jsonPath2.get("user.phone_number");

        Utils.setEvnVar("userId",String.valueOf(userId));
        Utils.setEvnVar("customer1_phone_number", customer1);
        Utils.setEvnVar("customer2_phone_number", customer2);
        Utils.setEvnVar("agent_phone_number", agent);
    }
    @Test(priority = 3, enabled = true)
    public void agentLogin() throws IOException, ConfigurationException {
        UserController userController=new UserController();
        userController.agentLogin(prop.getProperty("agent_phone_number"), "P@ssword123");
    }


}