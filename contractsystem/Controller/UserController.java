package com.example.contractsystem.Controller;

import com.example.contractsystem.ApiResponse.ApiResponse;
import com.example.contractsystem.Model.User;
import com.example.contractsystem.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add")
    public ResponseEntity addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Integer id,@RequestBody @Valid User user) {
        userService.updateUser(id, user);
        return ResponseEntity.status(200).body(new ApiResponse("user updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("user deleted successfully"));
    }






    @GetMapping("/get-top-contractor")
    public ResponseEntity getTopContractor(){
        return ResponseEntity.status(200).body(userService.getTopContractor());
    }

    @GetMapping("/report/{contractorId}")
    public ResponseEntity generateContractorReport(@PathVariable Integer contractorId){
        Map<String, Object> report = userService.generateContractorReport(contractorId);
        return ResponseEntity.status(200).body(report);
    }
}
