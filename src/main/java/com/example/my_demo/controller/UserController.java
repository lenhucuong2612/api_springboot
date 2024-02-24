package com.example.my_demo.controller;

import com.example.my_demo.payloads.ApiResponse;
import com.example.my_demo.payloads.UserDto;
import com.example.my_demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    //post-create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto userDto)
    {
        UserDto createUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }
    //get - get all user
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> getAllUser=this.userService.getAllUser();
        return new ResponseEntity<>(getAllUser, HttpStatus.OK);
    }
    //put - update user
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable Integer id){
        UserDto user=this.userService.updateUser(userDto,id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<UserDto> findUser(@PathVariable Integer id){
        UserDto userDto=this.userService.getUserById(id);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
       this.userService.deleteUserDto(id);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
    }
}
