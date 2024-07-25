package jagarcia.springboot.webflux.service.impl;

import jagarcia.springboot.webflux.dto.EmployeeDto;
import jagarcia.springboot.webflux.entity.Employee;
import jagarcia.springboot.webflux.mapper.EmployeeMapper;
import jagarcia.springboot.webflux.repository.EmployeeRepository;
import jagarcia.springboot.webflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;



    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        //convert EmployeeDto into Employee Entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> saveEmployee = employeeRepository.save(employee);
        return saveEmployee
                .map((EmployeeMapper::mapToEmployeeDto));
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String employeeId) {
        Mono<Employee> savedEmployee = employeeRepository.findById(employeeId);
        return savedEmployee.map((EmployeeMapper::mapToEmployeeDto));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {
        Flux<Employee> employeeFlux = employeeRepository.findAll();
        return employeeFlux
                //si no hay empleados retorna un empty
                .map((employee -> EmployeeMapper.mapToEmployeeDto(employee)))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String employeeId) {
        Mono<Employee> employeeMono = employeeRepository.findById(employeeId);

        Mono<Employee> updateEmployee = employeeMono.flatMap((existingEmployee) -> {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());

            return employeeRepository.save(existingEmployee);
        });

        return updateEmployee.map((employee -> EmployeeMapper.mapToEmployeeDto(employee)));

    }

    @Override
    public Mono<Void> deleteEmployee(String employeeId) {

        return employeeRepository.deleteById(employeeId);
    }
}
