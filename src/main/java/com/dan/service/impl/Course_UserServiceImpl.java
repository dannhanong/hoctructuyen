package com.dan.service.impl;

import com.dan.config.Environment;
import com.dan.exception.MoMoException;
import com.dan.model.Course;
import com.dan.model.Course_User;
import com.dan.model.User;
import com.dan.model.momo.PaymentResponse;
import com.dan.model.momo.RequestType;
import com.dan.repository.Course_UserRepository;
import com.dan.service.Course_UserService;
import com.dan.service.UserService;
import com.dan.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class Course_UserServiceImpl implements Course_UserService {
    @Autowired
    private Course_UserRepository course_userRepository;
    @Autowired
    private UserService userService;
//    @Autowired
//    private CreateOrderMoMo createOrderMoMo;

    @Override
    public Page<Course_User> getAllCourseByUser(User user, Pageable pageable) {
        return course_userRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Course_User> getAllCourse_User(String keyword, Pageable pageable) {
        return course_userRepository.findByKeyword(keyword, pageable);
    }

    @Override
    @Transactional
    public Course_User createCourse_User(Course_User course_user) {
        return course_userRepository.save(course_user);
    }

    @Override
    @Transactional
    public PaymentResponse createCourse_User(Course course, String username) throws MoMoException {
        User user = userService.getUserByUsername(username);
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = String.valueOf(System.currentTimeMillis());
        Long transId = 2L;
        Course_User course_user = new Course_User();
        course_user.setCourse(course);
        course_user.setUser(user);
        course_user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        long amount = course_user.getCourse().getCost();

        String partnerClientId = "partnerClientId";
        String orderInfo = "Pay With MoMo";
        String returnURL = "https://google.com.vn";
        String notifyURL = "https://google.com.vn";
        Environment environment = Environment.selectEnv("dev");
        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
        Course_User newCourse_User = course_userRepository.save(course_user);
        return captureWalletMoMoResponse;
    }

    @Override
    @Transactional
    public Course_User updateCourse_User(Course_User course_user, Long id) {
        return course_userRepository.findById(id).map(cu -> {
            cu.setCourse(course_user.getCourse());
            cu.setUser(course_user.getUser());
            cu.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            return course_userRepository.save(cu);
        }).orElseThrow(() -> new RuntimeException("Course_User not found"));
    }

    @Override
    @Transactional
    public void deleteCourse_User(Long id) {
        course_userRepository.deleteById(id);
    }

    @Override
    public int totalCostOfCourse(Course course) {
        return course_userRepository.totalCostOfCourse(course);
    }
}
