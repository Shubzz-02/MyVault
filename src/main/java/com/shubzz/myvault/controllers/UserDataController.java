package com.shubzz.myvault.controllers;


import com.shubzz.myvault.modes.DataUserPass;
import com.shubzz.myvault.payload.request.AddUserDataRequest;
import com.shubzz.myvault.payload.response.MessageResponse;
import com.shubzz.myvault.payload.response.UserDataResponse;
import com.shubzz.myvault.repository.UserDataRepository;
import com.shubzz.myvault.security.services.UserDetailsImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/mydata/")
public class UserDataController {

    final
    UserDataRepository userDataRepository;

    public UserDataController(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUserData() {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DataUserPass> list = userDataRepository.findByUserIdOrUserName(userDetails.getId(), userDetails.getUsername());
        List<UserDataResponse> resp = new ArrayList<>();
        for (DataUserPass d : list) {
            UserDataResponse userDataResponse = new UserDataResponse();
            userDataResponse.setEmailOrUsername(d.getEmailOrUsername());
            userDataResponse.setEncryptedPassword(d.getEncryptedPassword());
            userDataResponse.setDescription(d.getDescription());
            userDataResponse.setFolder(d.getFolder());
            userDataResponse.setCreatedAt(d.getCreatedAt());
            userDataResponse.setUpdatedAt(d.getUpdatedAt());
            resp.add(userDataResponse);
        }
        return ResponseEntity.ok(resp);
    }

    @PostMapping(value = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewUserData(@Valid @RequestBody List<AddUserDataRequest> addUserDataRequests, Errors error) {
        if (error.hasErrors())
            return ResponseEntity.ok(new MessageResponse("Enter Valid values!", 0));
        UserDetailsImpl userDetails =
                (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DataUserPass> list = new ArrayList<>();
        for (AddUserDataRequest n : addUserDataRequests) {
            DataUserPass dataUserPass = new DataUserPass();
            dataUserPass.setUserId(userDetails.getId());
            dataUserPass.setUserName(userDetails.getUsername());
            dataUserPass.setFolder(n.getFolder());
            dataUserPass.setEmailOrUsername(n.getEmailOrUsername());
            dataUserPass.setEncryptedPassword(n.getEncryptedPassword());
            dataUserPass.setDescription(n.getDescription());
            dataUserPass.setCreatedAt(new Date());
            dataUserPass.setUpdatedAt(new Date());
            list.add(dataUserPass);
        }

        userDataRepository.saveAll(list);

        return ResponseEntity.ok(new MessageResponse("Successfully added new Data", 2000));
    }
}
