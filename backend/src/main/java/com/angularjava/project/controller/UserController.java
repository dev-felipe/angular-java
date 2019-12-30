package com.angularjava.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.angularjava.project.model.User;
import com.angularjava.project.repository.UserRepository;
import com.angularjava.project.utils.Status;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository _userREPOSITORY;
	
	//Get all of the Registers on the database
    //user/
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getAllList(){
        return _userREPOSITORY.findAll();
    }
    
    
  //Get a one register by id
    //Registers/get/1
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Optional<User> getById(@PathVariable("id") int id) {
            return _userREPOSITORY.findById(id);
    }
    
    
    //Add entities in database, receive from angular interface by POST request
    //user/add
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Status insertNewPeople(@RequestBody User user) {
        try {
            if (user != null && _userREPOSITORY.findByEmail(user.getEmail()) == null){
            	_userREPOSITORY.save(user);
                return Status.SUCCESS;
            } else if (_userREPOSITORY.findByEmail(user.getEmail()) != null){
                return Status.EMAILEXIST;
            }
            return Status.FAIL;
        } catch (Exception e){
            return Status.ERROR;
        }
    }
    
    //Delete registers from database by id
    //user/delete/1
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public Status deletePeople(@PathVariable("id") int id) {
        try {
            if (id != 0){
            	_userREPOSITORY.deleteById(id);
                return Status.SUCCESS;
            }
            return Status.FAIL;
        } catch (Exception e){
            return Status.ERROR;
        }
    }
    
  //Update registers into database by id, receive entity from angular interface and replace.
    //Registers/update/1
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public Status changePeople(@PathVariable("id") int id, @RequestBody User user) {

        try {
            if (id != 0){
            User oldRegister = _userREPOSITORY.getOne(id);
            oldRegister = user;
            oldRegister.setId(id);
            _userREPOSITORY.save(oldRegister);
                return Status.SUCCESS;
            }
            return Status.FAIL;
        } catch (Exception e) {
            return Status.ERROR;
        }
    } 
}
