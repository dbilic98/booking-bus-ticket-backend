package com.bookingbustickets.bookingbustickets.util;

import com.bookingbustickets.bookingbustickets.domain.enumeration.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

public class RoleBasedAccessHelper {

  public static boolean isAdmin(Authentication authentication) {
    return hasRole(authentication, Role.ADMIN.name());
  }

  public static boolean isCompany(Authentication authentication) {
    return hasRole(authentication, Role.COMPANY.name());
  }

  public static boolean isClient(Authentication authentication) {
    return hasRole(authentication, Role.CLIENT.name());
  }

  private static boolean hasRole(Authentication authentication, String role) {
    return authentication.getAuthorities().stream()
        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + role));
  }

  public static void checkCompanyAccess(
      Authentication authentication, UUID companyIdFromToken, UUID companyIdFromRequest) {
    if (!companyIdFromToken.equals(companyIdFromRequest)) {
      throw new AccessDeniedException("Cannot manage resources for a different company.");
    }
  }

  public static void checkClientAccess(
      Authentication authentication, UUID userIdFromToken, UUID userIdFromRequest) {
    if (!userIdFromToken.equals(userIdFromRequest)) {
      throw new AccessDeniedException("Cannot manage resources for a different user.");
    }
  }
}
