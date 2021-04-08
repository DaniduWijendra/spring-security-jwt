package com.danidu.springsecurityjwt.Controller;

import com.danidu.springsecurityjwt.Service.MyUserDetailService;
import com.danidu.springsecurityjwt.model.AuthenticationRequest;
import com.danidu.springsecurityjwt.model.AuthenticationRespond;
import com.danidu.springsecurityjwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResouceController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @RequestMapping({"/hello"})
    @ResponseBody
    public String hello()
    {
        return "Hello world";
    }
    @PostMapping("/authenticate")
    //@RequestMapping(value = "/authenticate",method = RequestMethod.POST) give same result
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
    {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("Invalid Credintials",e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationRespond(jwt));
    }
}
