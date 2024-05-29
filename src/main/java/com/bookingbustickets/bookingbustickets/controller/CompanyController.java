package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestCompanyDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseCompanyDto;
import com.bookingbustickets.bookingbustickets.domain.model.Company;
import com.bookingbustickets.bookingbustickets.domain.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService; }

    @GetMapping
    public PaginatedResponse<ResponseCompanyDto> getCompanies (
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        Page<Company> allCompanies = companyService.getAllCompanies(pageNumber, pageSize);
        Page<ResponseCompanyDto> map = allCompanies.map(this::toResponseDto);
        return new PaginatedResponse<>(map);
        }
    private ResponseCompanyDto toResponseDto(Company company) {
        return new ResponseCompanyDto(
                company.getId(),
                company.getCompanyName());
    }

    @GetMapping("/{id}")
    public ResponseCompanyDto findCompanyById(@PathVariable("id") Long id) {
        Company company = companyService.findCompanyById(id);
        return new ResponseCompanyDto(
                company.getId(),
                company.getCompanyName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCompanyDto createCompany(@Valid @RequestBody RequestCompanyDto requestCompanyDto) {
        Company createdCompany = companyService.createCompany(requestCompanyDto);
        return new ResponseCompanyDto(
                createdCompany.getId(),
                createdCompany.getCompanyName());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseCompanyDto updateCompany(@Valid @PathVariable("id") Long id, @RequestBody RequestCompanyDto requestCompanyDto) {
        Company updatedCompany = companyService.updateCompany(id, requestCompanyDto);
        return new ResponseCompanyDto(
                updatedCompany.getId(),
                updatedCompany.getCompanyName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable("id") Long id) {
        companyService.deleteCompany(id);
    }
}
