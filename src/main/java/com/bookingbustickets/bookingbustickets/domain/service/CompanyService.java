package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.RequestCompanyDto;
import com.bookingbustickets.bookingbustickets.domain.model.Company;
import com.bookingbustickets.bookingbustickets.domain.repository.CompanyRepository;
import com.bookingbustickets.bookingbustickets.exception.CompanyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Page<Company> getAllCompanies(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return companyRepository.findAll(pageable);
    }

    public Company findCompanyById(Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isEmpty()) {
            throw new CompanyNotFoundException("Company with ID " + id + " is not found");
        }
        return companyOptional.get();
    }

    public Company createCompany(RequestCompanyDto requestCompanyDto) {
        Company createdCompany = new Company(requestCompanyDto.companyName());
        return companyRepository.save(createdCompany);
    }

    public Company updateCompany(Long id, RequestCompanyDto requestCompanyDto) {
        Company companyToUpdate = findCompanyById(id);
        companyToUpdate.setCompanyName(requestCompanyDto.companyName());
        return companyRepository.save(companyToUpdate);
    }

    public void deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else {
            throw new CompanyNotFoundException("Company with ID " + id + " is not found");
        }
    }
}