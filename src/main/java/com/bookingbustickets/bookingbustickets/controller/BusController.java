package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.RequestBusDto;
import com.bookingbustickets.bookingbustickets.controller.response.PaginatedResponse;
import com.bookingbustickets.bookingbustickets.controller.response.ResponseBusDto;
import com.bookingbustickets.bookingbustickets.domain.enumeration.Role;
import com.bookingbustickets.bookingbustickets.domain.model.Bus;
import com.bookingbustickets.bookingbustickets.domain.model.Company;
import com.bookingbustickets.bookingbustickets.domain.service.BusService;
import com.bookingbustickets.bookingbustickets.domain.service.CompanyService;
import com.bookingbustickets.bookingbustickets.util.RoleBasedAccessHelper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/buses")
public class BusController {

    private final BusService busService;
    private final CompanyService companyService;

    public BusController(BusService busService, CompanyService companyService) {
        this.busService = busService;
        this.companyService = companyService;
    }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public PaginatedResponse<ResponseBusDto> getBuses(
      @RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "25") int pageSize) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Page<Bus> allBuses;

    if (RoleBasedAccessHelper.isAdmin(authentication)) {
      allBuses = busService.getAllBuses(pageNumber, pageSize);
    } else if (RoleBasedAccessHelper.isCompany(authentication)) {
      String companyId = authentication.getName();
      allBuses = busService.getBusesByCompany(companyId, pageNumber, pageSize);
    } else {
      throw new AccessDeniedException("Access is denied");
    }

    Page<ResponseBusDto> map = allBuses.map(this::toResponseDto);
    return new PaginatedResponse<>(map);
  }

    private ResponseBusDto toResponseDto(Bus bus) {
        return new ResponseBusDto(
                bus.getId(),
                bus.getModel(),
                bus.getLicensePlate(),
                bus.getCompany().getId(),
                bus.getSeats());
    }

  @PostMapping
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public void createBus(@RequestBody @Valid RequestBusDto requestBusDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID companyIdFromToken = UUID.fromString(authentication.getName());

    if (RoleBasedAccessHelper.isAdmin(authentication)) {
      busService.createBus(requestBusDto);
    } else if (RoleBasedAccessHelper.isCompany(authentication)) {
      Company companyById = companyService.findCompanyById(requestBusDto.getCompanyId());
      RoleBasedAccessHelper.checkCompanyAccess(authentication, companyIdFromToken, companyById.getCompanyUuid());
      busService.createBus(requestBusDto);
    } else {
      throw new AccessDeniedException("Access is denied");
    }
  }

    @GetMapping("/{busId}/seats")
    public int getNumberOfSeats(@PathVariable Long busId) {
        return busService.getNumberOfSeats(busId);
    }

    @GetMapping("/{id}")
    public ResponseBusDto findBusById(@PathVariable Long id) {
        Bus bus = busService.findBusById(id);
        return new ResponseBusDto(
                bus.getId(),
                bus.getModel(),
                bus.getLicensePlate(),
                bus.getCompany().getId(),
                bus.getSeats());
    }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public ResponseBusDto updateBus(
      @Valid @PathVariable("id") Long id, @RequestBody RequestBusDto requestBusDto) {
    Bus updatedBus;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID companyIdFromToken = UUID.fromString(authentication.getName());
    if (RoleBasedAccessHelper.isAdmin(authentication)) {
      updatedBus = busService.updateBus(id, requestBusDto);
    } else if (RoleBasedAccessHelper.isCompany(authentication)) {
      Company companyById = companyService.findCompanyById(requestBusDto.getCompanyId());
      RoleBasedAccessHelper.checkCompanyAccess(authentication, companyIdFromToken, companyById.getCompanyUuid());
      updatedBus = busService.updateBus(id, requestBusDto);
    } else {
      throw new AccessDeniedException("Access is denied");
    }

    return new ResponseBusDto(
            updatedBus.getId(),
            updatedBus.getModel(),
            updatedBus.getLicensePlate(),
            updatedBus.getCompany().getId(),
            updatedBus.getSeats());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAnyRole('ADMIN','COMPANY')")
  public void deleteBus(@PathVariable("id") Long id) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UUID companyIdFromToken = UUID.fromString(authentication.getName());

    if (RoleBasedAccessHelper.isAdmin(authentication)) {
      busService.deleteBus(id);
    } else if (RoleBasedAccessHelper.isCompany(authentication)) {
      Bus bus = busService.findBusById(id);
      RoleBasedAccessHelper.checkCompanyAccess(authentication, companyIdFromToken, bus.getCompany().getCompanyUuid());
      busService.deleteBus(id);
    } else {
      throw new AccessDeniedException("Access is denied");
    }
  }
}
