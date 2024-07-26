package jagarcia.springboot.webflux;

import jagarcia.springboot.webflux.dto.EmployeeDto;
import jagarcia.springboot.webflux.repository.EmployeeRepository;
import jagarcia.springboot.webflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private EmployeeRepository employeeRepository;
    @BeforeEach
    public void  before() {
        System.out.println("Before each test");
        employeeRepository.deleteAll().subscribe();

    }
    @Test
    public void testSaveEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Alexis");
        employeeDto.setLastName("Martinez");
        employeeDto.setEmail("am@gmail.com");

        webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());

    }

    @Test
    public void testGetSingleEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Lina");
        employeeDto.setLastName("Herrera");
        employeeDto.setEmail("linah@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.get().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }
    @Test
    public void testGetAllEmployees() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Karina");
        employeeDto.setLastName("Guerrero");
        employeeDto.setEmail("ka@gmail.com");

        employeeService.saveEmployee(employeeDto).block();

        webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println);
    }

}
