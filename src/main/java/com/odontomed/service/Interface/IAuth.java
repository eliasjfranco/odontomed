package com.odontomed.service.Interface;

import com.odontomed.model.User;

public interface IAuth {

    User findByEmail(String email);
}
