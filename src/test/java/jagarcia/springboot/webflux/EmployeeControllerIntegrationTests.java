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

    /*
    *Este método se ejecuta antes de cada prueba.
    * Borra todos los empleados en el repositorio para asegurar
    *  que cada prueba comience con una base de datos limpia.
     */
    @BeforeEach
    public void  before() {
        System.out.println("Before each test");
        employeeRepository.deleteAll().subscribe();
    }

    /*
    Prueba el endpoint de creación de empleados (POST /api/employees).
    Envía un EmployeeDto y verifica
    que la respuesta tenga el estado 201 Created y
     que el cuerpo de la respuesta coincida con el empleado creado.
    * */
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

    /*
    Prueba el endpoint de obtención de un empleado por ID (GET /api/employees/{id}).
    Guarda un empleado, lo recupera por ID y verifica que los detalles sean correctos.
    * */

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

    /*
    Prueba el endpoint de obtención de todos los empleados (GET /api/employees).
    Guarda un empleado y luego recupera la lista de empleados,
    verificando que el estado de la respuesta sea 200 OK.
    * */
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

    /*
    Prueba el endpoint de actualización de un empleado (PUT /api/employees/{id}).
    Guarda un empleado, lo actualiza y verifica que los detalles actualizados sean correctos.
    * */
    @Test
    public void testUpdateEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Yamile");
        employeeDto.setLastName("Garcia");
        employeeDto.setEmail("yg@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        EmployeeDto updateEmployee = new EmployeeDto();
        updateEmployee.setFirstName("Yam");
        updateEmployee.setLastName("Restrepo");
        updateEmployee.setEmail("or@gmail.com");

        webTestClient.put().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(updateEmployee), EmployeeDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(updateEmployee.getFirstName())
                .jsonPath("$.lastName").isEqualTo(updateEmployee.getLastName())
                .jsonPath("$.email").isEqualTo(updateEmployee.getEmail());
    }

    /*
    Prueba el endpoint de eliminación de un empleado (DELETE /api/employees/{id}).
    Guarda un empleado, lo elimina y verifica que el estado de la respuesta sea 204 No Content.
     */

    @Test
    public void testDeleteEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("Yamile");
        employeeDto.setLastName("Garcia");
        employeeDto.setEmail("yg@gmail.com");

        EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

        webTestClient.delete().uri("/api/employees/{id}", Collections.singletonMap("id", savedEmployee.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .consumeWith(System.out::println);


    }

}

