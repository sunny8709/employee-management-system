package com.employee.service;

import com.employee.model.Attendance;
import com.employee.model.Employee;
import com.employee.repository.AttendanceRepository;
import com.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public Attendance trackAttendance(Long employeeId, LocalDate date, String status) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setAttendanceDate(date);
        attendance.setStatus(status);
        attendance.setCheckInTime(LocalTime.now());

        return attendanceRepository.save(attendance);
    }

    public Attendance markCheckOut(Long attendanceId) {
        if (attendanceId == null) {
            throw new IllegalArgumentException("Attendance ID cannot be null");
        }

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));

        attendance.setCheckOutTime(LocalTime.now());

        if (attendance.getCheckInTime() != null) {
            long hours = java.time.Duration.between(
                    attendance.getCheckInTime(),
                    attendance.getCheckOutTime()).toHours();
            attendance.setHoursWorked((double) hours);
        }

        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getEmployeeAttendanceLogs(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }

        return attendanceRepository.findByEmployeeEmployeeId(employeeId);
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        return attendanceRepository.findByAttendanceDate(date);
    }
}
