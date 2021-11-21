package com.devsuperior.bds01.resources;

import static org.springframework.data.domain.Sort.by;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds01.dto.EmployeeDTO;
import com.devsuperior.bds01.services.EmployeeService;

@RestController
@RequestMapping(value="/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	@GetMapping
	public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable){
		PageRequest pageRequest = PageRequest
								.of(pageable.getPageNumber(),
									pageable.getPageSize(),
									by("name"));
		
		Page<EmployeeDTO> list = service.findAll(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO employeeDTO){
		EmployeeDTO result = service.create(employeeDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employeeDTO.getId()).toUri();
		
		return ResponseEntity.created(uri).body(result);
	}
}
