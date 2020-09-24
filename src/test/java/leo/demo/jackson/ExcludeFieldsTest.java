package leo.demo.jackson;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

public class ExcludeFieldsTest {
  @Test
  public void excludeParentByFilter() throws JsonProcessingException {
    ObjectMapper objectMapper= new ObjectMapper();
    SimpleBeanPropertyFilter theFilter = SimpleBeanPropertyFilter
        .serializeAllExcept("name","phone");
    FilterProvider filters = new SimpleFilterProvider()
        .addFilter("filterPerson", theFilter);
    objectMapper.setFilterProvider(filters);
    final User user = new User("email1",12);
    user.setName("personName");
    user.setPhone("12345");
    System.out.println(objectMapper.writeValueAsString(user));
  }

  @Test
  public void unwrappedTest() throws JsonProcessingException {
    ObjectMapper objectMapper= new ObjectMapper();
    final Body body = new Body("head", "leg");
    final Person person = new Person("name1", "123", body);
    System.out.println(objectMapper.writeValueAsString(person));
  }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("filterPerson")
class User extends Person{
  String email;
  int age;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Person{
  String name;
  String phone;
  @JsonUnwrapped
  Body body;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Body{
  String head;
  String leg;
}
