package com.workshop.workshopproject.service;

import com.workshop.workshopproject.dao.RoleRepository;
import com.workshop.workshopproject.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(int theId) {
        Optional<Role> result = roleRepository.findById(theId);

        Role role = null;

        if (result.isPresent()) {
            role = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find role id - " + theId);
        }

        return role;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(int theId) {
        roleRepository.deleteById(theId);
    }
}
