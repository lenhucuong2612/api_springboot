package com.example.my_demo.services.impl;

import com.example.my_demo.entity.Roles;
import com.example.my_demo.entity.User;
import com.example.my_demo.exceptions.ResourceNotFoundException;
import com.example.my_demo.payloads.UserDto;
import com.example.my_demo.repositories.RoleRepo;
import com.example.my_demo.repositories.UserRepo;
import com.example.my_demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user1=this.dtoToUser(userDto);
        User userSave=this.userRepo.save(user1);
        return this.UserToDo(userSave);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        User userSave=this.userRepo.save(user);
        UserDto userUpdate=this.UserToDo(userSave);
        return userUpdate;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));

        return this.UserToDo(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> list_user=this.userRepo.findAll();
        List<UserDto> listUserDto=new ArrayList<>();
        for(int i=0; i<list_user.size();i++){
            UserDto userDto=this.UserToDo(list_user.get(i));
            listUserDto.add(userDto);
        }
        return listUserDto;
    }

    @Override
    public void deleteUserDto(int userId) {
        User user=this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
        this.userRepo.delete(user);
    }
    public User dtoToUser(UserDto userDto){

        User user=new User();
        Roles role=this.roleRepo.findByName("USER");
        if(role==null){
            role.setName("USER");
            this.roleRepo.save(role);
        }
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());
        Set<Roles> rolesSet=new HashSet<>();
        rolesSet.add(role);
        user.setRoles(rolesSet);
        return user;
    }
    public UserDto UserToDo(User userDto){
        UserDto user=new UserDto();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        Set<Roles> rolesSet=userDto.getRoles();
        rolesSet.stream().map(i->i.getName()).forEach(System.out::println);
        return user;
    }
}
