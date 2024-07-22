package jagarcia.springboot.webflux.service.impl;

import jagarcia.springboot.webflux.dto.EmployeeDto;
import jagarcia.springboot.webflux.entity.Employee;
import jagarcia.springboot.webflux.mapper.EmployeeMapper;
import jagarcia.springboot.webflux.repository.EmployeeRepository;
import jagarcia.springboot.webflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
}
