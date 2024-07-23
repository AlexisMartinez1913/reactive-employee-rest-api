package jagarcia.springboot.webflux.service;

import jagarcia.springboot.webflux.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);
    Mono<EmployeeDto> getEmployee(String employeeId);

}
