package com.bookingbustickets.bookingbustickets.security;


//@Component
//public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
//
//    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
//            new JwtGrantedAuthoritiesConverter();
//
//    private static final String principleAttribute = "preferred_username";
//
//    private static final String resourceId = "spring-boot-client";
//
//    @Override
//    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
//        Collection<GrantedAuthority> authorities = Stream.concat(
//                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
//                extractResourceRoles(jwt).stream()
//        ).collect(Collectors.toSet());
//
//        return new JwtAuthenticationToken(
//                jwt,
//                authorities,
//                getPrincipleClaimName(jwt)
//        );
//    }
//
//    private String getPrincipleClaimName(Jwt jwt) {
//        String claimName = JwtClaimNames.SUB;
//        if (principleAttribute != null) {
//            claimName = principleAttribute;
//        }
//        return jwt.getClaim(claimName);
//    }
//
//    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
//        Map<String, Object> resourceAccess;
//        Map<String, Object> resource;
//        Collection<String> resourceRoles;
//        if (jwt.getClaim("resource_access") == null) {
//            return Set.of();
//        }
//        resourceAccess = jwt.getClaim("resource_access");
//
//        if (resourceAccess.get(resourceId) == null) {
//            return Set.of();
//        }
//        resource = (Map<String, Object>) resourceAccess.get(resourceId);
//
//        resourceRoles = (Collection<String>) resource.get("roles");
//        return resourceRoles
//                .stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .collect(Collectors.toSet());
//    }
//}
